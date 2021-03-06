package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Samira Schorre/Ricardo La Valle.
 */

import android.app.Activity;
import android.os.Bundle;

import de.fhdw.bfws114a.data.MacAddressList;
import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private MacAddressList mDevicelist;
	private DataInterface mDataInterface;
	private String mSelectedMacAdress;

	public Data(Bundle b, Activity activity, MacAddressList devicelist){
		mActivity = activity;
		mDevicelist = devicelist;
		if(b != null){
		//Activity has been restarted e.g. change from Protrait to Landscape mode
			restoreDataFromBundle(b);
		}
		mDataInterface = new DataInterface(activity);
	}

	//getter and setter
	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	public void setDevicelist(MacAddressList mDevicelist) {
		this.mDevicelist = mDevicelist;
	}

	public void setSelectedMacAdress(String mSelectedMacAdress) {this.mSelectedMacAdress = mSelectedMacAdress; }

	public MacAddressList getDevicelist() {
		return mDevicelist;
	}

	public String getmSelectedMacAdress() { return mSelectedMacAdress; }

	private void restoreDataFromBundle(Bundle b) {
		//restore profile
		mDevicelist = (MacAddressList) b.getSerializable(Constants.KEY_CURRENT_DEVICELIST);
	}

	public void saveDataInBundle(Bundle b){
		//put the Current Profile in Bundle
		b.putSerializable(Constants.KEY_CURRENT_DEVICELIST, mDevicelist);
	}

	public DataInterface getDataInterface(){
		return mDataInterface;
	}
}