package com.example.batterymonitor;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Stats extends SQLiteOpenHelper{
	private static final String SCRIPT_CREATE_DATABASE = 
			"CREATE TABLE batt (_id INTEGER PRIMARY KEY, datum DATE, " +
			"                       proud INTEGER, procent INTEGER)";

	private static final String DATABASE_NAME = "stats";
	private static final int DATABASE_VERSION = 2;
	
	public Stats(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SCRIPT_CREATE_DATABASE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	
	public void ulozMereni(long proud, int procent){	
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("datum", new Date().toString());
		cv.put("proud", proud);
		cv.put("procent", procent);
		db.insert("batt", null, cv);
	}
	
	public int spocitejVydrz(){
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor result = db.query("batt",  new String[]{"procent"}, null, null, null, null, null, null);
		System.out.print(result.getCount());
		return result.getCount();
	}
}
