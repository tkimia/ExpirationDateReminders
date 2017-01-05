package com.kimia.tomer.expirationdatereminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kimia.tomer.expirationdatereminders.data.ExpirationContracts.*;
import com.kimia.tomer.expirationdatereminders.data.*;

import java.util.Calendar;

public class AddNewExpirationActivity extends AppCompatActivity {

    //Views
    private EditText mItemNameEditText;
    private DatePicker mItemExpirationDatePicker;
    private TextView mItemAddedMessageTextView;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expiration);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemNameEditText = (EditText) findViewById(R.id.edittext_item_name);
        mItemExpirationDatePicker = (DatePicker) findViewById(R.id.datepicker_item_expiration);
        mItemAddedMessageTextView = (TextView) findViewById(R.id.textview_add_activity_log_message);

        ExpirationDbHelper dbHelper = new ExpirationDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        findViewById(R.id.button_add_written_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpirationItem(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FocusOnEditText();
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }


    protected void addExpirationItem(View view)
    {
        //make sure name is there
        String name = mItemNameEditText.getText().toString();
        if (name.isEmpty()) return;

        //get date
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.set(
                mItemExpirationDatePicker.getYear(),
                mItemExpirationDatePicker.getMonth(),
                mItemExpirationDatePicker.getDayOfMonth()
        );
        long expirationTime = expirationDate.getTime().getTime();

        //create CV and insert
        ContentValues cv = new ContentValues();
        cv.put(ExpirationEntry.COLUMN_ITEM_NAME, name);
        cv.put(ExpirationEntry.COLUMN_EXPIRATION, expirationTime);

        if (mDb.insert(ExpirationEntry.TABLE_NAME, "", cv) >= 0)
        {
            mItemAddedMessageTextView.setTextColor(Color.GREEN);
            mItemAddedMessageTextView.setText(
                    String.format("%s has been successfully added to your list", name)
            );
        }
        else
        {
            mItemAddedMessageTextView.setTextColor(Color.RED);
            mItemAddedMessageTextView.setText(
                    String.format("%s could not be added to your list. Please try again", name)
            );
        }
        mItemAddedMessageTextView.setVisibility(View.VISIBLE);

        mItemNameEditText.getText().clear();
        FocusOnEditText();
    }


    private void FocusOnEditText() {
        mItemNameEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
