/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;

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
		this.activity = activity;
		initialize();

	}

	public void initialize(){
		if(db.getProfile(getOwnMacAdress())==null){
			db.writeProfile(getOwnMacAdress(),"Max Mustermann", "Zuhause", null);
		}
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
		return db.getProfile(getOwnMacAdress());
	}

	public void saveOwnProfile(Profile newProfile) {
		db.writeProfile(getOwnMacAdress(),newProfile.getName(), newProfile.getStatus(), newProfile.getImage());
	}

	public String getOwnMacAdress(){
		//http://stackoverflow.com/questions/10650337/how-do-you-retrieve-the-wifi-direct-mac-address
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface ntwInterface : interfaces) {

				if (ntwInterface.getName().equalsIgnoreCase("p2p0")) {
					byte[] byteMac = ntwInterface.getHardwareAddress();
					if (byteMac==null){
						return null;
					}
					StringBuilder strBuilder = new StringBuilder();
					for (int i=0; i<byteMac.length; i++) {
						strBuilder.append(String.format("%02X:", byteMac[i]));
					}

					if (strBuilder.length()>0){
						strBuilder.deleteCharAt(strBuilder.length()-1);
					}

					return strBuilder.toString().toLowerCase();
				}

			}
		} catch (Exception e) {
			Log.d("RICARDO", e.getMessage());
		}
		return null;
	}

	public MacAddressList getKnownMacAdresses(){
		MacAddressList result = new MacAddressList();

		ArrayList<Profile> dbProfiles = db.getAllProfiles();

		for(int i=0; i< dbProfiles.size(); i++){
			result.add(new MacAddress(dbProfiles.get(i).getMac()));
			Log.d("RICARDO", "Known MacAdress " + i + " - "+ dbProfiles.get(i).getMac());
		}

		return result;
	}

	public void addKnownMacAdress(String macAdress){
		db.writeProfile(macAdress,"","",null);
	}

	public void removeKnownMacAdress(String macAdress){
		db.deleteProfile(macAdress);
	}
}