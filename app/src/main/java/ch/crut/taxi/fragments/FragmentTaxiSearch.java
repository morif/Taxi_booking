package ch.crut.taxi.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ch.crut.taxi.R;

@EFragment(R.layout.fragment_taxi_search)
public class FragmentTaxiSearch extends Fragment {
    

    @Click(R.id.fragmentTaxiSearchDrawRoute)
    protected void clickDrawRoute() {

    }

}
