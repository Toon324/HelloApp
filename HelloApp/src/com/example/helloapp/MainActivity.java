package com.example.helloapp;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
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
	String delim = "<break>";

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
				String cellNum = savedInstanceState.getString("CellNum" + x);

				helper.setData(name, room, availability, project, cellNum);
				data.add(helper);
			}
		}

		else {
			try {
				FileInputStream input = openFileInput("people_file");
				Scanner inputScanner = new Scanner(input);
				ListHelper helper = new ListHelper();
				Log.w("INFO",
						"Does inputScanner have next? "
								+ inputScanner.hasNext());
				while (inputScanner.hasNextLine()) {
					String line = inputScanner.nextLine();
					Log.w("INFO", "Line found: " + line);
					Scanner lineScan = new Scanner(line);
					lineScan.useDelimiter(delim);
					while (lineScan.hasNext()) {
						helper = new ListHelper();
						int avail = Integer.parseInt(lineScan.next());
						String name = lineScan.next();
						String room = lineScan.next();
						String project = lineScan.next();
						String cellNum = lineScan.next();
						helper.setData(name, room, avail, project, cellNum);
						data.add(helper);
					}
				}
				for (ListHelper hp : data)
					Log.w("HI",
							hp.getName() + " is inputted as "
									+ hp.getAvailability());
			} catch (Exception e) {
				Log.w("HI", e.toString());
			}
		}

		if (data.size() == 0) {
			ListHelper helper = new ListHelper();
			helper.setData("Cody Swendrowski", "101", 2, "CEA", "2623128163");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Forrest", "Away", 0, "Soil Samples", "1234567890");
			data.add(helper);

			helper = new ListHelper();
			helper.setData("Casey Rogers", "208", 1, "Wall Section", "0987654321");
			data.add(helper);

			for (ListHelper hp : data)
				Log.w("HI",
						hp.getName() + " is created as "
								+ hp.getAvailability());
		}
	}

	public void onStart() {
		super.onStart();

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
				i.putExtra("com.example.helloapp.cellnum", helper.getCellNumber());

				startActivity(i);
			}

		});

		for (ListHelper hp : data)
			Log.w("HI", hp.getName() + " is started as " + hp.getAvailability());
	}

	public void onStop() {
		super.onStop();

		try {
			FileOutputStream fos = openFileOutput("people_file",
					Context.MODE_PRIVATE);
			BufferedOutputStream writer = new BufferedOutputStream(fos);
			for (ListHelper lh : data) {
				writer.write(("" + lh.getAvailability() + delim).getBytes());
				writer.write((lh.getName() + delim).getBytes());
				writer.write((lh.getRoom() + delim).getBytes());
				writer.write((lh.getProject() + delim).getBytes());
				writer.write((lh.getCellNumber() + delim).getBytes());
				writer.flush();
				Log.w("INFO", "Wrote " + lh.getName() + " to file people_file");
			}
			fos.close();
		} catch (Exception e) {
			Log.w("HI", e.getMessage());
		}
	}

	public void onResume() {
		super.onResume();

		nfc = NfcAdapter.getDefaultAdapter(this);

		for (ListHelper hp : data)
			Log.w("HI",
					hp.getName() + " entered resume as " + hp.getAvailability());

		Log.w("Action", getIntent().getAction() + "?"
				+ NfcAdapter.ACTION_NDEF_DISCOVERED);
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {

			for (ListHelper hp : data)
				Log.w("HI", hp.getName() + " is NFC as " + hp.getAvailability());

			Intent intent = getIntent();
			String name = getNdefMessages(intent);
			if (name == null)
				return;

			Object[] temp = data.toArray();
			data.clear();
			for (int x = 0; x < temp.length; x++) {
				ListHelper help = (ListHelper) temp[x];
				if (help.getName().equals(name)) {
					Log.w("HI", help.getAvailability() + " is equal to? "
							+ R.drawable.gone);
					if (help.getAvailability() == R.drawable.gone) {
						help.setData(help.getName(), "101", 2,
								help.getProject(), help.getCellNumber());
						Log.w("HI",
								"Set " + help.getName()
										+ " In-building. Is now "
										+ help.getAvailability());
					} else {
						help.setData(help.getName(), help.getRoom(), 0,
								help.getProject(), help.getCellNumber());
						Log.w("HI",
								"Set " + help.getName()
										+ " out-of-building. Is now "
										+ help.getAvailability());
					}
				}
				data.add(help);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		for (int x = 0; x < data.size(); x++) {
			ListHelper lh = data.get(x);
			Log.w("HI", "Storing " + lh.getAvailability());
			savedInstanceState.putInt("Availability" + x, lh.getAvailability());
			savedInstanceState.putString("Name" + x, lh.getName());
			savedInstanceState.putString("Room" + x, lh.getRoom());
			savedInstanceState.putString("Project" + x, lh.getProject());
			savedInstanceState.putString("CellNum" + x, lh.getCellNumber());
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
			
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse("tel:" + data.get(0).getCellNumber()));
			startActivity(callIntent);
			
		} else if (item.getTitle() == "Text") {
			
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", data.get(0).getCellNumber());
			startActivity(smsIntent);
			
		} else if (item.getTitle() == "Add to Friends List") {
			// Do your working
		} else {
			return false;
		}
		return true;
	}

}
