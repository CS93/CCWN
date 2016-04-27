package de.fhdw.bfws114a.startScreen;

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
		gui.getButtonSend().setOnClickListener(this);
		gui.getButtonDevices().setOnClickListener(this);
		gui.getButtonSettings().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId()){
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
