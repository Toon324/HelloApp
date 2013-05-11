package com.example.helloapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.helloapp.MESSAGE";
	
	ArrayList<String> values = new ArrayList<String>();
	ArrayList<String> list;
	AdapterView.AdapterContextMenuInfo info;
	ListAdapter adapter;
	ListHelper helper;
	ListView listview;
	ArrayList<ListHelper> data;
	Cursor model;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		data = new ArrayList<ListHelper>();

		if (savedInstanceState != null) {
			for (int x = 0; x < savedInstanceState.size() / 4; x++) {
				helper = new ListHelper();
				
				int availability = savedInstanceState.getInt("Availability" + x);
				String name = savedInstanceState.getString("Name" + x);
				String room = savedInstanceState.getString("Room" + x);
				String project = savedInstanceState.getString("Project" + x);
				
				helper.setData(name, room, availability, project);
				data.add(helper);
			}
		}

		else {
			helper = new ListHelper();
			helper.setData("Cody Swendrowski", "101", 2, "CEA");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Forrest", "Away", 0, "Soil Samples");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Casey Rogers", "208", 1, "Wall Section");
			data.add(helper);
		}

		listview = (ListView) findViewById(R.id.peopleList);

		list = new ArrayList<String>();

		adapter = new ListAdapter(data, this);

		listview.setAdapter(adapter);

		registerForContextMenu(listview);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				
					ListHelper helper = data.get(position);
				
					Intent i=new Intent(MainActivity.this, DisplayPerson.class);

				    i.putExtra("com.example.helloapp.availability", helper.getAvailability());
				    i.putExtra("com.example.helloapp.name", helper.getName());
				    i.putExtra("com.example.helloapp.room", helper.getRoom());
				    i.putExtra("com.example.helloapp.project", helper.getProject());
				    
				    startActivity(i);
			}

		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		for (int x=0; x < data.size(); x++) {
			ListHelper lh = data.get(x);
			savedInstanceState.putInt("Availability" + x, lh.getAvailability());
			savedInstanceState.putString("Name" + x, lh.getName());
			savedInstanceState.putString("Room" + x, lh.getRoom());
			savedInstanceState.putString("Project" + x, lh.getProject());
		}
			

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		info = (AdapterContextMenuInfo) menuInfo;

		menu.setHeaderTitle(data.get(info.position).getName());
		menu.add(Menu.NONE, v.getId(), 0, "Call");
		menu.add(Menu.NONE, v.getId(), 0, "Text");
		menu.add(Menu.NONE, v.getId(), 0, "Add to Friends List");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Call") {
			// Do your working
		} else if (item.getTitle() == "Text") {
			// Do your working
		} else if (item.getTitle() == "Add to Friends List") {
			// Do your working
		} else {
			return false;
		}
		return true;
	}

}
