/**
 * 
 */
package com.example.helloapp;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Cody
 * 
 */
public class ListAdapter extends BaseAdapter {

	private final Context context;
	private final ArrayList<ListHelper> data;

	public ListAdapter(ArrayList<ListHelper> values, Context context) {
		this.context = context;
		data = values;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = vi.inflate(R.layout.row_layout, null);
		}

		ImageView image = (ImageView) rowView.findViewById(R.id.icon);
		TextView nameView = (TextView) rowView.findViewById(R.id.Name);
		TextView roomView = (TextView) rowView.findViewById(R.id.Room);
		TextView projectView = (TextView) rowView.findViewById(R.id.Project);

		ListHelper msg = data.get(position);
		Cursor c = msg.getAll();
		image.setImageResource(msg.getAvailability(c));
		nameView.setText(msg.getName(c));
		roomView.setText(msg.getRoom(c));
		projectView.setText(msg.getProject(c));

		return rowView;
	}
}
