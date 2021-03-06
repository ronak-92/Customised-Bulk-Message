package com.example.bulksms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class TableSelector extends ExpandableListActivity {
	private static final String NAME = "NAME";

	private ExpandableListAdapter mAdapter;

	private String group[] = { "Development", "Data Process Team" };
	private String[][] child = { { "John", "Bill" }, { "Alice", "David" } };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
		for (int i = 0; i < group.length; i++) {
			Map<String, String> curGroupMap = new HashMap<String, String>();
			groupData.add(curGroupMap);
			curGroupMap.put(NAME, group[i]);

			List<Map<String, String>> children = new ArrayList<Map<String, String>>();
			for (int j = 0; j < child[i].length; j++) {
				Map<String, String> curChildMap = new HashMap<String, String>();
				children.add(curChildMap);
				curChildMap.put(NAME, child[i][j]);
			}
			childData.add(children);

		}

		// Set up our adapter
		mAdapter = new SimpleExpandableListAdapter(this, groupData,
				android.R.layout.simple_expandable_list_item_1,
				new String[] { NAME }, new int[] { android.R.id.text1 },
				childData, android.R.layout.simple_expandable_list_item_2,
				new String[] { NAME }, new int[] { android.R.id.text1 });
		setListAdapter(mAdapter);

	}

}
