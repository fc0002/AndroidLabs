package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static int VERSION_NUM = 1;
    protected final static String DATABASE_NAME = "Lab5.db";
    public final static String TABLE_NAME = "ToDoList";
    public static final String COL_ID = "_id";
    public static final String COL_ITEM = "ITEM";
    public static final String COL_URGENCY = "URGENCY";


    public MyOpener(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, COL_ITEM text, COL_URGENCY integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

}
