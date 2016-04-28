package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.fhdw.bfws114a.R;

public class Init extends AppCompatActivity {

    private Data mData;
/*
    private Gui mGui;
    private ApplicationLogic mApplicationLogic;
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        //initGui();
        //initApplicationLogic();
        //initEventToListenerMapping();

        //Testen der Übergabe
        setContentView(R.layout.activity_profilesettings);
    }

    @Override
    public void onBackPressed() {
        //close the app
        finish();
    }

    private void initData(Bundle savedInstanceState) {
        mData = new Data(savedInstanceState, this);
    }

/*
    private void initGui() {
        mGui = new Gui(this);

    }

    private void initApplicationLogic() {
        mApplicationLogic = new ApplicationLogic(mData, mGui);
    }

    private void initEventToListenerMapping() {
        new EventToListenerMapping(mGui, mApplicationLogic);

    }*/
}
