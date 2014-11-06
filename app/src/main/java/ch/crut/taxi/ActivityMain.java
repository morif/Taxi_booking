package ch.crut.taxi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.EActivity;

import ch.crut.taxi.fragmenthelper.FragmentHelper;


@EActivity(R.layout.activity_main)
public class ActivityMain extends FragmentActivity {

    public static final int FRAME_CONTAINER = R.id.activityMainFrameLayout;


    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentHelper.add(fragmentManager, SupportMapFragment.newInstance(), FRAME_CONTAINER);

    }
}
