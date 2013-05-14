package com.example.helloapp;

import android.util.Log;


/**
 * @author Cody
 * 
 */
public class ListHelper {

	int icon;
	String cellNumber;
	String name;
	String room;
	String project;

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public ListHelper() {
		icon = -1;
		cellNumber = "";
		name = "";
		room = "";
		project = "";
	}

	

	public void setData(String newName, String newRoom, int availability,
			String newProject, String cellNum) {

		name = newName;
		room = newRoom;
		icon = availability;
		project = newProject;
		cellNumber = cellNum;
		
		if (availability == 0) {
			this.room = "Out of Building";
			icon = R.drawable.gone;
		}
		else if (availability == 1)
			icon = R.drawable.busy;
		else if (availability == 2)
			icon = R.drawable.available;

	}

	public String getName() {
		return name;
	}

	public String getRoom() {
		return room;
	}

	public int getAvailability() {
		return icon;
	}

	public String getProject() {
		return project;
	}
	
	public String getCellNumber() {
		return cellNumber;
	}
}
