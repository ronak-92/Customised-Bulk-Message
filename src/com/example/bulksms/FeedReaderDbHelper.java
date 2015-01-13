package com.example.bulksms;

import java.io.Console;
import com.example.bulksms.BulkMessageContract.IndexTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BulkMessage.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + IndexTable.TABLE_NAME + " (" +
        IndexTable.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        IndexTable.COLUMN_NAME_NAME + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS Group1";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
    	try{
    	Log.d("sql", "running onCreate Database");
        db.execSQL(SQL_CREATE_ENTRIES);
    	}catch(Exception e){
    		Log.d("sql",e.getMessage());
    	}
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
    	try{
			Log.d("sql", "running onUpgrade Database");
			db.execSQL(SQL_DELETE_ENTRIES);
			onCreate(db);
    	}catch(Exception e){
    		Log.d("sql",e.getMessage());
    	}
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}