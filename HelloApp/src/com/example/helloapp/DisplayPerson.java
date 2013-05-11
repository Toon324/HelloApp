package com.example.helloapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DisplayPerson extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();

		int availability = intent.getIntExtra(
				"com.example.helloapp.availability", -1);

		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(25);

		String name = intent.getStringExtra("com.example.helloapp.name");
		String room = intent.getStringExtra("com.example.helloapp.room");
		String project = intent.getStringExtra("com.example.helloapp.project");

		if (availability == R.drawable.gone)
			textView.setText(name + " is not currently in-building. \n \n"
					+ name + " was last working on Project " + project + ".");

		else if (availability == R.drawable.busy)
			textView.setText(name + " is busy in Room " + room + "." + "\n \n"
					+ name + " is currently working on Project " + project
					+ ".");
		else
			textView.setText(name + " is available in Room " + room + "."
					+ "\n \n" + name + " is currently working on Project "
					+ project + ".");

		// Set the text view as the activity layout
		setContentView(textView);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
