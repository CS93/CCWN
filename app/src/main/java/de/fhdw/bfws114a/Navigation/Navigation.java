package de.fhdw.bfws114a.Navigation;

import android.app.Activity;
import android.content.Intent;

import de.fhdw.bfws114a.Communication.MacAddressList;
import de.fhdw.bfws114a.data.Constants;

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

    public static void startActivityDeviceOverview(Activity callingActivity, MacAddressList devicelist){
        startActivityWithMacAddressList(callingActivity,
                ACTIVITY_DeviceOverview_CLASS,
                Constants.KEY_CURRENT_DEVICELIST,
                devicelist);
    }

    //This mehtod is called when a new activity is called and a user should be given to the new activity through intent
    private static void startActivityWithMacAddressList(Activity callingActivity,
                                                        Class <?> classOfActivityToStart,
                                                        String key, MacAddressList devicelist){
        Intent intent;
        intent = new Intent();
        intent.putExtra(key, devicelist);
        intent.setClass(callingActivity, classOfActivityToStart);
        callingActivity.startActivity(intent);
    }

}
