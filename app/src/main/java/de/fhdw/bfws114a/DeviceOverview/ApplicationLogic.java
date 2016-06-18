package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import de.fhdw.bfws114a.Communication.MacAddress;
import de.fhdw.bfws114a.Communication.MacAddressList;


public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	private static final String TAG = "DeviceOverview";


	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;
		applyDataToGui();
	}

	private void applyDataToGui() {
		//if ( mData.getDevicelist() )

		mGui.setTextViewMacAdress(mData.getDataInterface().getOwnMacAdress());

        Log.d("RICARDO", "DeviceOverview/ApplyDataToGui aufgerufen");

        String[] knownDevices = mData.getDataInterface().getKnownMacAdresses().getStringArray();
        String[] foundDevices;

        try {
            foundDevices = mData.getDevicelist().getStringArray();
        }
        catch(NullPointerException e){
            foundDevices = new String[0];
        }

        Log.d("RICARDO", "ApplyDataToGui: Anzahl knownDevices " + knownDevices.length);
        Log.d("RICARDO", "ApplyDataToGui: Anzahl foundDevices " + foundDevices.length);

        ArrayList<String> resultList = new ArrayList<>();

        for (int i = 1; i < knownDevices.length; i++) {
            Log.d("RICARDO", "KnownDevice: "+ knownDevices[i]);

            if(foundDevices.length!=0) {
                for (int j = 0; j < foundDevices.length; j++) {
                    Log.d("RICARDO", "  Compare with Found Device: " + foundDevices[j]);
                    if (knownDevices[i].equalsIgnoreCase(foundDevices[j])) {
                        resultList.add(knownDevices[i] + " - ONLINE");
                    } else resultList.add(knownDevices[i]);
                }
            }
            else resultList.add(knownDevices[i]);
        }

        String[] result = new String[resultList.size()];
        result = resultList.toArray(result);


        mGui.setListView(result);
    }



	// The acitivty should present the screen like he left it (typed in nickname, status and image)
	public void onRestart() {
		//apply the restored data to GUI
		applyDataToGui();
	}

	public void applyProfileToGui(long statusId) {
		applyStatusToGui(statusId);
	}

	private void applyStatusToGui(long statusId){
		int shortStatusId = (int) statusId;
		mGui.setTextViewStatus(mData.getDevicelist().getMacAddressByIndex(shortStatusId).toString());
	}

	public void onAddDeviceButtonClicked(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mData.getActivity());
		builder.setTitle("MAC Adresse eingeben");

		// Set up the input
		final EditText input = new EditText(mData.getActivity());
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
                if (input.getText().length() != 0) {
                    String macAdress = input.getText().toString();
                    mData.getDataInterface().addKnownMacAdress(macAdress);
                    onRestart();
                }
                else{
                    mGui.showToast(mData.getActivity(), "Bitte eine Mac-Adresse eingeben!");
                    onAddDeviceButtonClicked();
                }
			}
		});
		builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();
	}

}