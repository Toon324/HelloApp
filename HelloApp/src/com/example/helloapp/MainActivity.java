package com.example.helloapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.helloapp.MESSAGE";
	ArrayList<String> values = new ArrayList<String>();
	ArrayList<String> list;
	StableArrayAdapter adapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			 for (int x = 0; x < savedInstanceState.size(); x++ )
				 values.add(savedInstanceState.getString("LIST VALUE " + x));
		}
		
		if (values.size() == 0) {
			values.add("Cody Swendrowski");
			values.add("Forrest Galkepetko");
			values.add("Casey Rogers");
			values.add("Guest");
		}
		
		setContentView(R.layout.activity_main);
		ListView listview = (ListView) findViewById(R.id.peopleList);

		list = new ArrayList<String>();
		for (int i = 0; i < values.size(); ++i) {
			list.add(values.get(i));
		}
		adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				list.remove(item);
				adapter.notifyDataSetChanged();
				
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

class StableArrayAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public StableArrayAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
