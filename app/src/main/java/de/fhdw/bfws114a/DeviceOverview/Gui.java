package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Samira Schorre/ Ricardo La Valle.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.fhdw.bfws114a.R;;

public class Gui {
	//components of the GUI
	private TextView mTextViewName;
	private TextView mTextViewStatus;
	private TextView mTextViewOwnMacAdress;
	private ListView mListView;
	private ListAdapter mListViewAdapter;
    private Button mButtonAddDevice;

	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_deviceoverview);
		mContext = act;
	//	map the intialized components with gui
        mTextViewName = (TextView) act.findViewById(R.id.deviceoverview_profile_name_textview);
        mTextViewStatus = (TextView) act.findViewById(R.id.deviceoverview_profile_status_textview);
		mListView = (ListView) act.findViewById(R.id.deviceoverview_device_overview_listview);
		mTextViewOwnMacAdress = (TextView) act.findViewById(R.id.deviceoverview_macadress_textview);
		mButtonAddDevice = (Button) act.findViewById(R.id.deviceoverview_addKnownMacAdress_Button);
	}

	//getter and setter for components
	public ListView getListView() {
		return mListView;
	}

	public void setListView(String[] mDevicelist) {
		//Add bubbles to Listview
		mListViewAdapter = new ArrayAdapter<String>(mContext,
													R.layout.left,
													R.id.startscreen_chat_bubble,
													mDevicelist );
		mListView.setAdapter(mListViewAdapter);
	}


    public Button getButtonAddDevice() {
        return mButtonAddDevice;
    }

	public ListAdapter getListViewAdapter() {
		return mListViewAdapter;
	}


	public void setListViewAdapter(ArrayAdapter mListViewAdapter) {
		this.mListViewAdapter = mListViewAdapter;
	}

	public TextView getTextViewStatus() {
		return mTextViewStatus;
	}


	public void setTextViewName(String name) {
		this.mTextViewName.setText(name);
	}

    public void setTextViewStatus(String status) {
        this.mTextViewStatus.setText(status);
    }

	public void setTextViewOwnMacAdress(String MacAdress) {
		this.mTextViewOwnMacAdress.setText(MacAdress);
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