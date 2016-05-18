/**
 * @author Ricardo La Valle
 */
package de.fhdw.bfws114a.dataInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Environment;

import de.fhdw.bfws114a.R;
import de.fhdw.bfws114a.data.DeviceList;
import de.fhdw.bfws114a.data.MessageList;
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
		copyDefaultXMLintoExternalFolder();
	}


	// load the default Time in minutes of Classes
	public int[] getDefaultClassDurations() {
		// Returns an Integer Array with Default Class Durations.
		int[] timeToClasses = new int[6];
		// The durations are in the time unit minute (1h = 60mins)
		timeToClasses[0] = 5; // 5mins
		timeToClasses[1] = 60; // 1h
		timeToClasses[2] = 1440; // 1d
		timeToClasses[3] = 10080; // 7d
		timeToClasses[4] = 43200; // 30d
		timeToClasses[5] = 259200; // 180d
		return timeToClasses;
	}


	// If there is no XML-File in the external Storage, the default file from the Project-Directory will be copied.
	public void copyDefaultXMLintoExternalFolder() {
		String uri = Environment.getExternalStorageDirectory().toString() + FILEPATH;
		java.io.File xmlFile = new java.io.File(uri, FILENAME);

		if (!xmlFile.getParentFile().exists()) {
			try {
				xmlFile.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!xmlFile.exists()) {
			try {
				if (xmlFile.createNewFile()) {
					InputStream is = activity.getAssets().open("karteien.xml");
					OutputStream out = new FileOutputStream(xmlFile);
					byte[] buf = new byte[1024];
					int len;
					while ((len = is.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.close();
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public MessageList getMessagelist() {
		//Get List of all Messages
		return null;
	}

	public Profile getOwnProfile() {
		//get own Profile from DB (name, status, picture)
		return new Profile(null, "Otto", "Im Kino");
	}

	public void saveOwnProfile(Profile newProfile) {
		//save the own Profile in DB
	}
}