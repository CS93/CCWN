package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten Schlender / Samira Schorre.
 */

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.FileNotFoundException;
import de.fhdw.bfws114a.data.Profile;
import de.fhdw.bfws114a.dataInterface.DataInterface;


public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	private DataInterface mDataInterface;


	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;
		mDataInterface = new DataInterface(mData.getActivity());
		applyDataToGui();
	}

	private void applyDataToGui() {
		mGui.setProfile(mData.getOwnProfile());
	}


	public void onSaveButtonClicked(){
		mData.setOwnProfile(new Profile(mDataInterface.getOwnMacAdress(), String.valueOf(mGui.getEditTextNickname().getText()), String.valueOf(mGui.getEditTextStatus().getText()), mDataInterface.convertDrawableToByteArray(mGui.getImage().getDrawable())));
		mData.saveProfile(mData.getOwnProfile());
		mData.getActivity().finish();
	}
		
	
	public void onUploadButtonClicked(){
		//go to galery and be able to upload a picture, a thread is necessary for that
		mData.getActivity().startActivityForResult(mData.getIntent(), mData.getRequestCode());
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
		Log.d("RICARDO","SaveDataFromScreen aufgerufen!");

		if(mGui.getImage()!=null) {
			mData.setOwnProfile(new Profile(mDataInterface.getOwnMacAdress(), String.valueOf(mGui.getEditTextNickname().getText()), String.valueOf(mGui.getEditTextStatus().getText()), mDataInterface.convertDrawableToByteArray(mGui.getImage().getDrawable())));
		}
		else{
			mData.setOwnProfile(new Profile(mDataInterface.getOwnMacAdress(), String.valueOf(mGui.getEditTextNickname().getText()), String.valueOf(mGui.getEditTextStatus().getText()), null));

		}
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent data) {

		//weitere Abfangen!! RESULT_CANCELD ist bei back, nichts ausgewählt
			if (requestCode == mData.getRequestCode()){ //bearbeitung des BildRequestes
				mData.setImageUri(data.getData());
				try {
					mData.setInputStream(mData.getActivity().getContentResolver().openInputStream(mData.getImageUri()));
					mData.setBitmap(BitmapFactory.decodeStream(mData.getInputStream()));
					mGui.getImage().setImageBitmap(mData.getBitmap());
				} catch (FileNotFoundException e) { //if File do not load
					e.printStackTrace();
				}
			}
	}
}

