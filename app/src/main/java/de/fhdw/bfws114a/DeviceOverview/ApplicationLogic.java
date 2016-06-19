package de.fhdw.bfws114a.DeviceOverview;

/**
 * Created by Samira Schorre/ Ricardo La Valle.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import de.fhdw.bfws114a.data.Profile;


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

		mGui.setTextViewOwnMacAdress(mData.getDataInterface().getOwnMacAdress());

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

        for (int i = 0; i < knownDevices.length; i++) {
            Log.d("RICARDO", "KnownDevice " + i + " :" +knownDevices[i]);

            if(foundDevices.length!=0 ) {
                for (int j = 0; j < foundDevices.length; j++) {
                    Log.d("RICARDO", "  Compare with Found Device: " + foundDevices[j]);
                    if (knownDevices[i].equalsIgnoreCase(foundDevices[j])) {
                        Log.d("RICARDO", "    --> MATCH --> Device ist Online!");

                        resultList.add(knownDevices[i] + " - ONLINE");
                        Log.d("RICARDO", "Zu result list hinzugefügt: \""+ knownDevices[i] + " - ONLINE\"");

                    }
                    else{
                        resultList.add(knownDevices[i]);
                        Log.d("RICARDO", "Zu result list hinzugefügt: \""+ knownDevices[i] + "\"");

                    }
                }
            }
            else{
                resultList.add(knownDevices[i]);
                Log.d("RICARDO", "Zu result list hinzugefügt: \""+ knownDevices[i] + "\"");
            }
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


    public void applyProfileToGui(String macAdress) {

        Profile p = mData.getDataInterface().getProfile(macAdress);

        applyStatusToGui(p.getStatus());
        applyNameToGui(p.getName());
    }


    private void applyNameToGui(String name){
        mGui.setTextViewName(name);
    }

    private void applyStatusToGui(String status){
        mGui.setTextViewStatus(status);
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
//		builder.setNeutralButton("QR-Code", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});


		builder.show();
	}

    public void removeKnownDeviceClicked(final String macAdress){

        AlertDialog.Builder builder = new AlertDialog.Builder(mData.getActivity());
        builder.setTitle("Bekanntes Gerät löschen");

        // Set up the input
        final TextView text = new TextView(mData.getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        text.setText("Sind Sie sicher dass Sie das Gerät \"" + macAdress + "\" von der Liste der bekannten Geräte entfernen möchten?");
        builder.setView(text);

        // Set up the buttons
        builder.setPositiveButton("Entfernen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mData.getDataInterface().removeKnownMacAdress(macAdress);
                onRestart();

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