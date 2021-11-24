package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRegisteredNumbers extends AppCompatActivity {

    DBHelper db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView numlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_numbers);
        numlist=(ListView) findViewById(R.id.registeredNumbers);
        db=new DBHelper(this);
        listItem=new ArrayList<>();

        viewData();

        numlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text=numlist.getItemAtPosition(i).toString();
                Toast.makeText(ViewRegisteredNumbers.this,""+text,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void viewData() {
        Cursor cursor=db.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"No contacts have been added",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(0));
                listItem.add(cursor.getString((1)));
                adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
                numlist.setAdapter(adapter);
            }

        }

    }

}