package de.fhdw.bfws114a.profileSettings;

/**
 * Created by Carsten on 21.04.2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.fhdw.bfws114a.R;

public class Init extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initData(savedInstanceState);
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
}
