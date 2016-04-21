/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import java.sql.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

	//TEST
	// General variables
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "karteikartenDB";
	
	private static final int CLASS1_DEFAULTDURATION = 1;	//1 Minute
	private static final int CLASS2_DEFAULTDURATION = 5;	//5 Minute
	private static final int CLASS3_DEFAULTDURATION = 10;	//10 Minute
	private static final int CLASS4_DEFAULTDURATION = 30;	//30 Minute
	private static final int CLASS5_DEFAULTDURATION = 60;	//1 Stunde
	private static final int CLASS6_DEFAULTDURATION = 1440;	//1 Tag


	// Table name
	private static final String TABLE_USERS= "users";
	private static final String TABLE_USERSCORES = "userscores";
	private static final String TABLE_FILES = "cardfiles";
	private static final String TABLE_CARDS = "cards";

	//// Table Columns names
	// Table Users
	private static final String KEY_USERS_USERID = "userid";
	private static final String KEY_USERS_USERNAME = "username";
	private static final String KEY_USERS_CLASS1_DURATION = "class1_duration";
	private static final String KEY_USERS_CLASS2_DURATION = "class2_duration";
	private static final String KEY_USERS_CLASS3_DURATION = "class3_duration";
	private static final String KEY_USERS_CLASS4_DURATION = "class4_duration";
	private static final String KEY_USERS_CLASS5_DURATION = "class5_duration";
	private static final String KEY_USERS_CLASS6_DURATION = "class6_duration";
	private static final String KEY_USERS_LAST_SEEN = "last_seen";

	// Table UserScores
	private static final String KEY_USERSCORES_USERID = "fk_userid";
	private static final String KEY_USERSCORES_FILEID= "fk_fileid";
	private static final String KEY_USERSCORES_CARDID= "fk_cardid";
	private static final String KEY_USERSCORES_ASSIGNEDCLASS= "assignedClass";
	private static final String KEY_USERSCORES_TIMESTAMP= "timestamp";

	// Table Files
	private static final String KEY_FILES_FILEID= "fileid";
	private static final String KEY_FILES_FILENAME= "filename";
	
	// Table Cards
	private static final String KEY_CARDS_CARDID= "cardid";
	private static final String KEY_CARDS_FILEID= "fk_fileid";
	private static final String KEY_CARDS_TYPE= "type";
	private static final String KEY_CARDS_QUESTION= "question";
	private static final String KEY_CARDS_ANSWER1= "answer1";
	private static final String KEY_CARDS_ANSWER2= "answer2";
	private static final String KEY_CARDS_ANSWER3= "answer3";
	private static final String KEY_CARDS_ANSWER4= "answer4";
	private static final String KEY_CARDS_ANSWER5= "answer5";
	private static final String KEY_CARDS_ANSWER6= "answer6";
	private static final String KEY_CARDS_SOLUTION= "solution";
	
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		createUsersTable(db);
		createCardsTable(db);
		createUserScoresTable(db);
		createFilesTable(db);
	}
	
	private void createUsersTable(SQLiteDatabase db){
		String create_users_table = 
				"CREATE TABLE " + TABLE_USERS + "("
				+ KEY_USERS_USERID + " INTEGER PRIMARY KEY," 
				+ KEY_USERS_USERNAME + " TEXT,"
				+ KEY_USERS_CLASS1_DURATION + " INTEGER," 
				+ KEY_USERS_CLASS2_DURATION + " INTEGER," 
				+ KEY_USERS_CLASS3_DURATION + " INTEGER," 
				+ KEY_USERS_CLASS4_DURATION + " INTEGER," 
				+ KEY_USERS_CLASS5_DURATION + " INTEGER," 
				+ KEY_USERS_CLASS6_DURATION + " INTEGER,"
				+ KEY_USERS_LAST_SEEN + " TEXT"
				+ ")";
		db.execSQL(create_users_table);
	}
	
	private void createUserScoresTable(SQLiteDatabase db){
		String create_userscores_table = 
				"CREATE TABLE " + TABLE_USERSCORES + "("
				+ KEY_USERSCORES_USERID + " INTEGER," 
				+ KEY_USERSCORES_FILEID + " INTEGER," 
				+ KEY_USERSCORES_CARDID + " INTEGER,"  
				+ KEY_USERSCORES_ASSIGNEDCLASS + " INTEGER,"
				+ KEY_USERSCORES_TIMESTAMP + " TEXT,"
				+ "PRIMARY KEY ("
				+ KEY_USERSCORES_USERID + ","
				+ KEY_USERSCORES_FILEID + ","
				+ KEY_USERSCORES_CARDID + ")"
				+ ")";
		db.execSQL(create_userscores_table);
	}
	
	private void createCardsTable(SQLiteDatabase db){
		String create_cards_table = 
				"CREATE TABLE " + TABLE_CARDS + "("
				+ KEY_CARDS_CARDID+ " INTEGER,"
				+ KEY_CARDS_FILEID+ " INTEGER,"
				+ KEY_CARDS_TYPE + " INTEGER," 
				+ KEY_CARDS_QUESTION + " TEXT,"
				+ KEY_CARDS_ANSWER1 + " TEXT,"
				+ KEY_CARDS_ANSWER2 + " TEXT,"
				+ KEY_CARDS_ANSWER3 + " TEXT,"
				+ KEY_CARDS_ANSWER4 + " TEXT,"
				+ KEY_CARDS_ANSWER5 + " TEXT,"
				+ KEY_CARDS_ANSWER6 + " TEXT,"
				+ KEY_CARDS_SOLUTION + " TEXT,"
				+ "PRIMARY KEY ("
				+ KEY_CARDS_CARDID + ","
				+ KEY_CARDS_FILEID + ")"
				+ ")";
		db.execSQL(create_cards_table);
	}
	
	private void createFilesTable(SQLiteDatabase db){
		String create_files_table = 
				"CREATE TABLE " + TABLE_FILES + "("
				+ KEY_FILES_FILEID + " INTEGER PRIMARY KEY," 
				+ KEY_FILES_FILENAME + " TEXT"
				+ ")";
		db.execSQL(create_files_table);
	}
	
	
	private void dropUserTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
		db.close();
	}
	
	private void dropFileTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_FILES);
		db.close();
	}
	
	private void dropCardTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARDS);
		db.close();
	}
	
	private void dropUserscoresTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
		db.close();
	}

	public void clearUserTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_USERS);
		db.close();
	}
	
	public void clearFileTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_FILES);
		db.close();
	}
	
	public void clearCardTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_CARDS);
		db.close();
	}
	
	public void clearUserscoreTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_USERSCORES);
		db.close();
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERSCORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);

		// Create tables again
		onCreate(db);
	}

	/***********************
	 * All USER Operations *
	 ***********************
*/
	// Adding new user
	void addUser(String username) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERS_USERNAME, username); // User Name
		values.put(KEY_USERS_CLASS1_DURATION, CLASS1_DEFAULTDURATION); // Class1 Duration
		values.put(KEY_USERS_CLASS2_DURATION, CLASS2_DEFAULTDURATION); // Class2 Duration
		values.put(KEY_USERS_CLASS3_DURATION, CLASS3_DEFAULTDURATION); // Class3 Duration
		values.put(KEY_USERS_CLASS4_DURATION, CLASS4_DEFAULTDURATION); // Class4 Duration
		values.put(KEY_USERS_CLASS5_DURATION, CLASS5_DEFAULTDURATION); // Class5 Duration
		values.put(KEY_USERS_CLASS6_DURATION, CLASS6_DEFAULTDURATION); // Class6 Duration

		// Inserting Row
		db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
	}

	
	public void updateUserClasses(String user, int class1, int class2, int class3, int class4, int class5, int class6){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_USERS_CLASS1_DURATION, class1); 
		values.put(KEY_USERS_CLASS2_DURATION, class2); 
		values.put(KEY_USERS_CLASS3_DURATION, class3); 
		values.put(KEY_USERS_CLASS4_DURATION, class4); 
		values.put(KEY_USERS_CLASS5_DURATION, class5); 
		values.put(KEY_USERS_CLASS6_DURATION, class6); 
		String whereClause = KEY_USERS_USERNAME + " = ?"; 
		String[] whereArgs = new String[] {user};		
		db.update(TABLE_USERS, values, whereClause, whereArgs);
		db.close();
	}

	// Deleting single user by ID
	public void deleteUser(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, KEY_USERS_USERID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}
	
	public void deleteUser(String name) {
		//Deletes a user by his name - ATTENTION: ALL USERS WITH THIS NAME WILL BE DELETED
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, KEY_USERS_USERNAME + " = ?",
				new String[] { name });
		db.close();
	}

	public int getUserCount() {
		// Returns the number of users in the DB.
		String countQuery = "SELECT * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	//***********************
	//* All FILE Operations *
	//***********************
	
	// Adding new File

	
		public int getFileID(String name){
			SQLiteDatabase db = this.getReadableDatabase();

			String selection = KEY_FILES_FILENAME + " = ?"; 
			String[] selectionArgs = new String[] {name};
			String[] columns = new String[] {KEY_FILES_FILEID};
			
			Cursor cursor = db.query(TABLE_FILES, columns, selection, selectionArgs, null, null, null);
			if(cursor.moveToFirst()){
				return Integer.parseInt(cursor.getString(0));
			}
			else return -1;		
		}
		

	
	/****************************
	 * All USERSCORE Operations *
	 ****************************
*/
		

}