package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.fhdw.bfws114a.R;

public class Init extends AppCompatActivity {

    private Data mData;
    private Gui mGui;
    private ApplicationLogic mApplicationLogic;




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
        // if the activity gets closed the current drawable_startscreen_chat_edittext and position in the chat are saved
        //actualizes the currentText and currentScrollPosition in Data
        mApplicationLogic.SaveDataFromScreen();
        mData.saveDataInBundle(outState);
    }

    //Whether this activity (login) is restarted e.g. after finishing profile management, the method onRestart() in ApplicationLogic is called
    @Override
    protected void onRestart() {
        super.onRestart();
        mApplicationLogic.onRestart();
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

    private void initEventToListenerMapping() {
        new EventToListenerMapping(mGui, mApplicationLogic);

    }
}
