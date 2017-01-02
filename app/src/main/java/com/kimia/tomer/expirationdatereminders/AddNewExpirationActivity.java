package com.kimia.tomer.expirationdatereminders;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.kimia.tomer.expirationdatereminders.data.ExpirationContracts.*;
import com.kimia.tomer.expirationdatereminders.data.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddNewExpirationActivity extends AppCompatActivity {
    //Views
    private EditText mItemNameEditText;
    private DatePicker mItemExpirationDatePicker;

    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expiration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemNameEditText = (EditText) findViewById(R.id.edittext_item_name);
        mItemExpirationDatePicker = (DatePicker) findViewById(R.id.datepicker_item_expiration);

        ExpirationDbHelper dbHelper = new ExpirationDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        ((Button) findViewById(R.id.button_add_written_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpirationItem(view);
            }
        });
    }

    protected void addExpirationItem(View view)
    {
        String name = mItemNameEditText.getText().toString();
        if (name.isEmpty()) return;
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.set(
                mItemExpirationDatePicker.getYear(),
                mItemExpirationDatePicker.getMonth(),
                mItemExpirationDatePicker.getDayOfMonth()
        );

        long expirationTime = expirationDate.getTime().getTime();

        ContentValues cv = new ContentValues();
        cv.put(ExpirationEntry.COLUMN_ITEM_NAME, name);
        cv.put(ExpirationEntry.COLUMN_EXPIRATION, expirationTime);

        mDb.insert(ExpirationEntry.TABLE_NAME, "", cv);

        mItemNameEditText.getText().clear();
    }


}
