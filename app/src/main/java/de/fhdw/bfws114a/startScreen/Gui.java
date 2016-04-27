package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import de.fhdw.bfws114a.R;
import de.fhdw.bfws114a.data.MessageList;

public class Gui {
	//components of the GUI


	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_startscreen);
		mContext = act;
	//	map the intialized components with gui
	}

	//getter and setter for components

	public void setMessages(MessageList list){
		//put the MessageList to listview
	}


	public void setScrollPanePosition(int pos){
		//set the scrollpanePosition
	}

	public String getText() {
		return "";
	}
}
