/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;
/**
 * Created by Ricardo La Valle.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.data.Profile;


public class DatabaseHandler extends SQLiteOpenHelper {

	//TEST
	// General variables
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "campusChatDB";

	// Table names
	private static final String TABLE_MESSAGES= "messages";
	private static final String TABLE_SYSTEMDATA= "system";
	private static final String TABLE_PROFILES= "profiles";


	//// Table Columns names
	// Table Messages
	private static final String KEY_MESSAGES_TIMESTAMP = "timestamp";
	private static final String KEY_MESSAGES_DATA = "data";
	private static final String KEY_MESSAGES_SENDER = "sender";

    // Table System
    private static final String KEY_SYSTEMDATA_KEY = "key";
    private static final String KEY_SYSTEMDATA_VALUE = "value";

	// Table Profiles
	private static final String KEY_PROFILES_MAC = "mac";
	private static final String KEY_PROFILES_NAME = "name";
	private static final String KEY_PROFILES_STATUS = "status";
	private static final String KEY_PROFILES_PICTURE = "picture";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableMessages(db);
        createTableSystemdata(db);
		createTableProfiles(db);
	}

	private void createTableMessages(SQLiteDatabase db) {
        String create_users_table =
                "CREATE TABLE " + TABLE_MESSAGES + "("
                        + KEY_MESSAGES_TIMESTAMP + " TEXT PRIMARY KEY,"
                        + KEY_MESSAGES_DATA + " TEXT,"
                        + KEY_MESSAGES_SENDER + " TEXT"
                        + ")";
        db.execSQL(create_users_table);
    }
	
	private void createTableSystemdata(SQLiteDatabase db) {
        String create_systemdata_table =
                "CREATE TABLE " + TABLE_SYSTEMDATA + "("
                        + KEY_SYSTEMDATA_KEY + " TEXT PRIMARY KEY,"
                        + KEY_SYSTEMDATA_VALUE + " TEXT"
                        + ")";
        db.execSQL(create_systemdata_table);
    }

	private void createTableProfiles(SQLiteDatabase db) {
		String create_profiles_table =
				"CREATE TABLE " + TABLE_PROFILES + "("
						+ KEY_PROFILES_MAC + " TEXT PRIMARY KEY,"
						+ KEY_PROFILES_NAME + " TEXT,"
						+ KEY_PROFILES_STATUS + " TEXT,"
						+ KEY_PROFILES_PICTURE + " BLOB"
						+ ")";
		db.execSQL(create_profiles_table);
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

	private void dropTableProfiles(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROFILES);
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

	public void clearTableProfiles(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_PROFILES);
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
	void addMessage(String sender, String data) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MESSAGES_TIMESTAMP, System.currentTimeMillis()); // Timestamp of the Message
		values.put(KEY_MESSAGES_SENDER, sender); // Sender of the Message
		values.put(KEY_MESSAGES_DATA, data); // Data of the Message

		// Inserting Row
		db.insert(TABLE_MESSAGES, null, values);
		Log.d("Database", "DB: The following Message was added to the DB " + data);
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

	public ArrayList<ChatMessage> getAllMessages(){
		ArrayList<ChatMessage> result = new ArrayList<ChatMessage>();

		String selectQuery = "SELECT * FROM " + TABLE_MESSAGES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list

		if (cursor.moveToFirst()) {
			for(int i=0;i<cursor.getCount();i++){
				boolean left;
				if(cursor.getString(2).equalsIgnoreCase("true")) left=true;
				else left=false;

				ChatMessage m=new ChatMessage(left,cursor.getString(1));

				result.add(m);

				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return result;

	}

    /***********************
     * All Systemdata Operations *
     ***********************
     */
	
	// Adding new Key/Value Pair
    public void addSystemdata(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SYSTEMDATA_KEY, key);
        values.put(KEY_SYSTEMDATA_VALUE, value);

        // Inserting Row
        db.insert(TABLE_SYSTEMDATA, null, values);
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


	/***********************
	 * All Profile Operations *
	 ***********************
	 */

	// Adding new Profile Pair
	private void addProfile(String mac, String name, String status, Drawable picture) {

		Log.d("Database", "DB: The following Profile was added to the DB: " + name);

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PROFILES_MAC, mac);
		values.put(KEY_PROFILES_NAME, name);
		values.put(KEY_PROFILES_STATUS, status);

		if(picture!=null){
			values.put(KEY_PROFILES_PICTURE, convertDrawableToByteArray(picture));
		}

		// Inserting Row
		db.insert(TABLE_PROFILES, null, values);
		db.close(); // Closing database connection
	}


    private void updateProfileByMac(String mac , String newName, String newStatus, Drawable newPicture){

        Log.d("Database", "DB: The following Profile was updated to the DB: " + mac);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILES_NAME, newName);
        values.put(KEY_PROFILES_STATUS, newStatus);

        if(newPicture!=null){
            values.put(KEY_PROFILES_PICTURE, convertDrawableToByteArray(newPicture));
        }

        // Inserting Row
        db.update(TABLE_PROFILES, values, "mac = ?", new String[] {mac});
        db.close(); // Closing database connection
    }

    public void writeProfile(String mMac, String mName, String mStatus, Drawable mPicture){
        if(getProfile(mMac)==null) addProfile(mMac, mName,mStatus,mPicture);
        else updateProfileByMac(mMac, mName, mStatus, mPicture);
    }

    public Profile getProfile(String mac) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PROFILES, new String[]{
                        KEY_PROFILES_MAC,
						KEY_PROFILES_NAME,
						KEY_PROFILES_STATUS,
						KEY_PROFILES_PICTURE
				},
				KEY_PROFILES_MAC + "=?",
				new String[]{mac}, null, null, null);
        if (cursor.moveToFirst()) {
            return new Profile(cursor.getString(0), cursor.getString(1), cursor.getString(2), null);
        }
        else return null;

	}

    public void deleteProfile(String mac) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILES, KEY_PROFILES_MAC + " = ?",
                new String[] { mac });
        db.close();
    }

    public int getProfilCount() {
        // Returns the number of users in the DB.
        String countQuery = "SELECT * FROM " + TABLE_PROFILES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public ArrayList<Profile> getAllProfiles(){
        ArrayList<Profile> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PROFILES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            for(int i=0;i<cursor.getCount();i++){
                boolean left;
                if(cursor.getString(2).equalsIgnoreCase("true")) left=true;
                else left=false;

                Profile p=new Profile(cursor.getString(0), cursor.getString(1), cursor.getString(2), null);
                result.add(p);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return result;
    }

	/************************************
	 * IMAGE CONVERSION AND COMPRESSION *
	 ************************************
	 */

    private byte[] convertDrawableToByteArray(Drawable picture){
		if(picture!=null){
            Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            return bitmapdata;
        }
        else {
            byte[] result = new byte[1];
            return result;
        }

	}

	/*Drawable convertByteArrayToDrawable(byte[] picture){

	}
	*/
}