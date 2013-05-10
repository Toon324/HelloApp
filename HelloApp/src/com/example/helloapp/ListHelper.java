/**
 * 
 */
package com.example.helloapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Cody
 * 
 */
public class ListHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "peoplelist.db";
	private static final int SCHEMA_VERSION = 1;

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public ListHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE people (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, room TEXT, availability INT, project TEXT);");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public Cursor getAll() {
		return (getReadableDatabase()
				.rawQuery(
						"SELECT _id, name, room, availability, project FROM people ORDER BY name",
						null));
	}

	public Cursor getById(String id) {
		String[] args = { id };

		return (getReadableDatabase()
				.rawQuery(
						"SELECT _id, name, room, availability, project FROM people WHERE _ID=?",
						args));
	}

	public void insert(String name, String room, int availability,
			String project) {
		ContentValues cv = new ContentValues();

		cv.put("name", name);
		cv.put("room", room);
		cv.put("availability", availability);
		cv.put("project", project);

		getWritableDatabase().insert("people", "name", cv);
	}

	public void update(String id, String name, String room, int availability,
			String project) {
		ContentValues cv = new ContentValues();
		String[] args = { id };

		cv.put("name", name);
		cv.put("room", room);
		cv.put("availability", availability);
		cv.put("project", project);

		getWritableDatabase().update("restaurants", cv, "_ID=?", args);
	}

	public String getName(Cursor c) {
		return (c.getString(1));
	}

	public String getRoom(Cursor c) {
		return (c.getString(2));
	}

	public int getAvailability(Cursor c) {
		return (c.getInt(3));
	}

	public String getProject(Cursor c) {
		return (c.getString(4));
	}
}
