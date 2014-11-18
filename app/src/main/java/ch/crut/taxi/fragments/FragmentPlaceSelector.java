package ch.crut.taxi.fragments;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.FavoriteHelper;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.google.map.GoogleMapUtils;
import ch.crut.taxi.utils.google.map.LocationAddress;
import ch.crut.taxi.views.NiceProgressDialog;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


@SmartFragment(items = {NBItems.CANCEL, NBItems.DONE})
@EFragment(R.layout.fragment_place_selector)
public class FragmentPlaceSelector extends NBFragment implements NBItemSelector {

    private static final int FRAME_CONTAINER = R.id.fragmentFromWhereFrameLayout;

    public static final String SELECTED_PLACE_KEY = "SELECTED_PLACE_KEY";
    public static final String AUTO_LOCATION = "AUTO_LOCATION";


    private OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey;
    private boolean autoLocation;


    //    private NBController barController;
    private GoogleMapUtils mapUtils;
    private NavigationPoint navigationPoint;
    private NiceProgressDialog niceProgressDialog;


    public static FragmentPlaceSelector newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey, boolean autoLocation) {
        FragmentPlaceSelector placeSelector = new FragmentPlaceSelector_();

        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_PLACE_KEY, placeSelectedKey);
        bundle.putBoolean(AUTO_LOCATION, autoLocation);

        placeSelector.setArguments(bundle);

        return placeSelector;
    }

    public static FragmentPlaceSelector newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {
        return newInstance(placeSelectedKey, false);
    }

    @ViewById(R.id.fragmentFromWhereAddress)
    protected EditText addressEditText;

    @ViewById(R.id.fragmentPlaceSelectorEntrance)
    protected EditText entranceEditText;

    @ViewById(R.id.fragmentPlaceSelectorHome)
    protected EditText homeEditText;

    @Click(R.id.fragmentPlaceSelectorLocation)
    protected void clickLocation() {
        findMyLocation(getActivity());
    }

    @Click(R.id.fragmentPlaceSelectorAddFavorite)
    protected void clickAddFavorite() {
        FavoriteHelper.add(navigationPoint, placeSelectedKey);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentPlaceSelector.class);

        Bundle bundle = getArguments();

        placeSelectedKey = (OnPlaceSelectedListener.PlaceSelectedKeys) bundle.getSerializable(SELECTED_PLACE_KEY);
        autoLocation = bundle.getBoolean(AUTO_LOCATION);
        navigationPoint = new NavigationPoint();


        final ActivityMain activityMain = (ActivityMain) activity;
//        activityMain.replaceActionBarClickListener(this);

        niceProgressDialog = new NiceProgressDialog(activity);
    }

    @Override
    public void onStart() {
        super.onStart();

        final SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentHelper.add(getChildFragmentManager(), mapFragment, FRAME_CONTAINER);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GoogleMap map = mapFragment.getMap();

                mapUtils = new GoogleMapUtils(getActivity(), map);

                mapUtils.getMap().setOnMapClickListener(onMapClick);
                mapUtils.getMap().setMyLocationEnabled(true);

                if (autoLocation)
                    findMyLocation(getActivity());
            }
        }, 100);
    }

    @Override
    public void onDetach() {
        super.onDetach();

//        ((ActivityMain) getActivity()).setActionBarDefaultListener();
    }

//    @Override
//    public void clickSettings(View view) {
//        ((ActivityMain) getActivity()).clickSettings(view);
//    }
//
//    @Override
//    public void clickBack(View view) {
//        ((ActivityMain) getActivity()).clickBack(view);
//
//    }
//
//    @Override
//    public void clickCancel(View view) {
//        ((ActivityMain) getActivity()).clickCancel(view);
//    }
//
//    @Override
//    public void clickDone(View view) {
//        ((ActivityMain) getActivity()).placeSelected(navigationPoint, placeSelectedKey);
//        ((ActivityMain) getActivity()).initialScreen();
//    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.DONE:
                ((ActivityMain) getActivity()).placeSelected(navigationPoint, placeSelectedKey);
                ((ActivityMain) getActivity()).initialScreen();
                break;
            case NBItems.CANCEL:
                FragmentHelper.pop(getFragmentManager());
                break;
        }
    }

    public final class OnAddressFoundListener implements LocationAddress.OnCompleteListener {

        private final LatLng latLng;

        public OnAddressFoundListener(LatLng latLng) {
            this.latLng = latLng;
        }

        @Override
        public void complete(List<Address> addresses) {
            if (addresses != null && addresses.size() > 0) {

                final Address userAddress = addresses.get(0);
                final String addressString = userAddress.getAddressLine(0) + ", " + userAddress.getAddressLine(1);

                navigationPoint.addressString = addressString;
                navigationPoint.latLng = latLng;
                navigationPoint.address = userAddress;
                navigationPoint.setHome();

                addressEditText.setText(addressString);
                homeEditText.setText(navigationPoint.getHome());
            }

            niceProgressDialog.dismiss();
        }

        @Override
        public void error() {
            niceProgressDialog.dismiss();
            QueryMaster.alert(getActivity(), R.string.fail_getting_address);
        }
    }

    private GoogleMap.OnMapClickListener onMapClick = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {

            mapUtils.getMap().clear();
            mapUtils.me(latLng);

            niceProgressDialog.show();

            OnAddressFoundListener onAddressFoundListener =
                    new OnAddressFoundListener(latLng);

            LocationAddress locationAddress = new LocationAddress(getActivity(), latLng);
            locationAddress.setOnCompleteListener(onAddressFoundListener);
            locationAddress.start();
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
                        final LatLng latLng = new LatLng(location.getLatitude(),
                                location.getLongitude());

                        final OnAddressFoundListener onAddressFoundListener = new
                                OnAddressFoundListener(latLng);
                        final LocationAddress locationAddress = new LocationAddress(context,
                                location.getLatitude(), location.getLongitude());

                        mapUtils.moveCamera(latLng);


                        locationAddress.setOnCompleteListener(onAddressFoundListener);
                        locationAddress.start();

                        return true;
                    }
                })
                .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS, Observable.from((Location) null), AndroidSchedulers.mainThread())
                .first()
                .observeOn(AndroidSchedulers.mainThread());

        goodEnoughQuicklyOrNothingObservable.subscribe();

        niceProgressDialog.show();
    }
}
