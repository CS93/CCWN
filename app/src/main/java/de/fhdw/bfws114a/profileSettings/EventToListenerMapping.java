package de.fhdw.bfws114a.profileSettings;

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
		gui.getButtonSave().setOnClickListener(this);
		gui.getButtonUpload().setOnClickListener(this);
		gui.getButtonDelete().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId()){
		case R.id.profilesettings_settings_save_button:
			//sendMessage
			mApplicationLogic.onSaveButtonClicked();
			break;
		case R.id.profilesettings_ppicture_upload_button:
			//go to DeviceOverview
			mApplicationLogic.onUploadButtonClicked();
			break;

		case R.id.profilesettings_ppicture_delete_button:
			//go to Settings
			mApplicationLogic.onDeleteButtonClicked();
			break;
	}

}
	
}
