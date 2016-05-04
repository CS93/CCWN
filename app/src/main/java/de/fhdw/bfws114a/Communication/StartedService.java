package de.fhdw.bfws114a.Communication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Carsten on 04.05.2016.
 */
public class StartedService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
     // not required because we wont use bounded services
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("BackgroundService", System.currentTimeMillis()
                + ": BackgroundService erstellt.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("BackgroundService", System.currentTimeMillis()
                + ": BackgroundService gestartet.");

        // executableMethod
        // updateData();

        // StopService after finishing its work (the service will be started regularly by the alarmmanager
        stopSelf();

        // through Start_Sticky the service will be started again whether it was killed e.g. by Android System causing memory issues
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v("BackgroundService", System.currentTimeMillis()
                + ": BackgroundService zerstoert.");
    }
}
