package com.example.lorrowlogger.data;

import static com.example.lorrowlogger.data.Constants.col2;
import static com.example.lorrowlogger.data.Constants.namecol1;
import static com.example.lorrowlogger.data.Constants.tableName;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context, Constants.databaseName, null, Constants.databaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlittable="Create Table "+ Constants.tableName +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,BALANCE INTEGER)";
        String sqlittable1="Create Table "+ namecol1 +" (TRANS INTEGER PRIMARY KEY AUTOINCREMENT)";
        sqLiteDatabase.execSQL(sqlittable);
        sqLiteDatabase.execSQL(sqlittable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constants.tableName);
    }

    public boolean insertData(String name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col2,name);
        long result=sqLiteDatabase.insert(tableName,null,cv);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+tableName,null);
        return res;
    }
    public Cursor getNameAccount(String name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor curs=sqLiteDatabase.rawQuery("select "+name+" from "+namecol1,null);
        return curs;
    }

    public void CreateNameAccount(String NameForAccount) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Log.d("name table","name table created for "+NameForAccount);
        sqLiteDatabase.execSQL("Alter Table "+ namecol1 +" ADD "+ NameForAccount +" INT DEFAULT NULL");
    }
    public void onUpgradeAccount(SQLiteDatabase sqLiteDatabase, int i, int i1,String name) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+name);
    }
    public boolean insertDataAccount(String name,Integer trans){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(name,trans);
        long result=sqLiteDatabase.insert(namecol1,null,cv);
        if (result==-1)
            return false;
        else
            return true;
    }
    public void deletedata(String name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+tableName+" WHERE "+col2+"='"+name+"'");
//        sqLiteDatabase.execSQL("ALTER TABLE "+namecol1+" DROP COLUMN "+name);
    }
    public void deletetrans(String name,int transnum){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+namecol1+" WHERE "+name+"='"+name+"'");
    }
}
