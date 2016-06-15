package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import de.fhdw.bfws114a.Communication.CommunicationObject;
import de.fhdw.bfws114a.data.Profile;


public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	private CommunicationObject mCO;

	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;
		mCO = new CommunicationObject();
		applyDataToGui();
	}

	private void applyDataToGui() {
		mGui.setListView( (String[]) mData.getDevicelist().getMacAddressList().toArray()); // cast ArrayList to String[]
		//mGui.setDeviceList(mData.getDevices());
		//mGui.setMessages(mData.getDevicelist().getMacAddressList());
	}


	public void onDeviceSelected(String name){
		mData.setSelectedDevice(name);
		mGui.setSelectedProfile(name);
	}

	// The acitivty should present the screen like he left it (typed in nickname, status and image)
	public void onRestart() {
		//apply the restored data to GUI
		applyDataToGui();
	}
}

