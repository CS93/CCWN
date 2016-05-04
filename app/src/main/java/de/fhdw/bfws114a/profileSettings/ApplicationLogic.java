package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.graphics.drawable.Drawable;

import de.fhdw.bfws114a.Communication.CommunicationObject;
import de.fhdw.bfws114a.Navigation.Navigation;
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
		mGui.setProfile(mData.getOwnProfile());
	}


	public void onSaveButtonClicked(){
		mData.setOwnProfile(new Profile(mGui.getImage().getDrawable(), String.valueOf(mGui.getEditTextNickname().getText()), String.valueOf(mGui.getEditTextStatus().getText())));
		mData.saveProfile(mData.getOwnProfile());
	}
		
	
	public void onUploadButtonClicked(){
		//go to galery and be able to upload a picture, a thread is necessary for that


		// map chosen picture to gui
		// mGui.getImage().setDrawable(<picture>);
	}

	public void onDeleteButtonClicked(){
		//delete Picture
		mGui.getImage().setImageDrawable(null); //maybe default picture instead of null
	}

	// The acitivty should present the screen like he left it (typed in nickname, status and image)
	public void onRestart() {
		//apply the restored data to GUI
		applyDataToGui();
	}

	public void SaveDataFromScreen(){
		mData.setOwnProfile(new Profile(mGui.getImage().getDrawable(), String.valueOf(mGui.getEditTextNickname().getText()), String.valueOf(mGui.getEditTextStatus().getText())));
	}
}

