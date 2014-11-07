package ch.crut.taxi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

import ch.crut.taxi.R;

@EFragment(R.layout.fragment_from_where)
public class FragmentFromWhere extends Fragment {

    public static FragmentFromWhere newInstance() {
        FragmentFromWhere fragmentFromWhere = new FragmentFromWhere_();
        Bundle bundle = new Bundle();
        fragmentFromWhere.setArguments(bundle);
        return fragmentFromWhere;
    }
}
