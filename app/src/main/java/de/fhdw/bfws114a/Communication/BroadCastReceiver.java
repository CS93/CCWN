package de.fhdw.bfws114a.Communication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.text.format.DateUtils;

/**
 * Created by Carsten on 04.05.2016.
 */
public class BroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Log.v("BackgroundProcess", "Device wurde neugestartet");
            Intent wtdSServiceIntent = new Intent(context,
                    StartedService.class);
            PendingIntent wtdSServicePendingIntent = PendingIntent.
                    getService(context, 0, wtdSServiceIntent, 0);

            long interval = DateUtils.MINUTE_IN_MILLIS * 3; //Alle 3 Minuten
            long firstStart = System.currentTimeMillis() + interval;

            AlarmManager am = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            am.setInexactRepeating(AlarmManager.RTC, firstStart,
                    interval, wtdSServicePendingIntent);

            Log.v("BackgroundProcess", "AlarmManager gesetzt");
        }
    }
}
