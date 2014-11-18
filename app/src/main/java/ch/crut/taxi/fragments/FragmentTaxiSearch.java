package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.google.map.AdapterInfoWindow;
import ch.crut.taxi.utils.google.map.DrawRoute;
import ch.crut.taxi.utils.google.map.GoogleMapUtils;
import ch.crut.taxi.utils.request.Entities;
import ch.crut.taxi.utils.request.ServerRequest;

@SmartFragment(title = R.string.elaboration, items = {NBItems.CANCEL, NBItems.DONE})
@EFragment(R.layout.fragment_taxi_search)
public class FragmentTaxiSearch extends NBFragment implements FragmentGoogleMap.OnMapInitialized {

    private static final int CONTAINER = R.id.fragmentTaxiSearchFrameLayout;


    private NavigationPoint original;
    private NavigationPoint destination;
    private GoogleMapUtils mapUtils;
    private DrawRoute drawRoute;


    public static FragmentTaxiSearch newInstance() {
        FragmentTaxiSearch fragmentTaxiSearch = new FragmentTaxiSearch_();

        Bundle bundle = new Bundle();
        fragmentTaxiSearch.setArguments(bundle);

        return fragmentTaxiSearch;
    }


    @Click(R.id.fragmentTaxiSearchDrawRoute)
    protected void clickDrawRoute() {
        drawRoute.draw(original.latLng, destination.latLng);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentTaxiSearch.class);
        ActivityMain activityMain = (ActivityMain) activity;

        original = activityMain.getTaxiBookingHelper().original;
        destination = activityMain.getTaxiBookingHelper().destination;

        ServerRequest.searchTaxi(original.latLng, (QueryMaster.OnErrorListener) getActivity(),
                onCompleteListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        final FragmentGoogleMap mapFragment = FragmentGoogleMap.newInstance();
        mapFragment.setOnMapInitialized(this);

        FragmentHelper.add(getChildFragmentManager(), mapFragment, CONTAINER);
    }

    private void setUpCamera() {
        mapUtils.moveCamera(original.latLng);
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {
            QueryMaster.alert(getActivity(), jsonObject.toString());

            JSONArray data = QueryMaster.getData(jsonObject);

            List<Entities.SearchTaxi> drives = Entities.SearchTaxi.get(data);

            setUpTaxiDrivers(drives);

        }
    };

    private void setUpTaxiDrivers(List<Entities.SearchTaxi> listDrivers) {
        mapUtils.clearDrivers();
        mapUtils.addDriver(listDrivers);
    }

    private AdapterInfoWindow.OnDriverMarkerClickListener onDriverMarkerClickListener = new AdapterInfoWindow.OnDriverMarkerClickListener() {
        @Override
        public void driverClick(Entities.SearchTaxi driver) {
            QueryMaster.toast(getActivity(), driver.id);
            //((ActivityMain) getActivity()).add();
        }
    };

    @Override
    public void mapWasInitialized(GoogleMap googleMap) {
        mapUtils = new GoogleMapUtils(getActivity(), googleMap);
        mapUtils.setOnDriverMarkerClick(onDriverMarkerClickListener);

        mapUtils.addOriginMarker(original.latLng);
        mapUtils.addDestinationMarker(destination.latLng);

        drawRoute = new DrawRoute(getActivity(), mapUtils);

        setUpCamera();
    }
}
