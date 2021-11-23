package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText name,phoneNumber;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.besafe", Context.MODE_PRIVATE);

        if(!sharedPreferences.contains("status"))
        {
            sharedPreferences.edit().putString("status","Active").apply();
        }

        if(sharedPreferences.getString("status","Inactive").equals("Active"))
        {
            Intent intent=new Intent(MainActivity.this,HomeScreen.class);
            startActivity(intent);
        }


        name=(EditText) findViewById(R.id.name);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        phoneNumber=(EditText) findViewById(R.id.phoneNumber);
        ccp.registerCarrierNumberEditText(phoneNumber);
        next=(Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OTPConfirmation.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });
    }
}