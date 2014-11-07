package ch.crut.taxi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;

import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import ch.crut.taxi.actionbar.ActionBarController;
import ch.crut.taxi.actionbar.UIActionBar;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.fragments.FragmentTaxiBooking;
import ch.crut.taxi.interfaces.ActionBarClickListener;
import ch.crut.taxi.querymaster.QueryMaster;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


@WindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
@EActivity(R.layout.activity_main)
public class ActivityMain extends FragmentActivity implements ActionBarClickListener {

    public static final int MAP_CONTAINER = R.id.activityMainMap;
    public static final int FRAME_CONTAINER = R.id.activityMainContainer;


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ActionBarController actionBarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIActionBar uiActionBar = new UIActionBar(getActionBar());
        actionBarController = new ActionBarController(uiActionBar);
        actionBarController.setActionBarClickListener(this);

        actionBarController.settingEnabled();
        actionBarController.setTitle(getString(R.string.taxi_booking));
        actionBarController.hideLeft();

        FragmentHelper.add(fragmentManager, SupportMapFragment.newInstance(), MAP_CONTAINER);
        FragmentHelper.add(fragmentManager, FragmentTaxiBooking.newInstance(), FRAME_CONTAINER);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void clickSettings(View view) {
        QueryMaster.toast(this, "settings");
    }

    @Override
    public void clickBack(View view) {
        QueryMaster.toast(this, "back");
    }

}
