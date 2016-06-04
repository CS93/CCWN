package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

import de.fhdw.bfws114a.R;

public class EventToListenerMapping implements OnClickListener, CompoundButton.OnCheckedChangeListener {

	private ApplicationLogic mApplicationLogic;
	//WiFi Direct Listeners
	private DiscoverPeersActionListener mDiscoverPeersActionListener;
	private StopDiscoveryActionListener mStopDiscoveryActionListener;
	private PeerListListener mPeerListListener;
	private ConnectionInfoListener mConnectionInfoListener;
	private ConnectActionListener mConnectActionListener;

	public EventToListenerMapping(Gui gui, ApplicationLogic applicationLogic){
		mApplicationLogic = applicationLogic;
		gui.getButtonSend().setOnClickListener(this);
		gui.getButtonDevices().setOnClickListener(this);
		gui.getButtonSettings().setOnClickListener(this);
		gui.getSwitch().setOnCheckedChangeListener(this);
		//WiFi Direct Listeners
		mDiscoverPeersActionListener = new DiscoverPeersActionListener();
		mStopDiscoveryActionListener = new StopDiscoveryActionListener();
		mPeerListListener = new PeerListListener();
		mConnectionInfoListener = new ConnectionInfoListener();
		mConnectActionListener = new ConnectActionListener();
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId()){
		case R.id.startscreen_send_message_button:
			//sendMessage
			try {
				mApplicationLogic.onSendButtonClicked();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case R.id.startscreen_goto_deviceoverview_button:
			//go to DeviceOverview
			mApplicationLogic.onDeviceButtonClicked();
			break;

		case R.id.startscreen_goto_settings_button:
			//go to Settings
			mApplicationLogic.onSettingsButtonClicked();
			break;
	}
}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d("Switch", "Der Switch ist : " + (isChecked ? "on" : "off"));
		mApplicationLogic.onOnlineStatusChanged(isChecked);
	}

	//Listener-----------------------------------------------

	public class DiscoverPeersActionListener implements WifiP2pManager.ActionListener {
		@Override
		public void onSuccess(){
			mApplicationLogic.onDiscoverPeersSuccess();
		}
		@Override
		public void onFailure(int reason){
			mApplicationLogic.onDiscoverPeersFailure(reason);
		}
	}

	public class StopDiscoveryActionListener implements WifiP2pManager.ActionListener {
		@Override
		public void onSuccess(){
			mApplicationLogic.onStopDiscoverySuccess();
		}
		@Override
		public void onFailure(int reason){
			mApplicationLogic.onStopDiscoveryFailure(reason);
		}
	}

	public class PeerListListener implements WifiP2pManager.PeerListListener {
		@Override
		public void onPeersAvailable(WifiP2pDeviceList peers){
			mApplicationLogic.onPeersAvailable(peers);
		}

	}

	public class ConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener {
		@Override
		public void onConnectionInfoAvailable(WifiP2pInfo info){
			mApplicationLogic.onConnectionInfoAvailable(info);
		}
	}

	public class ConnectActionListener implements WifiP2pManager.ActionListener {
		@Override
		public void onSuccess(){
			mApplicationLogic.onConnectSuccess();;
		}
		@Override
		public void onFailure(int reason){
			mApplicationLogic.onConnectFailure(reason);
		}
	}

}
