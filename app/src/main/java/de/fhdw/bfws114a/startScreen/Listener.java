package de.fhdw.bfws114a.startScreen;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import de.fhdw.bfws114a.R;

public class Listener {

    private ApplicationLogic mApplicationLogic;
    private Gui mGui;
    private ButtonListener mButtonListener;
    private CheckedChangeListener mCheckedChangeListener;
    private DiscoverPeersActionListener mDiscoverPeersActionListener;
    private StopDiscoveryActionListener mStopDiscoveryActionListener;
    private PeerListListener mPeerListListener;
    private ConnectionInfoListener mConnectionInfoListener;
    private ConnectActionListener mConnectActionListener;


    public Listener(ApplicationLogic applicationLogic, Gui gui){

        mApplicationLogic = applicationLogic;
        mGui = gui;
        mButtonListener = new ButtonListener();
        mCheckedChangeListener = new CheckedChangeListener();
        gui.getButtonSend().setOnClickListener(mButtonListener);
        gui.getButtonDevices().setOnClickListener(mButtonListener);
        gui.getButtonSettings().setOnClickListener(mButtonListener);
        gui.getSwitch().setOnCheckedChangeListener(mCheckedChangeListener);
        //WiFi Direct Listeners
        mDiscoverPeersActionListener = new DiscoverPeersActionListener();
        mStopDiscoveryActionListener = new StopDiscoveryActionListener();
        mPeerListListener = new PeerListListener();
        mConnectionInfoListener = new ConnectionInfoListener();
        mConnectActionListener = new ConnectActionListener();
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startscreen_send_message_button:
                    //sendMessage
                    mApplicationLogic.onSendButtonClicked();
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

    }

    private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d("Switch", "Der Switch ist : " + (isChecked ? "on" : "off"));
            mApplicationLogic.onOnlineStatusChanged(isChecked);
        }
    }

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
            mApplicationLogic.onConnectSuccess();
        }
        @Override
        public void onFailure(int reason){
            mApplicationLogic.onConnectFailure(reason);
        }
    }

    // getter

    public ButtonListener getButtonListener(){
        return mButtonListener;
    }

    public DiscoverPeersActionListener getDiscoverPeersActionListener(){
        return mDiscoverPeersActionListener;
    }

    public StopDiscoveryActionListener getStopDiscoveryActionListener(){
        return mStopDiscoveryActionListener;
    }

    public PeerListListener getPeerListListener(){
        return mPeerListListener;
    }

    public ConnectionInfoListener getConnectionInfoListener(){
        return mConnectionInfoListener;
    }

    public ConnectActionListener getConnectActionListener(){
        return mConnectActionListener;
    }

}
