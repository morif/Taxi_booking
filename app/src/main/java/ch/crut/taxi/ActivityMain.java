package ch.crut.taxi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main)
public class ActivityMain extends FragmentActivity {

    public static final int FRAME_CONTAINER = R.id.activityMainFrameLayout;


    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
