package com.chends.shrecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by chends on 2017/6/30.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_OUT = "create table Out ("
            + "id integer primary key autoincrement,"
            + "money real,"
            + "mode text,"
            + "type text,"
            + "date text,"
            + "explain text)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_OUT);
        Toast.makeText(mContext,"SQLite create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
