package com.example.besafe;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    TextView addnum,instruct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addnum=findViewById(R.id.addnum);
        instruct=findViewById(R.id.instruct);
        addnum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent number=new Intent(Menu.this,Add_Number.class);
                startActivity(number);
            }
        });
        instruct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instr=new Intent(Menu.this,Instructions.class);
                startActivity(instr);
            }
        });
    }
}