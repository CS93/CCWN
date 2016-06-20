package de.fhdw.bfws114a.startScreen;


/**
 * Created by Carsten Schlender.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Init extends AppCompatActivity {
    private Data mData;
    private Gui mGui;
    private ApplicationLogic mApplicationLogic;

    //this onCreate is called when the app starts.
    // The Data, Gui, Applogic and Listenermapping classes are instantiated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        initGui();
        initApplicationLogic();
        // nicht mehr nötig wg des Listeners/Listener-Klasse
        // initEventToListenerMapping();
    }


    //Whether this activity is restarted e.g. after finishing profilesettings/deviceoverview, the method onRestart() in ApplicationLogic is called
    @Override
    protected void onRestart() {
        super.onRestart();
        mApplicationLogic.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mApplicationLogic.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mApplicationLogic.onPause();
    }


    //Save the Instance State is called when this activity is destroyed or resumed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // if the activity gets closed the current drawable_startscreen_chat_edittext and position in the chat are saved
        //actualizes the currentText and currentScrollPosition in Data
        mApplicationLogic.SaveDataFromScreen();
        mData.saveDataInBundle(outState);
    }


    //overriding physical buttons

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        //prevent the key from bein handled by system
        return true;
    }

    @Override
    public void onBackPressed() {
        //close the app
        finish();
    }

    private void initData(Bundle savedInstanceState) {
        mData = new Data(savedInstanceState, this);
    }


    private void initGui() {
        mGui = new Gui(this);

    }

    private void initApplicationLogic() {
        mApplicationLogic = new ApplicationLogic(mData, mGui);
    }

//    private void initEventToListenerMapping() {
//        new EventToListenerMapping(mGui, mApplicationLogic);
//
//    }

}
