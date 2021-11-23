package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditSOSMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sosmessage);
        try{
            Button save=findViewById(R.id.SaveAndBackMenu);
            SQLiteDatabase myDatabase=this.openOrCreateDatabase("sosMsg",MODE_PRIVATE,null);
            //executing sql query
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS sosMsg (message TEXT)");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText msg=findViewById(R.id.editTextTextMultiLine2);
                    String m=msg.getText().toString();
                    try {
                        //creating database or opening existing database


                        //creating cursor to store the retrieved data from the table
                        // String query="INSERT INTO sosMsg  VALUES(" + m + ")";

                        // myDatabase.execSQL(query);
                        ContentValues cv=new ContentValues();
                        cv.put("message",m);
                        myDatabase.insert("sosMsg",null,cv);

                        //Cursor c=myDatabase.rawQuery("SELECT * FROM sosMsg",null);
                        //storing starting index
                      //  int messageIndex=c.getColumnIndex("message");

                       // c.moveToLast();
                         //Toast.makeText(EditSOSMessage.this, c.getString(messageIndex), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }





}