package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.data.User;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private ArrayList<User> mUserList; //Eventuell eine Liste verwenden
	private Activity mActivity;
	private DataInterface mDataInterface;
	
	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);
		
		mDataInterface.importXMLtoDB();
		
		if(b == null){
			//Activity has been started the first time
			loadUser(); 
		} else {
			//Activity has been restarted e.g. change from Protrait to Landscape mode
			restoreDataFromBundle(b);
		}
		 
		
	}
	
	public Activity getActivity() {
		return mActivity;
	}

	//Load users from Database through DataInterface and store them in ArrayList mUserList
	protected void loadUser(){
		mUserList = mDataInterface.loadUsers();	
		
	}
	
	public ArrayList<User> getUser(){
		return mUserList;
	}
	private void restoreDataFromBundle(Bundle b) {
		//The Serializable is used to put the User-Object in Bundle
		mUserList = (ArrayList<User>) b.getSerializable(Constants.KEY_USER_LIST);		
		Log.d("Login", mUserList.get(0).getName());
	}


	public void saveDataInBundle(Bundle b, String text, int pos){
		//put the Current String text in Bundle
		b.putString(Constants.KEY_CURRENT_TEXT, text);
		b.putInt(Constants.KEY_CURRENT_SCROLL_POSITION, pos);
	}
	
}