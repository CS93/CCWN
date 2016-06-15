package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

import de.fhdw.bfws114a.R;

public class EventToListenerMapping implements OnClickListener {

	private ApplicationLogic mApplicationLogic;

	public EventToListenerMapping(Gui gui, ApplicationLogic applicationLogic){
		mApplicationLogic = applicationLogic;
		gui.getListView().setOnItemClickListener( new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
			//TODO
				//TextView textView = (TextView) viewClicked;
				//String message = "You clicked # " + position + ", which is string: " + textView.getText().toSring();
				//Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show;
			}
		} );
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
