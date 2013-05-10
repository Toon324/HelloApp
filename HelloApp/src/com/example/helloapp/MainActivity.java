package com.example.helloapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.helloapp.MESSAGE";
	ArrayList<String> values = new ArrayList<String>();
	ArrayList<String> list;
	AdapterView.AdapterContextMenuInfo info;
	ListAdapter adapter;
	ListHelper helper;
	ListView listview;
	Cursor model;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		helper = new ListHelper(this, "helper", null, 1);
		
		if (savedInstanceState != null) {
			for (int x = 0; x < savedInstanceState.size(); x++)
				values.add(savedInstanceState.getString("LIST VALUE " + x));
		}

		else {
			helper.insert("Cody Swendrowski", "101", 1, "CEA");
			helper.insert("Forrest", "101", 0, "Soil Samples");
			helper.insert("Casey Rogers", "208", 1, "Wall Section");
		}
		
		listview = (ListView) findViewById(R.id.peopleList);

		list = new ArrayList<String>();
		for (int i = 0; i < values.size(); ++i) {
			list.add(values.get(i));
		}
		//adapter = new ListAdapter(details, this);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				//list.remove(item);
				//adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(),
						item.toString(), Toast.LENGTH_LONG)
						.show();
			}

		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save the user's current game state
		for (int i = 0; i < list.size(); i++)
			savedInstanceState.putString("LIST VALUE " + i, list.get(i));

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

}
