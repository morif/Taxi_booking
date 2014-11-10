package ch.crut.taxi.fragments.branch.from.where;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.actionbar.ActionBarController;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.ActionBarClickListener;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.GoogleMapUtils;
import ch.crut.taxi.utils.LocationAddress;
import ch.crut.taxi.utils.TaxiBookingHelper;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

@EFragment(R.layout.fragment_from_where)
public class FromWhereAutoLocation extends Fragment implements ActionBarClickListener {

    private static final int FRAME_CONTAINER = R.id.fragmentFromWhereFrameLayout;

    private ActionBarController barController;
    private GoogleMapUtils mapUtils;
    private LocationAddress locationAddress;
    private TaxiBookingHelper taxiBookingHelper;

    public static FromWhereAutoLocation newInstance() {
        FromWhereAutoLocation fromWhereAutoLocation = new FromWhereAutoLocation_();
        Bundle bundle = new Bundle();
        fromWhereAutoLocation.setArguments(bundle);
        return fromWhereAutoLocation;
    }

    @ViewById(R.id.fragmentFromWhereAddress)
    protected EditText address;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        final ActivityMain activityMain = (ActivityMain) activity;
        activityMain.replaceActionBarClickListener(this);

        barController = activityMain.getActionBarController();
        taxiBookingHelper = activityMain.getTaxiBookingHelper();
    }

    @Override
    public void onStart() {
        super.onStart();

        barController.title(getString(R.string.elaboration));
        barController.cancelEnabled(true);
        barController.doneEnabled(true);

        final SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentHelper.add(getChildFragmentManager(), mapFragment, FRAME_CONTAINER);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GoogleMap map = mapFragment.getMap();
                map.setMyLocationEnabled(true);

                mapUtils = new GoogleMapUtils(mapFragment.getMap());

                findMyLocation(getActivity());
            }
        }, 100);
    }

    @Override
    public void onPause() {
        super.onPause();

        barController.cancelEnabled(false);
        barController.doneEnabled(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ((ActivityMain) getActivity()).setActionBarDefaultListener();
    }

    private LocationAddress.OnCompleteListener onAddressFound = new LocationAddress.OnCompleteListener() {
        @Override
        public void complete(List<Address> addresses) {
            final Address userAddress = addresses.get(0);

            address.setText(userAddress.getAddressLine(0)
                    + ", " + userAddress.getAddressLine(1));
        }

        @Override
        public void error() {
            QueryMaster.toast(getActivity(), R.string.fail_getting_address);
        }
    };

    private void findMyLocation(final Context context) {
        long LOCATION_UPDATE_INTERVAL = 10;
        long WAIT_TIME = 10;
        long LOCATION_TIMEOUT_IN_SECONDS = 10;

        LocationRequest req = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setExpirationDuration(TimeUnit.SECONDS.toMillis(WAIT_TIME))
                .setInterval(LOCATION_UPDATE_INTERVAL);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);


        Observable<Location> goodEnoughQuicklyOrNothingObservable = locationProvider.getUpdatedLocation(req)
                .filter(new Func1<Location, Boolean>() {
                    @Override
                    public Boolean call(Location location) {

                        mapUtils.moveCamera(location.getLatitude(), location.getLongitude());

                        locationAddress = new LocationAddress(context,
                                location.getLatitude(), location.getLongitude());
                        locationAddress.setOnCompleteListener(onAddressFound);
                        locationAddress.start();

                        return true;
                    }
                })
                .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS, Observable.from((Location) null), AndroidSchedulers.mainThread())
                .first()
                .observeOn(AndroidSchedulers.mainThread());

        goodEnoughQuicklyOrNothingObservable.subscribe();
    }

    @Override
    public void clickSettings(View view) {

    }

    @Override
    public void clickBack(View view) {

    }

    @Override
    public void clickCancel(View view) {

    }

    @Override
    public void clickDone(View view) {

    }
}

