package com.example.besafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class Add_Number extends AppCompatActivity {

    EditText name,contact;
    Button save;
    DBHelper DB;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        name=findViewById(R.id.Inputname);
        contact=findViewById(R.id.Inputcontact);
        save=findViewById(R.id.savebtn);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.Inputname, RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        awesomeValidation.addValidation(this,R.id.Inputcontact,"[5-9]{1}[0-9]{9}$",R.string.invalid_mobile);


        DB=new DBHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT =name.getText().toString();
                String contactTXT= contact.getText().toString();



                if(awesomeValidation.validate()) {
                    Boolean check_numbers=DB.insertnumbers(nameTXT,contactTXT);
                    if(check_numbers==true) {

                        Toast.makeText(Add_Number.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");

                    }
                }
                else{
                    Toast.makeText(Add_Number.this,"Invalid data entered",Toast.LENGTH_SHORT).show();
                }
/*
                Cursor res=DB.viewData();
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Contact:"+res.getString(1)+"\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(Add_Number.this);
                builder.setCancelable(true);
                builder.setTitle("Emergency numbers");   //set title for AlertDialog
                builder.setMessage(buffer.toString());   //To display name and contact which is saved on buffer
                builder.show();
                //Display alert message with all values
                */
            }
        });
    }
}