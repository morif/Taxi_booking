package ch.crut.taxi;


import android.app.Activity;
import android.app.Application;

public class TaxiApplication extends Application {

    private static TaxiApplication taxiApplication;


    @Override
    public void onCreate() {
        super.onCreate();

        taxiApplication = this;

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
