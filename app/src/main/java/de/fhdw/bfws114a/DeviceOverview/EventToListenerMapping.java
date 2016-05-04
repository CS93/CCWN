package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.view.View;
import android.view.View.OnClickListener;

import de.fhdw.bfws114a.R;

public class EventToListenerMapping implements OnClickListener {

	private ApplicationLogic mApplicationLogic;

	public EventToListenerMapping(Gui gui, ApplicationLogic applicationLogic){
		mApplicationLogic = applicationLogic;
//		gui.getButtonSave().setOnClickListener(this);
//		gui.getButtonUpload().setOnClickListener(this);
//		gui.getButtonDelete().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId()){
		case R.id.profilesettings_settings_save_button:
			//sendMessage
			mApplicationLogic.onDeviceSelected("test");
			break;
	}

}
	
}
