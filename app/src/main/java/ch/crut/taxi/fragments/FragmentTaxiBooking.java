package ch.crut.taxi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

import ch.crut.taxi.R;

@EFragment(R.layout.fragment_taxi_booking)
public class FragmentTaxiBooking extends Fragment {

    public static FragmentTaxiBooking newInstance() {
        FragmentTaxiBooking fragmentTaxiBooking = new FragmentTaxiBooking_();
        Bundle bundle = new Bundle();
        fragmentTaxiBooking.setArguments(bundle);
        return fragmentTaxiBooking;
    }
}
