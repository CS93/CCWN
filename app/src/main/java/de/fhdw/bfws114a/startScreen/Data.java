package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.fhdw.bfws114a.Communication.MacAddressList;
import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.data.ChatMessageList;
import de.fhdw.bfws114a.data.Constants;
import de.fhdw.bfws114a.dataInterface.DataInterface;

public class Data {
	
	private Activity mActivity;
	private DataInterface mDataInterface;
	private ChatMessageList mMessageList;
	private String mCurrentText;
	private boolean mSwitch_online;
	private int mCurrentScrollPosition;
	private MacAddressList mDevicelist;
	private WifiP2pManager.Channel mChannel;
	private WifiP2pManager mManager;
	
	public Data(Bundle b, Activity activity){	
		mActivity = activity;	
		mDataInterface = new DataInterface(activity);

		mManager = (WifiP2pManager) mActivity.getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(mActivity, mActivity.getMainLooper(), null);

		mMessageList = new ChatMessageList();

		loadMessages();
		if(b != null){
			//Activity has been restarted e.g. change from Protrait to Landscape mode
			restoreDataFromBundle(b);
		}
	}

	//getter and setter
	public Activity getActivity() {
		return mActivity;
	}

	public ChatMessageList getMessageList(){
		return mMessageList;
	}

	private void setMessageList(ChatMessageList messageList){
		mMessageList = messageList;
	}

	public String getCurrentText(){
		return mCurrentText;
	}

	public int getCurrentScrollPosition(){
		return mCurrentScrollPosition;
	}

	public DataInterface getDataInterface() {
		return mDataInterface;
	}

	public void setCurrentText(String text){
		mCurrentText = text;
	}

	public void setCurrentScrollPosition(int pos){
		mCurrentScrollPosition = pos;
	}

	private void loadMessages(){
		//load messages from Database
		setMessageList(new ChatMessageList(mDataInterface.getMessagelist()));
	}

	public WifiP2pManager.Channel getChannel() {
		return mChannel;
	}

	public void setChannel(WifiP2pManager.Channel mChannel) {
		this.mChannel = mChannel;
	}

	public WifiP2pManager getManager() {
		return mManager;
	}

	public void setManager(WifiP2pManager mManager) {
		this.mManager = mManager;
	}

	public void setDeviceList(MacAddressList devicelist){
		mDevicelist = devicelist;
	}

	public MacAddressList getDeviceList(){
		return mDevicelist;
	}

	private void restoreDataFromBundle(Bundle b) {
		//restore scrollposition and mCurrentText
		mMessageList = (ChatMessageList) b.getSerializable(Constants.KEY_CURRENT_MESSAGELIST);
		mCurrentText = b.getString(Constants.KEY_CURRENT_TEXT);
		mCurrentScrollPosition = b.getInt(Constants.KEY_CURRENT_SCROLL_POSITION);
		mSwitch_online = b.getBoolean(Constants.KEY_CURRENT_SWITCH_ONLINE);
	}


	public void saveDataInBundle(Bundle b){
		//put the Current String drawable_startscreen_chat_edittext in Bundle
		b.putSerializable(Constants.KEY_CURRENT_MESSAGELIST, mMessageList);
		b.putString(Constants.KEY_CURRENT_TEXT, mCurrentText);
		b.putInt(Constants.KEY_CURRENT_SCROLL_POSITION, mCurrentScrollPosition);
		b.putBoolean(Constants.KEY_CURRENT_SWITCH_ONLINE, mSwitch_online);
	}
	
}