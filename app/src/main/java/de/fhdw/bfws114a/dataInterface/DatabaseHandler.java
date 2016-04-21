/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

	//TEST
	// General variables
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "campusChatDB";

	// Table names
	private static final String TABLE_MESSAGES= "messages";
	private static final String TABLE_SYSTEMDATA= "system";

	//// Table Columns names
	// Table Users
	private static final String KEY_MESSAGES_TIMESTAMP = "timestamp";
	private static final String KEY_MESSAGES_DATA = "data";
	private static final String KEY_MESSAGES_RECIPIENTLIST = "recipients";
	private static final String KEY_MESSAGES_SENDER = "sender";

    // Table System
    private static final String KEY_SYSTEMDATA_KEY = "key";
    private static final String KEY_SYSTEMDATA_VALUE = "value";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableMessages(db);
        createTableSystemdata();
	}
	
	private void createTableMessages(SQLiteDatabase db) {
        String create_users_table =
                "CREATE TABLE " + TABLE_MESSAGES + "("
                        + KEY_MESSAGES_TIMESTAMP + " TEXT PRIMARY KEY,"
                        + KEY_MESSAGES_DATA + " TEXT,"
                        + KEY_MESSAGES_RECIPIENTLIST + " TEXT,"
                        + KEY_MESSAGES_SENDER + " TEXT"
                        + ")";
        db.execSQL(create_users_table);
    }
	
	private void createTableSystemdata(SQLiteDatabase db) {
        String create_userscores_table =
                "CREATE TABLE " + TABLE_SYSTEMDATA + "("
                        + KEY_SYSTEMDATA_KEY + " TEXT PRIMARY KEY,"
                        + KEY_SYSTEMDATA_VALUE + " TEXT"
                        + ")";
        db.execSQL(create_userscores_table);
    }

	private void dropTableMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.close();
    }
	
	private void dropTableSystem(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_SYSTEMDATA);
		db.close();
	}

	public void clearTableMessages(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_MESSAGES);
		db.close();
	}
	
	public void clearTableSystem(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_SYSTEMDATA);
		db.close();
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEMDATA);

		// Create tables again
		onCreate(db);
	}

	/***********************
	 * All Message Operations *
	 ***********************
*/
	// Adding new Message
	void addMessage(String sender, String data, String recipientlist) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MESSAGES_TIMESTAMP, System.currentTimeMillis()); // Timestamp of the Message
		values.put(KEY_MESSAGES_SENDER, sender); // Sender of the Message
		values.put(KEY_MESSAGES_DATA, data); // Data of the Message
		values.put(KEY_MESSAGES_RECIPIENTLIST, recipientlist); // Recipientlist

		// Inserting Row
		db.insert(TABLE_MESSAGES, null, values);
		db.close(); // Closing database connection
	}


	// Deleting single message by timestamp
	public void deleteUser(int timestamp) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MESSAGES, KEY_MESSAGES_TIMESTAMP + " = ?",
				new String[] { String.valueOf(timestamp) });
		db.close();
	}

	public int getMessageCount() {
		// Returns the number of users in the DB.
		String countQuery = "SELECT * FROM " + TABLE_MESSAGES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

    /***********************
     * All Systemdata Operations *
     ***********************
     */
	
	// Adding new Key/Value Pair
    void addSystemdata(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SYSTEMDATA_KEY, key);
        values.put(KEY_SYSTEMDATA_VALUE, value);

        // Inserting Row
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Closing database connection
    }

    // Deleting single Value/Key-Pair by Key
    public void deleteSystemdata(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SYSTEMDATA, KEY_SYSTEMDATA_KEY + " = ?",
                new String[] { key });
        db.close();
    }

    public void updateSystemdata(String key, System newValue){
        String sql=
                "UPDATE " + TABLE_SYSTEMDATA
                        + " SET " + KEY_SYSTEMDATA_VALUE + " = " + newValue
                        + " WHERE " + KEY_SYSTEMDATA_KEY + " = " + key;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public String getValue(String key) {
        String result = "";

        String selectQuery = "SELECT " + KEY_SYSTEMDATA_VALUE + " FROM " + TABLE_SYSTEMDATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            result = cursor.getString(0); //Value
        }
        db.close();

        return result;
    }

}