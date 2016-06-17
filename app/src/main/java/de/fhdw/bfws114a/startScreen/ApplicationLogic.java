package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.*;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.util.Iterator;

import de.fhdw.bfws114a.Communication.ClientInit;
import de.fhdw.bfws114a.Communication.MacAddress;
import de.fhdw.bfws114a.Communication.MacAddressList;
import de.fhdw.bfws114a.Communication.ReceiveMessageClient;
import de.fhdw.bfws114a.Communication.ReceiveMessageServer;
import de.fhdw.bfws114a.Communication.SendMessageClient;
import de.fhdw.bfws114a.Communication.SendMessageServer;
import de.fhdw.bfws114a.Communication.ServerInit;
import de.fhdw.bfws114a.Communication.WifiDirectBroadcastReceiver;
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
	private ReceiveMessageClient clientReceiverThread;
	private ReceiveMessageServer serverReceiverThread;
	private ServerInit serverInitThread;
	private ClientInit clientInitThread;

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

	//Reactions on Acitivity-Life-Cycle--------------------------
	public void onResume(){
		// register the BroadcastReceiver with the intent values to be matched
		if(mGui.getSwitch().isChecked()){
			mReceiver = new WifiDirectBroadcastReceiver(this);
			mData.getActivity().registerReceiver(mReceiver, mIntentFilter);
			Log.d("Communication", "Online --> call discoverPeers()");
			mData.getManager().discoverPeers(mData.getChannel(), mListener.getDiscoverPeersActionListener());
			if(mGui.getEditText().getText().equals("")){
				//set Button to usual status
				mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

			}
		}
	}

	public void onPause(){
		//mData.getActivity().unregisterReceiver(mReceiver);
		mReceiver = null;
		mData.getManager().removeGroup(mData.getChannel(), mListener.getStopDiscoveryActionListener());
		mData.getManager().cancelConnect(mData.getChannel(), mListener.getStopDiscoveryActionListener());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			Log.d("Communication", "Offline --> call stopPeerDiscovery()");
			mData.getManager().stopPeerDiscovery(mData.getChannel(), mListener.getStopDiscoveryActionListener());
		} else {
			Log.d("Communication", "The Api Level of the current device is to low");
		}
	}

	// The acitivty should present the screen like he left it (started messages/position of the scrollpane
	public void onRestart() {
		//apply the restored data to GUI
		mGui.getEditText().setText(mData.getCurrentText());
		mGui.setScrollPanePosition(mData.getCurrentScrollPosition());
		mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	}

	public void SaveDataFromScreen(){
		mData.setCurrentText(String.valueOf(mGui.getEditText().getText()));
		mData.setCurrentScrollPosition(mGui.getListView().getScrollY());
	}

	//Reactions on GUI-Events-------------------------------------------
	public void onSendButtonClicked() throws InterruptedException {
		/*if(mWifiP2pInfo != null){
			//there is a succesful connection
			try {
				if (mWifiP2pInfo.groupFormed && mWifiP2pInfo.isGroupOwner) {
					// the group owner acts as serverInitThread
					new SendMessageServer(mData.getActivity(), mGui, this, mGui.getEditText().getText().toString(), mData.getDeviceList()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
				} else {
					if (mWifiP2pInfo.groupFormed) {
						// the other device acts as the clientInitThread
						new SendMessageClient(mData.getActivity(), mGui, this, mWifiP2pInfo.groupOwnerAddress.getHostAddress() , mGui.getEditText().getText().toString()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
					}
				}
			} catch (NullPointerException e){
				Log.d("Communication", "No Connection Info available up to now");
				mGui.showToast(mData.getActivity(), "Keine Verbindung, bitte erneut versuchen");
			}
		} else {
			mGui.showToast(mData.getActivity(), "Keine Verbindung, bitte erneut versuchen");
		}*/

		//test whether there is no empty message
		if(mGui.getEditText().getText().equals("")){
			mGui.showToast(mData.getActivity(), "Der Versand leerer Nachrichten ist nicht möglich");
			return;
		}

		// send Data to everyone in your Peerslist
		//Test whether there are any peers availabe
		if(mData.getDeviceList() != null) {
			//erase current connection
			//mData.getManager().cancelConnect(mData.getChannel(), mListener.getConnectActionListener());
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

					if(mWifiP2pInfo == null){
						synchronized(mData.getActivity()){
							mData.getActivity().wait(6000);
						}
					}
					if(mWifiP2pInfo != null){
						//there is a succesful connection
						try {
							if (mWifiP2pInfo.groupFormed && mWifiP2pInfo.isGroupOwner) {
								// the group owner acts as serverInitThread
								new SendMessageServer(this, mGui.getEditText().getText().toString()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
							} else {
								if (mWifiP2pInfo.groupFormed) {
									// the other device acts as the clientInitThread
									new SendMessageClient(this, mWifiP2pInfo.groupOwnerAddress.getHostAddress() , mGui.getEditText().getText().toString()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
								}
							}
						} catch (NullPointerException e){
							Log.d("Communication", "No Connection Info available up to now");
							mGui.showToast(mData.getActivity(), "Keine Verbindung, bitte erneut versuchen");
						}
					} else {
					mGui.showToast(mData.getActivity(), "Keine Verbindung, bitte erneut versuchen");
					}
					mData.getManager().cancelConnect(mData.getChannel(), mListener.getConnectActionListener());
				}
			}else {
				Log.d("Communication", "Devicelist is null");
				mGui.showToast(mData.getActivity(), "Keine Geräte in ihrer Nähe, bitte erneut versuchen");
			}
		} else {
			mGui.showToast(mData.getActivity(), "Keine Geräte in ihrer Nähe, bitte WLAN überprüfen");
		}
	}

	public void onSettingsButtonClicked(){
		//go to settings screen
		Navigation.startActivity(mData.getActivity(), Navigation.ACTIVITY_ProfileSettings_CLASS);
	}

	public void onDeviceButtonClicked(){
		//go to member overview screen
		Navigation.startActivityDeviceOverview(mData.getActivity(), mData.getDeviceList());
	}

	public void onOnlineStatusChanged(boolean isChecked){
		//start/stop background process or delete/initialize listeners
		if(isChecked) {
			//on has been selected --> register receiver
			mReceiver = new WifiDirectBroadcastReceiver(this);
			//mData.getActivity().registerReceiver(mReceiver, mIntentFilter);

			Log.d("Communication", "Online --> call discoverPeers()");
			mData.getManager().discoverPeers(mData.getChannel(), mListener.getDiscoverPeersActionListener());

			//may connect at this point

			//mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}else {
			//off has been selected
			mData.getManager().removeGroup(mData.getChannel(), null);
			//mData.getActivity().unregisterReceiver(mReceiver);
			mReceiver = null;

			mData.getManager().cancelConnect(mData.getChannel(), mListener.getStopDiscoveryActionListener());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				Log.d("Communication", "Offline --> call stopPeerDiscovery()");
				mData.getManager().stopPeerDiscovery(mData.getChannel(), mListener.getStopDiscoveryActionListener());
			} else {
				Log.d("Communication", "The Api Level of the current device is to low");
			}

			//Visualize being offline at button
			mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			//killing the 0threads
			if(serverInitThread != null){
				serverInitThread.interrupt();
				if(serverReceiverThread != null){
					serverReceiverThread.cancel(false);
				}
			} else if(clientInitThread != null){
				clientInitThread.interrupt();
				if(clientReceiverThread != null){
					clientReceiverThread.cancel(false);
				}
			}
		}
	}

	//Reactions on WiFi-Direct actions
	public void onPeersAvailable(WifiP2pDeviceList peers){
		if (peers.getDeviceList().size() == 0) {
			mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			Log.d("Communication", "No devices found");
			return;
		} else {
			mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
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
		if(mWifiP2pInfo.groupFormed && mWifiP2pInfo.groupOwnerAddress != null){
			//When there is a connection, the receivers tasks should be started
			if(mWifiP2pInfo.isGroupOwner){
				// the group owner acts as serverInitThread
				if(serverReceiverThread == null){
					//no receiver initialized
					serverReceiverThread = new ReceiveMessageServer(mData.getActivity(), mGui, this);
					serverReceiverThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else if(!serverReceiverThread.isCancelled()){
					//receiver has been cancelled and should be restarted
					serverReceiverThread = new ReceiveMessageServer(mData.getActivity(), mGui, this);
					serverReceiverThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}

				if(serverInitThread == null){
					serverInitThread = new ServerInit();
					serverInitThread.start();
				}
			} else {
				//this device acts like clientInitThread
				if(clientReceiverThread == null){
					//no receiver initialized
					clientReceiverThread = new ReceiveMessageClient(mData.getActivity(), mGui, this, mWifiP2pInfo.groupOwnerAddress.getHostAddress());
					clientReceiverThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else if(!clientReceiverThread.isCancelled()){
					//receiver has been cancelled and should be restarted
					clientReceiverThread = new ReceiveMessageClient(mData.getActivity(), mGui, this, mWifiP2pInfo.groupOwnerAddress.getHostAddress());
					clientReceiverThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}

				if(clientInitThread == null){
					clientInitThread = new ClientInit(mWifiP2pInfo.groupOwnerAddress);
					clientInitThread.start();
				}
			}

		}
	}

	public void onDiscoverPeersSuccess(){
		Log.d("Communication","onDiscoverPeersSuccess() called");
	}

	public void onDiscoverPeersFailure(int reason){
		Log.d("Communication","onDiscoverPeersFailure() called: reason: " + reason);
		//may alert user
		mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	}

	public void onStopDiscoverySuccess(){
		Log.d("Communication","onStopDiscoverySuccess() called");
		mGui.getButtonSend().setPaintFlags(mGui.getButtonSend().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	}

	public void onStopDiscoveryFailure(int reason){
		if(reason == 0){
			//its an Error
			Log.d("Communication","onStopDiscoveryFailure() called: reason: Error" );
		} else if(reason == 2){
				//device is busy
				Log.d("Communication","onStopDiscoveryFailure() called: reason: Busy" );
		} else {
			Log.d("Communication","onStopDiscoveryFailure() called: reason: " + reason);
		}

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


	//Reaction on Threads/Asynctasks---------------------------------------------------------------
	public void showErrorMessage(String text){
		mGui.showToast(mData.getActivity(), text);
	}

	public void onMessageReceived(String msg) {
		//add to chatbubble
		ChatMessage receivedMessage = new ChatMessage(false, msg);
		//add message to gui (true because the standard would be the left side and the messages of the own user used to be on right side)
		mGui.getChatArrayAdapter().add(receivedMessage);
		// save message not-persistent in data
		mData.getMessageList().add(receivedMessage);
	}

	public void onMessageSuccesfulSend() {
		//add to chatbubble
		if(mGui.getEditText().getText().equals("")){
			//this is an empty message which results from sending several messages
			return;
		}
		ChatMessage receivedMessage = new ChatMessage(true, mGui.getEditText().getText().toString());
		//add message to gui (true because the standard would be the left side and the messages of the own user used to be on right side)
		mGui.getChatArrayAdapter().add(receivedMessage);
		// save message not-persistent in data
		mData.getMessageList().add(receivedMessage);
		//Reset EditTextField
		mGui.getEditText().setText("");
	}
}