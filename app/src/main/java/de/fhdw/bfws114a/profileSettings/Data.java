package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.os.Bundle;

import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.data.Profile;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private DataInterface mDataInterface;
	private Profile mOwnProfile;
	
	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);

		loadProfile();
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

	public Profile getOwnProfile() {
		return mOwnProfile;
	}

	public void setOwnProfile(Profile mOwnProfile) {
		this.mOwnProfile = mOwnProfile;
	}

	private void loadProfile(){
		//load own Profile from Database
		setOwnProfile(mDataInterface.getOwnProfile());
	}

	private void saveProfile(Profile newProfile){
		//save the new Profile in DB
		mDataInterface.saveOwnProfile(newProfile);
	}

	private void restoreDataFromBundle(Bundle b) {
		//restore profile
		mOwnProfile = (Profile) b.getSerializable(Constants.KEY_CURRENT_OWN_PROFILE);
	}


	public void saveDataInBundle(Bundle b){
		//put the Current Profile in Bundle
		b.putSerializable(Constants.KEY_CURRENT_OWN_PROFILE, mOwnProfile);
	}
	
}