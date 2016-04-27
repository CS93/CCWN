package de.fhdw.bfws114a.Navigation;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Carsten on 21.04.2016.
 */
public class Navigation {
    public static final Class<?> ACTIVITY_StartScreen_CLASS = de.fhdw.bfws114a.startScreen.Init.class,
            ACTIVITY_DeviceOverview_CLASS = de.fhdw.bfws114a.DeviceOverview.Init.class,
            ACTIVITY_ProfileSettings_CLASS = de.fhdw.bfws114a.profileSettings.Init.class;

    public static void startActivity(Activity callingActivity,
                                      Class <?> classOfActivityToStart){
        Intent intent;
        intent = new Intent();
        intent.setClass(callingActivity, classOfActivityToStart);
        callingActivity.startActivity(intent);
    }
}
