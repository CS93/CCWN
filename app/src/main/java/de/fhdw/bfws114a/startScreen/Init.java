package de.fhdw.bfws114a.startScreen;


/**
 * Created by Carsten on 16.04.2016.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import de.fhdw.bfws114a.Communication.StartedService;

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

/*
        Intent service = new Intent(this, StartedService.class);
        PendingIntent servicePendingIntent =
                PendingIntent.getService(this, 0, service, 0);

        //Wie gross soll der Intervall sein?
        long interval = DateUtils.MINUTE_IN_MILLIS * 2; // Alle 2 Minuten

        //Wann soll der Service das erste Mal gestartet werden?
        long firstStart = System.currentTimeMillis() + interval;

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //am.set(AlarmManager.RTC, firstStart, servicePendingIntent);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, firstStart, interval,
        //											servicePendingIntent);
        am.setInexactRepeating(AlarmManager.RTC, firstStart, interval,
                servicePendingIntent);

        Log.v("Backgroundservice", "AlarmManager gesetzt");
*/

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
