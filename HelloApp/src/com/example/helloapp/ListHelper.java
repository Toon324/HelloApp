package com.example.helloapp;


/**
 * @author Cody
 * 
 */
public class ListHelper {

	int icon;
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
		name = "";
		room = "";
		project = "";
	}

	

	public void setData(String name, String room, int availability,
			String project) {

		this.name = name;
		this.room = room;
		this.icon = availability;
		this.project = project;
		
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
}
