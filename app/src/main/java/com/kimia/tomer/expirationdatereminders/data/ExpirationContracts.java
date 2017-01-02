package com.kimia.tomer.expirationdatereminders.data;

import android.provider.BaseColumns;

public class ExpirationContracts {

    public static final class ExpirationEntry implements BaseColumns {
        public static final String TABLE_NAME = "expiration_items";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_EXPIRATION = "expiration";
    }
}
