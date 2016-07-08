package app.famousenuf.base;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by bharatbhusan on 30/6/16.
 */
public class FamousenufApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Facebook SDK.
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
