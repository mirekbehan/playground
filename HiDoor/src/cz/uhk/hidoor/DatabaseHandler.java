package cz.uhk.hidoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

	/**
	 * @param args
	 */
	/**database*/
	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "hidoor";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_DOOR = "door";
	private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE "+ TABLE_DOOR +"(id varchar(30) NOT NULL PRIMARY KEY,name varchar(55) NOT NULL UNIQUE,securityLevel int(3) NOT NULL,pswd varchar(512) NOT NULL,publicKey varchar(512))";
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOOR);
 
        // Create tables again
        onCreate(db);
	}
	
	public void addDoor(Door d) {
		db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id", d.getId());
		values.put("name", d.getName());
		values.put("securityLevel", d.getSecurityLevel());
		values.put("pswd", d.getPswd());
		values.put("publicKey", d.getPublicKey());
		
		db.insert(TABLE_DOOR, null, values);
		db.close();
	}
	
	public Door getDoorByPassword(String password) {
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = null;
	        cursor = db.query(TABLE_DOOR, new String[] { "id",
	                "name", "securityLevel", "pswd", "publicKey" }, "pswd" + "=?",
	                new String[] { String.valueOf(password) }, null, null, null, null);
	       
	        if (cursor.getCount() > 0) {
	            cursor.moveToFirst();
	            Door d = new Door(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
	        return d; } else { return null;}}
	

}
