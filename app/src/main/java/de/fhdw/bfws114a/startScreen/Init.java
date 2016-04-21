package de.fhdw.bfws114a.startScreen;


/**
 * Created by Carsten on 16.04.2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        initEventToListenerMapping();
    }

    //Save the Instance State is called when this activity is destroyed or resumed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // if the activity gets closed the current text and position in the chat are saved
        mData.saveDataInBundle(outState, mGui.getCurrentText(), mGui.getCurrentScrollPosition());
    }

    //Whether this activity (login) is restarted e.g. after finishing profile management, the method onRestart() in ApplicationLogic is called
    @Override
    protected void onRestart() {
        super.onRestart();
        mApplicationLogic.onRestart();
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

    private void initEventToListenerMapping() {
        new EventToListenerMapping(mGui, mApplicationLogic);

    }

}
