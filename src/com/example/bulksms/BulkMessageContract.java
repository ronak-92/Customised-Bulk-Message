package com.example.bulksms;

import android.provider.BaseColumns;

//This class serves as a contract for the database.
//This means it contains the structure of the Database for all it's tables and columns
//for uniformity while accessing it.
public final class BulkMessageContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public BulkMessageContract() {}

    /* Inner class that defines the table contents */
    public static abstract class IndexTable implements BaseColumns {
        public static final String TABLE_NAME = "Index_Table";
        public static final String COLUMN_NAME_ENTRY_ID = "Id";
        public static final String COLUMN_NAME_NAME = "Table_Name";
        //public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}