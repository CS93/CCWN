package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import de.fhdw.bfws114a.Navigation.Navigation;
import de.fhdw.bfws114a.data.User;
import de.fhdw.bfws114a.dataInterface.DataInterface;
import de.fhdw.bfws114a.lernKartei.R;

public class ApplicationLogic {
	private Data mData;
	private Gui mGui;
	
	ApplicationLogic(Data data, Gui gui){
		mData=data;
		mGui=gui;
		applyDataToGui();
	}

	private void applyDataToGui() {
		mGui.setChoiceList(mData.getUser());		
		
	}
	
	public void onLoginButtonClicked(){
		if(mData.getUser().size() == 0){
			//Keine User im Spinner mit denen man sich einloggen kann
			mGui.showToast(mData.getActivity());
		} else {
			//Weiterleitung zum UserMenu (Auswahl der Kartei) mit entsprechendem User 
			for(User u : mData.getUser()){
				if(u.getName().equals(mGui.getChoiceList().getSelectedItem().toString())){
					Navigation.startActivityUserMenu(mData.getActivity(), u);
					return;
				}
			}
		}		
	}
		
	
	public void onProfileManagementButtonClicked(){
		//Weiterleitung zum UserMenu (Auswahl der Kartei) mit entsprechendem User 	
		Navigation.startActivityProfileManagement(mData.getActivity());
	}
	
	public void onInfoButtonClicked(){
		//Popup einblenden
		LayoutInflater layoutInflater= (LayoutInflater) mData.getActivity().getBaseContext().getSystemService(mData.getActivity().LAYOUT_INFLATER_SERVICE);  
	    View popupView = layoutInflater.inflate(R.layout.popup, null);  
        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
	             
	       	Button btnDismiss = (Button)popupView.findViewById(R.id.b_dismiss_popup_window);
	        btnDismiss.setOnClickListener(new Button.OnClickListener(){
	        	@Override
	        	public void onClick(View v) {
	        		popupWindow.dismiss();
	        	}});
	        
	        Button btnReset = (Button)popupView.findViewById(R.id.b_reset_popup_window);
	        btnReset.setOnClickListener(new Button.OnClickListener(){
	        	@Override
	        	public void onClick(View v) {
	        		DataInterface dataInterface = new DataInterface(mData.getActivity());
	        		dataInterface.importXMLtoDB();
	        		popupWindow.dismiss();
	        	}});
	    //Avoid that more than one PopupWindow is opened
	    popupWindow.dismiss();    
	    popupWindow.showAsDropDown(mGui.getChoiceList(), 50, -30);	    
	}
		

	// The acitivty should present the screen like he left it (started messages/position of the scrollpane
	public void onRestart() {
		mGui.setCurrentText();
		mGui.setCurrentScrollPosition();
	}
}

