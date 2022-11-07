package com.urjc.android_notes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteLocalDatabase extends SQLiteOpenHelper {

    public SQLiteLocalDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Table(s) definition
        sqLiteDatabase.execSQL(
                "CREATE TABLE notes(_id integer primary key autoincrement, title text, description text, tags text, dateCreated date)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}