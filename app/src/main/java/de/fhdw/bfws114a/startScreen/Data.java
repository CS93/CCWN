package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.os.Bundle;
import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.data.MessageList;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private DataInterface mDataInterface;
	private MessageList mMessageList;
	private String mCurrentText;
	private int mCurrentScrollPosition;
	
	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);
		
		//mDataInterface.importXMLtoDB();

		loadMessages();
		if(b != null){
			//Activity has been restarted e.g. change from Protrait to Landscape mode
			restoreDataFromBundle(b);
		}
	}
	
	public Activity getActivity() {
		return mActivity;
	}

	public MessageList getMessageList(){
		return mMessageList;
	}

	private void loadMessages(){
		//load messages from Database
	}

	private void restoreDataFromBundle(Bundle b) {
		//restore scrollposition and mCurrentText
		mCurrentText = b.getString(Constants.KEY_CURRENT_TEXT);
		mCurrentScrollPosition = b.getInt(Constants.KEY_CURRENT_SCROLL_POSITION);
	}


	public void saveDataInBundle(Bundle b){
		//put the Current String text in Bundle
		b.putString(Constants.KEY_CURRENT_TEXT, mCurrentText);
		b.putInt(Constants.KEY_CURRENT_SCROLL_POSITION, mCurrentScrollPosition);
	}
	
}