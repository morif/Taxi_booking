package ua.sv.ch.taxi.driver;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.androidannotations.annotations.EActivity;

import fragmenthelper.FragmentHelper;


@EActivity(R.layout.activity_main)
public class ActivityMain extends FragmentActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentHelper.
    }
}
