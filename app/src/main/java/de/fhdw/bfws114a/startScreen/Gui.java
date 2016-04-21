package de.fhdw.bfws114a.startScreen;

/**
 * Created by Carsten on 21.04.2016.
 */


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

public class Gui {
	
	//components of the GUI

	//To initialize some Objects the context is necessary
	private Context mContext;

	public Gui(Activity act) {
		act.setContentView(R.layout.activity_startscreen);
		mContext = act;
	//	map the intialized components with gui
	}

	public String getCurrentText(){
		// returns the text which the user typed in so far (may just return the field because the GUI shouldnt "know" the text)
		return "test";
	}

	public void setCurrentText(String text){
		// fills the textpane on the GUI with the given text

	}

	public int getCurrentScrollPosition(){
		// returns the position of the scrollbar (may just return the field because the GUI shouldnt "know" the pos)
		return 0;
	}

	public void setCurrentScrollPosition(int pos){
		// puts / focusses the scrollbar to the given position
	}
}
