package com.example.besafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewRegisteredNumbers extends AppCompatActivity {
    //SQLiteDatabase sqLiteDatabase;
    //ListView registeredNumbers=findViewById(R.id.registeredNumbers);

    DBHelper db;
    Button backBtn;
    ListView listView;
    List list=new ArrayList<String>();

    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_numbers);
        listView=findViewById(R.id.list_view);
        try{
            db=new DBHelper(this);
            Cursor res=db.getdata();
            if(res.getCount()==0)
            {
                Toast.makeText(ViewRegisteredNumbers.this, "No entry present", Toast.LENGTH_SHORT).show();
                finish();
            }
            int NameIndex=res.getColumnIndex("Name");
            int ContactIndex=res.getColumnIndex("Contact");
            res.moveToFirst();
            while(!res.isAfterLast())
            {
                list.add(res.getString(NameIndex));
                res.moveToNext();
            }
            adapter=new ArrayAdapter(ViewRegisteredNumbers.this, android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    new AlertDialog.Builder(ViewRegisteredNumbers.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure?")
                            .setMessage("Do you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String name = ( (TextView) view ).getText().toString();
                                    //String name=res.getString(i);
                                   // Toast.makeText(ViewRegisteredNumbers.this, name, Toast.LENGTH_SHORT).show();
                                    db.deletenumber(name);
                                    adapter.notifyDataSetChanged();
                                    ViewRegisteredNumbers.this.recreate();
                                }
                            }).setNegativeButton("No", null)
                            .show();


                    //Toast.makeText(ViewRegisteredNumbers.this, Integer.toString(i), Toast.LENGTH_SHORT).show();


                }
            });

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        backBtn=findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}