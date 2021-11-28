package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText name,phoneNumber;
    AwesomeValidation awesomeValidation;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.besafe", Context.MODE_PRIVATE);

        if(!sharedPreferences.contains("status"))
        {
            sharedPreferences.edit().putString("status","Inactive").apply();
        }

        if(sharedPreferences.getString("status","Inactive").equals("Active"))
        {
            Intent intent=new Intent(MainActivity.this,HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            if(getIntent().getBooleanExtra("Exit",false))
            {
                finish();
            }
            else{
                finish();
            }
        }


        name=(EditText) findViewById(R.id.name);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        phoneNumber=(EditText) findViewById(R.id.phoneNumber);
        ccp.registerCarrierNumberEditText(phoneNumber);
        next=(Button)findViewById(R.id.next);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        awesomeValidation.addValidation(this,R.id.phoneNumber,"[5-9]{1}[0-9]{9}$",R.string.invalid_mobile);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(awesomeValidation.validate()) {
                      //  Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,OTPConfirmation.class);
                    intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                    if(getIntent().getBooleanExtra("Exit",false))
                    {
                        finish();
                    }
                    else{
                        finish();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid data entered",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}