package com.kimia.tomer.expirationdatereminders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kimia.tomer.expirationdatereminders.data.ExpirationContracts.*;
import com.kimia.tomer.expirationdatereminders.data.ExpirationDbHelper;
import com.kimia.tomer.expirationdatereminders.models.Expiration;


public class MainActivity extends AppCompatActivity {

    RecyclerView mExpirationRecyclerView;
    ExpirationListAdapter mAdapter;
    SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ExpirationListAdapter();
        mExpirationRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_expiration_items);
        mExpirationRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mExpirationRecyclerView.setAdapter(mAdapter);

        mDb = new ExpirationDbHelper(this).getWritableDatabase();
    }


    @Override
    protected void onResume() {
        mAdapter.setListData(getAllExpirations());
        super.onResume();
    }

    public void openAddActivity(View v) {
        startActivity(new Intent(this, AddNewExpirationActivity.class));

    }

    public Expiration[] getAllExpirations() {
        Cursor c = mDb.query(ExpirationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ExpirationEntry.COLUMN_EXPIRATION);

        Expiration[] result = new Expiration[c.getCount()];
        int i = 0;
        while(c.moveToNext()) {
            result[i++] = new Expiration(
                    c.getLong(c.getColumnIndex(ExpirationEntry._ID)),
                    c.getString(c.getColumnIndex(ExpirationEntry.COLUMN_ITEM_NAME)),
                    c.getLong(c.getColumnIndex(ExpirationEntry.COLUMN_EXPIRATION))
            );
        }
        c.close();

        return result;
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }
}
