package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import de.fhdw.bfws114a.R;
import de.fhdw.bfws114a.data.MessageList;

public class Gui {
	//components of the GUI
	private ListView mListView;
	private EditText mEditText;
	private Button mButtonSend;
	private ImageButton mButtonSettings, mButtonDevices;
	private TextView mTextView; //Chat bubble
	private Switch mSwitch; //chagne from Online To Offline


	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_startscreen);
		mContext = act;
	//	map the intialized components with gui
		mContext = act;
		mListView = (ListView) act.findViewById(R.id.startscreen_chat_overview_listview);
		mEditText = (EditText) act.findViewById(R.id.startscreen_send_message_edittext);
		mButtonSend = (Button) act.findViewById(R.id.startscreen_send_message_button);
		mButtonSettings = (ImageButton) act.findViewById(R.id.startscreen_goto_settings_button);
		mButtonDevices = (ImageButton) act.findViewById(R.id.startscreen_goto_deviceoverview_button);
		mSwitch = (Switch) act.findViewById(R.id.startscreen_availability_switch);
		mTextView = (TextView) act.findViewById(R.id.startscreen_chat_bubble);
	}

	//getter and setter for components


	public ListView getListView() {
		return mListView;
	}

	public EditText getEditText() {
		return mEditText;
	}

	public Button getButtonSend() {
		return mButtonSend;
	}

	public TextView getTextView() {
		return mTextView;
	}

	public Context getContext() {
		return mContext;
	}

	public Switch getSwitch() {
		return mSwitch;
	}

	public ImageButton getButtonDevices() {
		return mButtonDevices;
	}

	public ImageButton getButtonSettings() {
		return mButtonSettings;
	}

	public void setListView(ListView mListView) {
		this.mListView = mListView;
	}

	public void setEditText(EditText mEditText) {
		this.mEditText = mEditText;
	}

	public void setButtonSend(Button mButton) {
		this.mButtonSend = mButton;
	}

	public void setTextView(TextView mTextView) {
		this.mTextView = mTextView;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public void setButtonSettings(ImageButton mButtonSettings) {
		this.mButtonSettings = mButtonSettings;
	}

	public void setButtonDevices(ImageButton mButtonDevices) {
		this.mButtonDevices = mButtonDevices;
	}

	public void setSwitch(Switch mSwitch) {
		this.mSwitch = mSwitch;
	}

	public void setMessages(MessageList list){
		//put the MessageList to listview
	}

	public void setScrollPanePosition(int pos){
		//set the position of the scrollpane (in the listview)
		mListView.setScrollY(pos);
	}

	public void showToast (Activity act, String text){
		Toast toast = Toast.makeText(act, text, Toast.LENGTH_LONG);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView toastTV = (TextView) toastLayout.getChildAt(0);
		toastTV.setTextSize(30);
		toastTV.setTextColor(Color.RED);
		toast.show();
	}

}
