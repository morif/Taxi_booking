package ch.crut.taxi;


import android.app.Activity;
import android.app.Application;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ch.crut.taxi.database.DAOFavorite;
import ch.crut.taxi.interfaces.UserPref_;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


@EApplication
public class TaxiApplication extends Application {

    private static TaxiApplication taxiApplication;

    @Pref
    protected UserPref_ userLocationPref;

    private DAOFavorite daoFavorite;

    @Override
    public void onCreate() {
        super.onCreate();

        taxiApplication = this;

        CalligraphyConfig.initDefault("fonts/SegoePrint.ttf", R.attr.fontPath);


        daoFavorite = new DAOFavorite(this);
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

    public static UserPref_ getUserPrefs() {
        return taxiApplication.userLocationPref;
    }

    public static boolean isUserAuthorized() {
        return !getUserPrefs().id().get().isEmpty();
    }

    public static DAOFavorite getDaoFavorite() {
        return taxiApplication.daoFavorite;
    }
}
