package com.pluralsight.candycoded.DB;

import android.provider.BaseColumns;

public class CandyContract {
    public static final String DB_NAME = "candycoded.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CandyEntry.TABLE_NAME + " (" +
                    CandyEntry._ID + " INTEGER PRIMARY KEY," +
                    CandyEntry.COLUMN_NAME_NAME + " TEXT," +
                    CandyEntry.COLUMN_NAME_PRICE + " TEXT," +
                    CandyEntry.COLUMN_NAME_DESC + " TEXT," +
                    CandyEntry.COLUMN_NAME_IMAGE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CandyEntry.TABLE_NAME;

    public static class CandyEntry implements BaseColumns {
        public static final String TABLE_NAME = "candy";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}
