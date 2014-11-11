package ch.crut.taxi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.actionbar.ActionBarController;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.TaxiBookingHelper;

@EFragment(R.layout.fragment_taxi_booking)
public class FragmentTaxiBooking extends Fragment {

    private ActionBarController barController;

    private NavigationPoint originalPoint;
    private NavigationPoint destinationPoint;

    public static FragmentTaxiBooking newInstance() {
        FragmentTaxiBooking fragmentTaxiBooking = new FragmentTaxiBooking_();
        Bundle bundle = new Bundle();
        fragmentTaxiBooking.setArguments(bundle);
        return fragmentTaxiBooking;
    }

    @ViewById(R.id.fragmentTaxiBookingOrigin)
    protected EditText originAddress;

    @ViewById(R.id.fragmentTaxiBookingDestination)
    protected EditText destinationAddress;

    @Click(R.id.fragmentTaxiBookingOriginButton)
    protected void clickOriginal() {
        final OnPlaceSelectedListener.PlaceSelectedKeys originalKey = OnPlaceSelectedListener.PlaceSelectedKeys.ORIGINAL;
        ((ActivityMain) getActivity()).add(FragmentDirectionAction.newInstance(originalKey));
    }

    @Click(R.id.fragmentTaxiBookingDestinationButton)
    protected void clickDestination() {
        final OnPlaceSelectedListener.PlaceSelectedKeys destinationKey = OnPlaceSelectedListener.PlaceSelectedKeys.DESTINATION;
        ((ActivityMain) getActivity()).add(FragmentDirectionAction.newInstance(destinationKey));
    }

    @Override
    public void onStart() {
        super.onStart();
        barController = ((ActivityMain) getActivity()).getActionBarController();
        barController.title(getString(R.string.taxi_booking));
        barController.settingEnabled(true);


        TaxiBookingHelper bookingHelper = ((ActivityMain) getActivity()).getTaxiBookingHelper();

        originalPoint = bookingHelper.original;
        destinationPoint = bookingHelper.destination;

        if (originalPoint != null) {
            originAddress.setText(originalPoint.addressString);
        }

        if (destinationPoint != null) {
            destinationAddress.setText(destinationPoint.addressString);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        barController.settingEnabled(false);
    }

    public void setFrom(String fromAddress) {
        this.originAddress.setText(fromAddress);
    }


}

