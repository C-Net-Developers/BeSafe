package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class ViewRegisteredNumbers extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    ListView registeredNumbers=findViewById(R.id.registeredNumbers);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_numbers);

    }
}