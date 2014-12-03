package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

import ch.crut.taxi.R;
import ch.crut.taxi.interfaces.SmartFragment;

import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;

@SmartFragment(title = R.string.settings, items = {NBItems.BACK})
@EFragment(R.layout.fragment_settings)
public class FragmentSettings extends NBFragment {

    public static FragmentSettings newInstance() {
        FragmentSettings fragmentSettings = new FragmentSettings_();
        Bundle bundle = new Bundle();
        fragmentSettings.setArguments(bundle);
        return fragmentSettings;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentSettings.class);
    }

}
