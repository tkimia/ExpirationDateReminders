package com.kimia.tomer.expirationdatereminders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kimia.tomer.expirationdatereminders.data.ExpirationContracts.*;
import com.kimia.tomer.expirationdatereminders.data.ExpirationDbHelper;
import com.kimia.tomer.expirationdatereminders.models.Expiration;


public class MainActivity extends AppCompatActivity {

    LinearLayout mHeaderLayout;
    TextView mEmptyAdapterTextView;
    RecyclerView mExpirationRecyclerView;
    ExpirationListAdapter mAdapter;

    SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExpirationRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_expiration_items);
        mHeaderLayout = (LinearLayout) findViewById(R.id.header_layout);
        mEmptyAdapterTextView = (TextView) findViewById(R.id.textview_empty_list);

        mAdapter = new ExpirationListAdapter();
        mExpirationRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mExpirationRecyclerView.setAdapter(mAdapter);

        mDb = new ExpirationDbHelper(this).getWritableDatabase();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeExpiration(id);
                loadItemsIntoAdapter();
            }
        });
        itemTouchHelper.attachToRecyclerView(mExpirationRecyclerView);
    }

    @Override
    protected void onResume() {
        loadItemsIntoAdapter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }

    public void openAddActivity(View v) {
        startActivity(new Intent(this, AddNewExpirationActivity.class));

    }

    private void loadItemsIntoAdapter() {
        Expiration[] data = getAllExpirations();
        setScreenVisibilities(data.length > 0);
        mAdapter.setListData(data);
    }

    public boolean removeExpiration(long id) {
        int effect = mDb.delete(ExpirationEntry.TABLE_NAME,
                ExpirationEntry._ID + "=" + id,
                null);
        return effect > 0;
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

    private void setScreenVisibilities(boolean hasData) {
        int listVisibility = (hasData) ? View.VISIBLE : View.INVISIBLE;
        int emptyVisibility = (hasData) ? View.INVISIBLE : View.VISIBLE;

        mHeaderLayout.setVisibility(listVisibility);
        mExpirationRecyclerView.setVisibility(listVisibility);
        mEmptyAdapterTextView.setVisibility(emptyVisibility);
    }
}
