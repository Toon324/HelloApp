package com.example.helloapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
	ListView listview;
	private static ArrayList<ListHelper> data;
	Cursor model;

	NfcAdapter nfc;
	PendingIntent pending;
	IntentFilter filter[];

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = new ArrayList<ListHelper>();

		if (savedInstanceState != null) {
			for (int x = 0; x < savedInstanceState.size() / 4; x++) {
				ListHelper helper = new ListHelper();

				int availability = savedInstanceState
						.getInt("Availability" + x);
				String name = savedInstanceState.getString("Name" + x);
				String room = savedInstanceState.getString("Room" + x);
				String project = savedInstanceState.getString("Project" + x);

				Log.d("Hi", name + " is restored as " + availability);
				
				helper.setData(name, room, availability, project);
				data.add(helper);
			}
		}

		else {
			ListHelper helper = new ListHelper();
			helper.setData("Cody Swendrowski", "101", 0, "CEA");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Forrest", "Away", 0, "Soil Samples");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Casey Rogers", "208", 1, "Wall Section");
			data.add(helper);
		}
	}

	public void onStart() {
		super.onStart();
		
		nfc = NfcAdapter.getDefaultAdapter(this);

		if (nfc.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			for (ListHelper hp : data)
				Log.d("HI", hp.getName() + " is NFC as " + hp.getAvailability());

			Intent intent = getIntent();
			String name = getNdefMessages(intent);
			if (name == null)
				return;

			Object[] temp = data.toArray();
			data.clear();
			for (int x = 0; x < temp.length; x++) {
				ListHelper help = (ListHelper) temp[x];
				if (help.getName().equals(name)) {
					Log.d("HI", help.getAvailability() + " is equal to? " + R.drawable.gone);
					if (help.getAvailability() == R.drawable.gone) {
						help.setData(help.getName(), help.getRoom(), 2,
								help.getProject());
						Log.d("Hi", "Set " + help.getName() + " In-building. Is now " + help.getAvailability());
					}
					else
						help.setData(help.getName(), help.getRoom(),
								2, help.getProject());
				}
				data.add(help);
			}
		}
		
		setContentView(R.layout.activity_main);
		
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

				Intent i = new Intent(MainActivity.this, DisplayPerson.class);

				i.putExtra("com.example.helloapp.availability",
						helper.getAvailability());
				i.putExtra("com.example.helloapp.name", helper.getName());
				i.putExtra("com.example.helloapp.room", helper.getRoom());
				i.putExtra("com.example.helloapp.project", helper.getProject());

				startActivity(i);
			}

		});
		
		for (ListHelper hp : data)
			Log.d("HI", hp.getName() + " is started as " + hp.getAvailability());
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		for (ListHelper hp : data)
			Log.d("HI", hp.getName() + " is saved as " + hp.getAvailability());

		for (int x = 0; x < data.size(); x++) {
			ListHelper lh = data.get(x);
			Log.d("Hi", "Storing " + lh.getAvailability());
			savedInstanceState.putInt("Availability" + x, lh.getAvailability());
			savedInstanceState.putString("Name" + x, lh.getName());
			savedInstanceState.putString("Room" + x, lh.getRoom());
			savedInstanceState.putString("Project" + x, lh.getProject());
		}

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	private String getNdefMessages(Intent intent) {
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}

		}
		if (msgs == null)
			return null;
		else
			return new String(msgs[0].getRecords()[0].getPayload())
					.substring(3);
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
