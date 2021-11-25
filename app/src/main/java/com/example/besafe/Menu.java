package com.example.besafe;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    TextView addnum,instruct,registeredNumber,nearbyPoliceStations,emergencynum,editsos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addnum=findViewById(R.id.addnum);
        instruct=findViewById(R.id.instruct);
        registeredNumber=findViewById(R.id.regnum);
        nearbyPoliceStations=findViewById(R.id.nearbyPolice);
        emergencynum=findViewById(R.id.emergencynum);
        editsos=findViewById(R.id.editsos);
        //registeredNumber=findViewById(R.id.registeredNumber);
        addnum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent number=new Intent(Menu.this,Add_Number.class);
                startActivity(number);
            }
        });

        registeredNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewNum=new Intent(Menu.this,ViewRegisteredNumbers.class);
                startActivity(viewNum);
            }
        });

        instruct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instr=new Intent(Menu.this,Instructions.class);
                startActivity(instr);
            }
        });

        nearbyPoliceStations.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("geo:0,0?q=Police Station");
                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });
        emergencynum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instr=new Intent(Menu.this,EmergencyNumber.class);
                startActivity(instr);
            }
        });

        editsos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instr=new Intent(Menu.this,EditSOSMessage.class);
                startActivity(instr);
            }
        });


    }
}