package com.example.besafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Number extends AppCompatActivity {

    EditText name,contact;
    Button save;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        name=findViewById(R.id.Inputname);
        contact=findViewById(R.id.Inputcontact);
        save=findViewById(R.id.savebtn);
        DB=new DBHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT =name.getText().toString();
                String contactTXT= contact.getText().toString();
                Boolean check_numbers=DB.insertnumbers(nameTXT,contactTXT);

                if(check_numbers==true){
                    Toast.makeText(Add_Number.this,"Contact added successfully",Toast.LENGTH_SHORT).show();
/*

*/
                }
                else{
                    Toast.makeText(Add_Number.this,"Some error occurred while adding contact",Toast.LENGTH_SHORT).show();
                }

                Cursor res=DB.getdata();
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Contact:"+res.getString(1)+"\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(Add_Number.this);
                builder.setCancelable(true);
                builder.setTitle("Emergency numbers");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}