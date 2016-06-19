package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten Schlender / Samira Schorre.
 */


import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.fhdw.bfws114a.R;
import de.fhdw.bfws114a.data.Profile;

public class Gui {
	//components of the GUI
	private ImageView mImage;
	private Button mButtonSave, mButtonUpload, mButtonDelete;
	private TextView mTextViewNicknameLabel;
	private EditText mEditTextNickname, mEditTextStatus;

	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_profilesettings);
		mContext = act;
	//	map the intialized components with gui
		mImage = (ImageView) act.findViewById(R.id.profilesettings_ppicture_imageview);
		mButtonSave = (Button) act.findViewById(R.id.profilesettings_settings_save_button);
		mButtonUpload = (Button) act.findViewById(R.id.profilesettings_ppicture_upload_button);
		mButtonDelete = (Button) act.findViewById(R.id.profilesettings_ppicture_delete_button);
		mTextViewNicknameLabel = (TextView) act.findViewById(R.id.profilesettings_nickname_label);
		mEditTextNickname = (EditText) act.findViewById(R.id.profilesettings_nickname_edittext);
		mEditTextStatus = (EditText) act.findViewById(R.id.profilesettings_status_edittext);
	}

	//getter and setter for components


	public Button getButtonDelete() {
		return mButtonDelete;
	}

	public void setButtonDelete(Button mButtonDelete) {
		this.mButtonDelete = mButtonDelete;
	}

	public Button getButtonSave() {
		return mButtonSave;
	}

	public void setButtonSave(Button mButtonSave) {
		this.mButtonSave = mButtonSave;
	}

	public Button getButtonUpload() {
		return mButtonUpload;
	}

	public void setButtonUpload(Button mButtonUpload) {
		this.mButtonUpload = mButtonUpload;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public EditText getEditTextNickname() {
		return mEditTextNickname;
	}

	public void setEditTextNickname(EditText mEditTextNickname) {
		this.mEditTextNickname = mEditTextNickname;
	}

	public EditText getEditTextStatus() {
		return mEditTextStatus;
	}

	public void setEditTextStatus(EditText mEditTextStatus) {
		this.mEditTextStatus = mEditTextStatus;
	}

	public ImageView getImage() {
		return mImage;
	}

	public void setImage(ImageView mImage) {
		this.mImage = mImage;
	}

	public TextView getTextViewNicknameLabel() {
		return mTextViewNicknameLabel;
	}

	public void setTextViewNicknameLabel(TextView mTextViewNicknameLabel) {
		this.mTextViewNicknameLabel = mTextViewNicknameLabel;
	}

	public void setProfile(Profile prof){
	 // sets whole profile
		mImage.setImageDrawable(prof.getImage());
		mEditTextNickname.setText(prof.getName());
		mEditTextStatus.setText(prof.getStatus());
	}
}
