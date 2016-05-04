package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */


import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.fhdw.bfws114a.R;
import de.fhdw.bfws114a.data.DeviceList;
import de.fhdw.bfws114a.data.Profile;

public class Gui {
	//components of the GUI
	private EditText mEditTextStatus;

	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_deviceoverview);
		mContext = act;
	//	map the intialized components with gui
		mContext = act;
		//mEditTextStatus = (EditText) act.findViewById(R.id.profilesettings_status_edittext);
	}

	//getter and setter for components
	public void setDeviceList(DeviceList devices){
		// sets names of devices in list

	}

	public void setSelectedProfile(String name){
	 // sets name, status, picture from selected Profile to gui

	}
}