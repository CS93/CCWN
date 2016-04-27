package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import de.fhdw.bfws114a.Communication.CommunicationObject;
import de.fhdw.bfws114a.Navigation.Navigation;

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
		mGui.setMessages(mData.getMessageList());
	}


	public void onSendButtonClicked(){
		//send Data
		mCO.sendMessage(mGui.getText());
	}
		
	
	public void onSettingsButtonClicked(){
		//go to settings screen
		Navigation.startActivity(mData.getActivity(), Navigation.ACTIVITY_ProfileSettings_CLASS);
	}

	public void onDeviceButtonClicked(){
		//go to member overview screen
		Navigation.startActivity(mData.getActivity(), Navigation.ACTIVITY_DeviceOverview_CLASS);
	}

	public void onOnlineStatusChanged(){
		//start/stop background process or delete/initialize listeners
	}

	// The acitivty should present the screen like he left it (started messages/position of the scrollpane
	public void onRestart() {
		//mGui.Text.setText(mData.getCurrentText());
		//mGui.Scrollbar.setPosition(mData.getCurrentScrollPosition());
	}

	public void actualizeScreenData(){
		//mData.setCurrentText = mGui.Text1.getText();
		//mData.setCurrentScrollPosition = mGui.Scrollpane.getPosition();
	}
}

