package com.example.besafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Emergency.db";
    //private static final String TABLE_NAME = "Registered Contacts";
    private static final String COLUMN_0 = "Name";
    private static final String COLUMN_1 = "Contact";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Registered_Contacts ( Name TEXT primary key,Contact TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Registered_Contacts");
        onCreate(DB);
    }

    public Boolean insertnumbers(String name,String contact){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_0, name);
        cv.put(COLUMN_1, contact);
        long result=DB.insert("Registered_Contacts",null,cv);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    /*
        public Cursor getdata(){
            SQLiteDatabase DB=this.getWritableDatabase();
            Cursor cursor=DB.rawQuery("Select * from Registered_Contacts",null); //return data in form of cursor object
            return cursor;
        }
    */
    public Cursor viewData(){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from Registered_Contacts",null); //return data in form of cursor object
        return cursor;
    }

}