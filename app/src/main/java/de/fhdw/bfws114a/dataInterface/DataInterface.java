/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Environment;

import de.fhdw.bfws114a.Communication.MacAddress;
import de.fhdw.bfws114a.Communication.MacAddressList;
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

	public MacAddressList getKnownMacAdresses(){
		MacAddressList result = new MacAddressList();

		ArrayList<String> macadresses = new ArrayList<>();
		macadresses.add("5c:0a:5b_da_83_d3");//s3 Ricardo
		macadresses.add("10:d5:42:96:fc:43");//s3 mini Ricardo
		macadresses.add("be:72:b1:70:67:62");//Carsten 1
		macadresses.add("e6:92:fb:ce:ba:46");//Carsten 2

		for(int i=0; i< macadresses.size(); i++){
			result.add(new MacAddress(macadresses.get(i)));
		}

		return result;
	}
}