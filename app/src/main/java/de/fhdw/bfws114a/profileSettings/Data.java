package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import java.io.InputStream;

import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.data.Profile;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private DataInterface mDataInterface;
	private Profile mOwnProfile;

	private Intent mIntent;
	private final int mRequestCode = 1; //needed to check which Intent is answered
	private Uri mImageUri;          //Path of the Picture
	private Bitmap mBitmap;
	private InputStream mInputStream;

	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);
		mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.setType("image/*");

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

	public Intent getIntent() {
		return mIntent;
	}

	public void setIntent(Intent mIntent) {
		this.mIntent = mIntent;
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	public Uri getImageUri() {
		return mImageUri;
	}

	public void setImageUri(Uri mImageUri) {
		this.mImageUri = mImageUri;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

	public void setInputStream(InputStream iStream) {
		this.mInputStream = iStream;
	}

	public InputStream getInputStream() {
		return mInputStream;
	}


	private void loadProfile(){
		//load own Profile from Database
		setOwnProfile(mDataInterface.getOwnProfile());
	}

	public void saveProfile(Profile newProfile){
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