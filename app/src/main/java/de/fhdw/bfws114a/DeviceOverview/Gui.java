package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.fhdw.bfws114a.R;;
import de.fhdw.bfws114a.data.DeviceList;

public class Gui {
	//components of the GUI
	private EditText mEditTextStatus;
	private ListView mListView;
	private ListAdapter mListViewAdapter;
	private Data mData;

	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_deviceoverview);
		mContext = act;
	//	map the intialized components with gui
		//mEditTextStatus = (EditText) act.findViewById(R.id.profilesettings_status_edittext);
		mListView = (ListView) act.findViewById(R.id.deviceoverview_device_overview_listview);

	}

	//getter and setter for components
	public ListView getListView() {
		return mListView;
	}

	public void setListView(String[] mDevicelist) {
		//Add bubbles to Listview
		mListViewAdapter = new ArrayAdapter<String>(mContext, R.layout.right, mDevicelist );
		mListView.setAdapter(mListViewAdapter);
	}

	public ListAdapter getListViewAdapter() {
		return mListViewAdapter;
	}

	public void setListViewAdapter(ArrayAdapter mListViewAdapter) {
		this.mListViewAdapter = mListViewAdapter;
	}

	public void setDeviceList(DeviceList devices){
		// sets names of devices in list

	}

	public void setSelectedProfile(String name){
	 // sets name, status, picture from selected Profile to gui

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