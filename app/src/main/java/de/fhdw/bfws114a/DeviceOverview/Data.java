package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.os.Bundle;

import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.data.DeviceList;
import de.fhdw.bfws114a.data.Profile;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private DataInterface mDataInterface;
	private DeviceList mDevices;
	private String mSelectedDevice;
	
	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);

		if(b != null){
			//Activity has been restarted e.g. change from Protrait to Landscape mode
			restoreDataFromBundle(b);
		}
	}

	//getter and setter
	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	public DeviceList getDevices() {
		return mDevices;
	}

	public void setDevices(DeviceList mDevices) {
		this.mDevices = mDevices;
	}

	public void setSelectedDevice(String device){
		mSelectedDevice = device;
	}
	private void restoreDataFromBundle(Bundle b) {
		//restore profile
		mDevices = (DeviceList) b.getSerializable(Constants.KEY_CURRENT_DEVICELIST);
	}


	public void saveDataInBundle(Bundle b){
		//put the Current Profile in Bundle
		b.putSerializable(Constants.KEY_CURRENT_DEVICELIST, mDevices);
	}
	
}