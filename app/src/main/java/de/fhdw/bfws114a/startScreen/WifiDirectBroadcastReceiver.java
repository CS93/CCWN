package de.fhdw.bfws114a.startScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private ApplicationLogic mApplicationLogic;

    public WifiDirectBroadcastReceiver(ApplicationLogic applicationLogic){
        super();
        mApplicationLogic = applicationLogic;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action;
        NetworkInfo networkInfo;
        WifiP2pDevice device;

        action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Wifi P2P mode is now enabled or disabled
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            mApplicationLogic.onP2pStateChangedAction(state);

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // The peer list has changed!
            mApplicationLogic.onP2pPeersChangedAction();

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Connection state has changed --> sb wants sent a respond to a new connection or disconnected
            networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            mApplicationLogic.onP2pConnectionChangedAction(networkInfo);

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // details of this device changed
            device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            mApplicationLogic.onP2pThisDeviceChangedAction(device);
        }
    }

}