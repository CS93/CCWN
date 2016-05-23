package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.*;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import de.fhdw.bfws114a.Communication.ClientAsyncTask;
import de.fhdw.bfws114a.Communication.MacAddress;
import de.fhdw.bfws114a.Communication.MacAddressList;
import de.fhdw.bfws114a.Communication.ServerAsyncTask;
import de.fhdw.bfws114a.Navigation.Navigation;
import de.fhdw.bfws114a.data.ChatMessage;

public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	private BroadcastReceiver mReceiver = null;
	private final IntentFilter mIntentFilter;
	private Listener mListener;
	private WifiP2pDeviceList mWifiP2pDeviceList;
	private WifiP2pInfo mWifiP2pInfo;

	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;

		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		mWifiP2pDeviceList = null;
		mWifiP2pInfo = null;
		mListener = new Listener(this, mGui); // init listener


		applyDataToGui();
	}

	private void applyDataToGui() {
		mGui.setMessages(mData.getMessageList());
	}

	private ArrayList<String> getAddressList(WifiP2pDeviceList wifiP2pDeviceList){
		ArrayList<String> addressList;

		addressList = new ArrayList<String>();
		for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
			addressList.add(wifiP2pDevice.deviceAddress);
		}
		return addressList;
	}

	public void onSendButtonClicked(){
		//send Data to everyone in your Peerslist
		//Test whether there are any peers availabe
		if(mData.getDeviceList() != null) {
			Log.d("Communication", "Number of Device: " + mData.getDeviceList().size());
			if (mData.getDeviceList().size() > 0) {
				for (MacAddress currentDevice : mData.getDeviceList()) {
					//Connect -------------------------------
					//String address;
					//address = getAddressList(mWifiP2pDeviceList).get(0);
					Log.d("Communication", "try to connect() to " + currentDevice.getMacAddress());
					WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
					wifiP2pConfig.deviceAddress = currentDevice.getMacAddress();
					wifiP2pConfig.wps.setup = WpsInfo.PBC;
					mData.getManager().connect(mData.getChannel(), wifiP2pConfig, mListener.getConnectActionListener());
					//--------------------------------------------

					//Hier brauchen wir ein Protokoll um richtigen Text beim senden zu übergeben (Adressliste + Text)
					//SimpleDataExchange.send(a, mGui.getEditText().getText())
					if (mWifiP2pInfo.groupFormed && mWifiP2pInfo.isGroupOwner) {
						// the group owner acts as server
						new ServerAsyncTask(mData.getActivity(), mGui, mGui.getEditText().getText().toString()).execute();
					} else {
						if (mWifiP2pInfo.groupFormed) {
							// the other device acts as the client
							new ClientAsyncTask(mData.getActivity(), mGui, mWifiP2pInfo.groupOwnerAddress.getHostAddress(), mGui.getEditText().getText().toString()).execute();
						}
					}
					mData.getManager().cancelConnect(mData.getChannel(), mListener.getConnectActionListener());
				}
			}
		}else {
			Log.d("Communication", "Devicelist is null");
		}
		//2do: Nachricht in db schreiben
		//add message to gui (true because the standard would be the left side and the messages of the own user used to be on right side)
		mGui.getChatArrayAdapter().add(new ChatMessage(true, mGui.getEditText().getText().toString()));
		mGui.getEditText().setText("");
	}


	public void onSettingsButtonClicked(){
		//go to settings screen
		Navigation.startActivity(mData.getActivity(), Navigation.ACTIVITY_ProfileSettings_CLASS);
	}

	public void onDeviceButtonClicked(){
		//go to member overview screen
		Navigation.startActivity(mData.getActivity(), Navigation.ACTIVITY_DeviceOverview_CLASS);
	}

	public void onOnlineStatusChanged(boolean isChecked){
		//start/stop background process or delete/initialize listeners
		if(isChecked) {
			//on has been selected --> register receiver
			//SimpleDataExchange.onRestart()

			mReceiver = new WifiDirectBroadcastReceiver(this);
			mData.getActivity().registerReceiver(mReceiver, mIntentFilter);

			Log.d("Communication", "Online --> call discoverPeers()");
			mData.getManager().discoverPeers(mData.getChannel(), mListener.getDiscoverPeersActionListener());
		}else{
			//off has been selected --> unregister receiver
			//SimpleDataExchange.onPause()
			mData.getActivity().unregisterReceiver(mReceiver);
			mReceiver = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					Log.d("Communication", "Offline --> call stopPeerDiscovery()");
					mData.getManager().stopPeerDiscovery(mData.getChannel(), mListener.getStopDiscoveryActionListener());
			} else {
				Log.d("Communication", "The Api Level of the current device is to low");
			}
		}
	}


	public void onResume(){
		// register the BroadcastReceiver with the intent values to be matched
		if(mGui.getSwitch().isChecked()){
			mReceiver = new WifiDirectBroadcastReceiver(this);
			mData.getActivity().registerReceiver(mReceiver, mIntentFilter);
			Log.d("Communication", "Online --> call discoverPeers()");
			mData.getManager().discoverPeers(mData.getChannel(), mListener.getDiscoverPeersActionListener());
		}

	}

	public void onPause(){
		mData.getActivity().unregisterReceiver(mReceiver);
		mReceiver = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			Log.d("Communication", "Offline --> call stopPeerDiscovery()");
			mData.getManager().stopPeerDiscovery(mData.getChannel(), mListener.getStopDiscoveryActionListener());
		} else {
			Log.d("Communication", "The Api Level of the current device is to low");
		}
	}

	public void onPeersAvailable(WifiP2pDeviceList peers){
		if (peers.getDeviceList().size() == 0) {
			Log.d("Communication", "No devices found");
			return;
		} else {
			Log.d("Communication", "onPeersAvailable() called");
			for (WifiP2pDevice wd : peers.getDeviceList()) {
				Log.d("Communication","     " + wd.deviceName);
			}
			mWifiP2pDeviceList = peers;
			WifiP2pDevice device;
			MacAddressList newDeviceList = new MacAddressList();
			// Out with the old, in with the new.
			Iterator it = peers.getDeviceList().iterator();
			while(it.hasNext()){
				device = (WifiP2pDevice) it.next();
				MacAddress newDevice = new MacAddress(device.deviceAddress);
				newDeviceList.add(newDevice);
				Log.d("Communication","     This device has been added to DevicList: " + newDevice.getMacAddress());
			}
			mData.setDeviceList(newDeviceList);
		}
	}

	public void onConnectionInfoAvailable(WifiP2pInfo info){
		Log.d("Communication","onConnectionInfoAvailable() called:");
		Log.d("Communication","    groupFormed: " + info.groupFormed);
		Log.d("Communication","    isGroupOwner: " + info.isGroupOwner);
		Log.d("Communication","    groupOwnerAddress: " + info.groupOwnerAddress);
		Log.d("Communication","    IP address of group owner: " + info.groupOwnerAddress.getHostAddress());
		mWifiP2pInfo = info;
	}

	public void onDiscoverPeersSuccess(){
		Log.d("Communication","onDiscoverPeersSuccess() called");
	}

	public void onDiscoverPeersFailure(int reason){
		Log.d("Communication","onDiscoverPeersFailure() called: reason: " + reason);
		//may alert user
	}

	public void onStopDiscoverySuccess(){
		Log.d("Communication","onStopDiscoverySuccess() called");
	}

	public void onStopDiscoveryFailure(int reason){
		Log.d("Communication","onStopDiscoveryFailure() called: reason: " + reason);
	}

	public void onConnectSuccess(){
		Log.d("Communication","onConnectSuccess() called");
	}

	public void onConnectFailure(int reason){
		Log.d("Communication","onConnectFailure() called: reason: " + reason);
	}

	public void onP2pStateChangedAction(int state){
		Log.d("Communication","onP2pStateChangedAction() called: state: " + state);
		if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
			// Wifi Direct mode has been enabled
			Log.d("Communication","    WIFI_P2P_STATE_CHANGED_ACTION wifi enabled");
		} else {
			Log.d("Communication","    WIFI_P2P_STATE_CHANGED_ACTION wifi disabled");
		}
	}

	public void onP2pPeersChangedAction(){
		// The peer list has changed! --> send Request to available peers from the wifi p2p manager (asynchronous call and callback at onPeersAvailable()
		Log.d("Communication","onP2pPeersChangedAction() called");
		if (mData.getManager() != null) {
			Log.d("Communication","    calling requestPeers()");
			mData.getManager().requestPeers(mData.getChannel(), mListener.getPeerListListener());
		}
	}

	public void onP2pConnectionChangedAction(NetworkInfo networkInfo){
		Log.d("Communication","onP2pConnectionChangedAction() called: networkInfo:");
		for (String s : networkInfo.toString().split(", ")) {
			Log.d("Communication","    " + s);
		}
		if (mData.getManager() != null) {
			if (networkInfo.isConnected()) {
				// we are connected with the other device, request connection
				// info to find group owner IP
				Log.d("Communication","    device is connected: calling requestConnectionInfo()");
				mData.getManager().requestConnectionInfo(mData.getChannel(), mListener.getConnectionInfoListener());
			} else {
				// it's a disconnect
				Log.d("Communication","    device is disconnected");
			}
		}
	}

	public void onP2pThisDeviceChangedAction(WifiP2pDevice device){
		Log.d("Communication","onP2pThisDeviceChangedAction() called: device:");
		for (String s : device.toString().split("\n ")) {
			Log.d("Communication","    " + s);
		}
	}


	// The acitivty should present the screen like he left it (started messages/position of the scrollpane
	public void onRestart() {
		//apply the restored data to GUI
		mGui.getEditText().setText(mData.getCurrentText());
		mGui.setScrollPanePosition(mData.getCurrentScrollPosition());
	}

	public void SaveDataFromScreen(){
		mData.setCurrentText(String.valueOf(mGui.getEditText().getText()));
		mData.setCurrentScrollPosition(mGui.getListView().getScrollY());
	}
}

