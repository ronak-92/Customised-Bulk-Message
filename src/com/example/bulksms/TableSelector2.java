package com.example.bulksms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class TableSelector2 extends Activity {

	List<String> groupList;
	List<String> childList;
	Map<String, List<String>> laptopCollection;
	ExpandableListView expListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table_list);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log.d("sql", "first statement");
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(BulkMessageContract.IndexTable.COLUMN_NAME_NAME, "Building");

		// Code to insert Group names in Database
		// Log.d("sql", "before insert statement");
		// // Insert the new row, returning the primary key value of the new row
		// long newRowId;
		// newRowId = db.insert(
		// BulkMessageContract.IndexTable.TABLE_NAME,
		// null,
		// values);
		//
		// Log.d("sql", "after insertion step");

		createGroupList();
		//createCollection();

		expListView = (ExpandableListView) findViewById(R.id.exlv_table_list);
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, laptopCollection);
		expListView.setAdapter(expListAdapter);

		// setGroupIndicatorToRight();

		expListView.setOnChildClickListener(new OnChildClickListener() {

			// This is where we handle the click event of any item(column) in
			// the list
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// final String selected = (String) expListAdapter.getChild(
				// groupPosition, childPosition);

				final String selected = (String) expListAdapter
						.getCompleteChildName(groupPosition, childPosition);

				Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
						.show();
				Intent returnedData = new Intent();
				returnedData.putExtra("fullName", selected);
				setResult(RESULT_OK, returnedData);
				finish();
				return true;
			}
		});

	}

	private void createGroupList() {
		groupList = new ArrayList<String>();
		laptopCollection = new LinkedHashMap<String, List<String>>();
		// groupList.add("Samsung");

		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				BulkMessageContract.IndexTable.COLUMN_NAME_ENTRY_ID,
				BulkMessageContract.IndexTable.COLUMN_NAME_NAME, };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = BulkMessageContract.IndexTable.COLUMN_NAME_NAME
				+ " DESC";

		Cursor cursor = db.query(BulkMessageContract.IndexTable.TABLE_NAME, // The
																			// table
																			// to
																			// query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		// cursor.moveToFirst();
		// cursor.moveToPosition(-1);
		while (cursor.moveToNext()) {
			String name = cursor
					.getString(cursor
							.getColumnIndexOrThrow(BulkMessageContract.IndexTable.COLUMN_NAME_NAME));
			groupList.add(name);
			long id = cursor
					.getLong(cursor
							.getColumnIndexOrThrow(BulkMessageContract.IndexTable.COLUMN_NAME_ENTRY_ID));
			//loadChildren("Table_" + id);
			loadChildren(name);
			laptopCollection.put(name,childList);
		}
		cursor.close();
	}

	private void loadChildren(String tableName) {
		// TODO Auto-generated method stub
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		Cursor cursor = db.query(tableName,
				null,
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null
				);

		childList = new ArrayList<String>();
		String columns[] = cursor.getColumnNames();
		for (String column : columns) {
			childList.add(column);
		}
		cursor.close();

	}

//	private void createCollection() {
//		// preparing laptops collection(child)
//		String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
//				"HP Envy 4-1025TX" };
//		String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
//		String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
//				"ThinkPad X Series", "Ideapad Z Series" };
//		String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
//				"VAIO S Series", "VAIO YB Series" };
//		String[] dellModels = { "Inspiron", "Vostro", "XPS" };
//		String[] samsungModels = { "NP Series", "Series 5", "SF Series" };
//
//		laptopCollection = new LinkedHashMap<String, List<String>>();
//
//		for (String laptop : groupList) {
//			if (laptop.equals("HP")) {
//				loadChild(hpModels);
//			} else if (laptop.equals("Dell"))
//				loadChild(dellModels);
//			else if (laptop.equals("Sony"))
//				loadChild(sonyModels);
//			else if (laptop.equals("HCL"))
//				loadChild(hclModels);
//			else if (laptop.equals("Samsung"))
//				loadChild(samsungModels);
//			else
//				loadChild(lenovoModels);
//
//			laptopCollection.put(laptop, childList);
//		}
//	}
//
//	private void loadChild(String[] laptopModels) {
//		childList = new ArrayList<String>();
//		for (String model : laptopModels)
//			childList.add(model);
//	}

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
	 */
}
