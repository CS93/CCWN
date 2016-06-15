package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.util.Log;

import java.util.Iterator;

import de.fhdw.bfws114a.Communication.CommunicationObject;
import de.fhdw.bfws114a.Communication.MacAddress;
import de.fhdw.bfws114a.data.Profile;


public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	private CommunicationObject mCO;
	private static final String TAG = "DeviceOverview";


	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;
		mCO = new CommunicationObject();
		applyDataToGui();
	}

	private void applyDataToGui() {
		//if ( mData.getDevicelist() )
		try{
			String[] stringListView = new String[mData.getDevicelist().getMacAddressList().size()];
			Iterator<MacAddress> iterator = mData.getDevicelist().getMacAddressList().iterator();
			int counter =0;
			while(iterator.hasNext()){
				stringListView[counter] = iterator.next().getMacAddress();
				counter++;
			}
			mGui.setListView(stringListView); // cast ArrayList to String[]
		} catch (ClassCastException e) {
			Log.d(TAG, "applyDataToGui: Fehler beim ClassCasting");
		} catch (NullPointerException e) {
			mGui.showToast(mData.getActivity(), "Leider sind keine Geräte in Ihrer Nähe.");
		}
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

