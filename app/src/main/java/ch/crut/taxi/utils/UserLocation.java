package ch.crut.taxi.utils;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import java.util.List;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.interfaces.UserLocationPref_;
import ch.crut.taxi.querymaster.QueryMaster;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UserLocation {

    private Activity activity;
    private ReactiveLocationProvider locationProvider;
    private Subscription addressSubscription;

    public UserLocation(Activity activity) {
        this.activity = activity;
    }

    public void update() {
        locationProvider = new ReactiveLocationProvider(activity);


        Observable<Location> locationUpdatesObservable = locationProvider.getUpdatedLocation(
                LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setNumUpdates(5)
                        .setInterval(10000)
        );


        addressSubscription = AndroidObservable.bindActivity(activity, locationUpdatesObservable
                .flatMap(new Func1<Location, Observable<List<Address>>>() {
                    @Override
                    public Observable<List<Address>> call(Location location) {

                        addressSubscription.unsubscribe();

                        UserLocationPref_ userLocationPref = TaxiApplication.getUserPrefs();
                        userLocationPref.edit()
                                .latitude().put((float) location.getLatitude())
                                .longitude().put((float) location.getLongitude())
                                .apply();

                        return locationProvider.getGeocodeObservable(location.getLatitude(), location.getLongitude(), 1);
                    }
                })
                .map(new Func1<List<Address>, Address>() {
                    @Override
                    public Address call(List<Address> addresses) {
                        return addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
                    }
                })
                .map(new SaveUserAddress())
                .subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisplayTextOnViewAction());
    }

    public void destroy() {
        addressSubscription.unsubscribe();
    }

    private class DisplayTextOnViewAction implements Action1<String> {

        @Override
        public void call(String s) {
//            QueryMaster.toast(activity, "DisplayTextOnViewAction -> " + s);
        }
    }

    private class SaveUserAddress implements Func1<Address, String> {

        @Override
        public String call(Address address) {
            if (address == null) {
                return null;
            }

            String street = address.getAddressLine(0);

            TaxiApplication.getUserPrefs().street().put(street);

            QueryMaster.alert(TaxiApplication.getRunningActivityContext(), TaxiApplication.getUserPrefs().street().get() + "," +
                    TaxiApplication.getUserPrefs().latitude().get() + ", " + TaxiApplication.getUserPrefs().longitude().get());

            return null;
        }
    }


}
