/**
 * 
 */
package com.example.helloapp;

import java.util.ArrayList;

import android.content.Context;
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
		data = (ArrayList<ListHelper>) values.clone();
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
		
		image.setImageResource(msg.getAvailability());
		
		nameView.setText(msg.getName());
		roomView.setText(msg.getRoom());
		projectView.setText(msg.getProject());

		return rowView;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int pos) {
		return data.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
