package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Samira Schorre/ Ricardo La Valle.
 */

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.fhdw.bfws114a.R;

public class EventToListenerMapping implements OnClickListener {

	private ApplicationLogic mApplicationLogic;

	public EventToListenerMapping(Gui gui, ApplicationLogic applicationLogic){
		mApplicationLogic = applicationLogic;
		gui.getButtonAddDevice().setOnClickListener(this);
		gui.getListView().setOnItemClickListener( new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

				String selectedMacAdress = (String) parent.getItemAtPosition(position);
				if(selectedMacAdress.endsWith(" - ONLINE")){
					selectedMacAdress = selectedMacAdress.substring(0,selectedMacAdress.length()-(" - ONLINE").length());
				}
				mApplicationLogic.applyProfileToGui(selectedMacAdress);
			}

		});
		gui.getListView().setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				String selectedMacAdress = (String) parent.getItemAtPosition(position);

				if(selectedMacAdress.endsWith(" - ONLINE")){
					selectedMacAdress = selectedMacAdress.substring(0,selectedMacAdress.length()-(" - ONLINE").length());
				}

				mApplicationLogic.removeKnownDeviceClicked(selectedMacAdress);
				return true;
			}
		});

	}



	@Override
	public void onClick(View v) {
		switch ( v.getId()) {
			case R.id.deviceoverview_addKnownMacAdress_Button:
				mApplicationLogic.onAddDeviceButtonClicked();
				break;
		}

	}
}
