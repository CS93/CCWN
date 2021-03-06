package de.fhdw.bfws114a.dataInterface;

import java.io.ByteArrayOutputStream;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import de.fhdw.bfws114a.data.MacAddress;
import de.fhdw.bfws114a.data.MacAddressList;
import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.data.Profile;

/**
 * Created by Ricardo La Valle.
 */

public class DataInterface {

	private DatabaseHandler db;
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
		db.addMessage(Boolean.toString(m.isLeft()), m.getMessage());//add a message to DB
	}

	public Profile getOwnProfile() {
		//get own Profile from DB (name, status, picture)
		return db.getProfile(getOwnMacAdress());
	}

	public void saveOwnProfile(Profile newProfile) {
		if(!newProfile.getMac().equals("00:00:00:00:00:00")) db.writeProfile(getOwnMacAdress(),newProfile.getName(), newProfile.getStatus(), newProfile.getImage());
	}

	public Profile getProfile(String macAdress){
		return db.getProfile(macAdress);
	}

	public String getOwnMacAdress(){
		//http://stackoverflow.com/questions/10650337/how-do-you-retrieve-the-wifi-direct-mac-address
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface ntwInterface : interfaces) {

				if (ntwInterface.getName().equalsIgnoreCase("p2p0")) {
					byte[] byteMac = ntwInterface.getHardwareAddress();
					if (byteMac==null){
						return "00:00:00:00:00:00";
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
		}
		return "00:00:00:00:00:00";
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
		if(!macAdress.equalsIgnoreCase("00:00:00:00:00:00"))db.writeProfile(macAdress,"No Name","No Status",null);
	}

	public void removeKnownMacAdress(String macAdress){
		db.deleteProfile(macAdress);
	}

	/************************************
	 * IMAGE CONVERSION AND COMPRESSION *
	 ************************************
	 */

	public byte[] convertDrawableToByteArray(Drawable picture){
		if(picture!=null) {
			Bitmap bitmap = ((BitmapDrawable) picture).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
			byte[] bitmapdata = stream.toByteArray();
			return bitmapdata;
		}
		else {
			return null;
		}
	}
}