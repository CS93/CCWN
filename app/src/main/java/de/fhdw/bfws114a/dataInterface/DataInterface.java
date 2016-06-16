/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Environment;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.data.Profile;

public class DataInterface {

	private final static String FILEPATH = "/android/data/de.fhdw.LernKartei/";
	private final static String FILENAME = "karteien.xml";

	private DatabaseHandler db;
	private int[] timeToClasses;
	private Activity activity;

	public DataInterface(Activity activity) {
		db = new DatabaseHandler(activity);
		this.activity = activity;
	}


	public ArrayList<ChatMessage> getMessagelist() {

		//Get List of all Messages
		return db.getAllMessages();
	}

	public void addMessageToDB(ChatMessage m){
		db.addMessage(Boolean.toString(m.left), m.message);//add a message to DB
	}

	public Profile getOwnProfile() {
		//get own Profile from DB (name, status, picture)
		return new Profile(null, "Otto", "Im Kino");
	}

	public void saveOwnProfile(Profile newProfile) {
		//save the own Profile in DB
	}
}