package ch.crut.taxi;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class TaxiApplication extends Application {

    private static TaxiApplication taxiApplication;


    @Override
    public void onCreate() {
        super.onCreate();

        taxiApplication = this;

//        CalligraphyConfig.initDefault("fonts/SegoePrint.ttf", R.attr.fontPath);
    }

    private Activity mCurrentActivity = null;

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public static Activity getRunningActivityContext() {
        return taxiApplication.getCurrentActivity();
    }
}
