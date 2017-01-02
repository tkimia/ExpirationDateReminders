package com.kimia.tomer.expirationdatereminders.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kimia.tomer.expirationdatereminders.data.ExpirationContracts.*;

public class ExpirationDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expiration.db";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    public ExpirationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpirationEntry.TABLE_NAME + "(" +
                ExpirationEntry._ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, "    +
                ExpirationEntry.COLUMN_ITEM_NAME    + " TEXT NOT NULL, "                        +
                ExpirationEntry.COLUMN_EXPIRATION   + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
