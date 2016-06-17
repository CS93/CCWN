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
		db.initialize();
		this.activity = activity;
	}


	public ArrayList<ChatMessage> getMessagelist() {
		//DONE - CHECKED
		//Get List of all Messages
		return db.getAllMessages();
	}

	public void addMessageToDB(ChatMessage m){
		//DONE - CHECKED
		db.addMessage(Boolean.toString(m.left), m.message);//add a message to DB
	}

	public Profile getOwnProfile() {
		//get own Profile from DB (name, status, picture)
		return db.getProfile(1);
	}

	public void saveOwnProfile(Profile newProfile) {
		db.writeProfile(1,newProfile.getName(), newProfile.getStatus(), newProfile.getImage());
	}
}