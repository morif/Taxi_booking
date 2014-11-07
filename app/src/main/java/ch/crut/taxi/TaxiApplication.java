package ch.crut.taxi;


import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class TaxiApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault("fonts/SegoePrint.ttf", R.attr.fontPath);
    }
}
