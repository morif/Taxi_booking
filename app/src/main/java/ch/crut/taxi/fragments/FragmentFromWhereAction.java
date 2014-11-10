package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.actionbar.ActionBarController;
import ch.crut.taxi.fragments.branch.from.where.FromWhereAutoLocation;

@EFragment(R.layout.fragment_from_where_action)
public class FragmentFromWhereAction extends Fragment {

    private ActionBarController barController;

    public static FragmentFromWhereAction newInstance() {
        FragmentFromWhereAction fragmentFromWhereAction = new FragmentFromWhereAction_();
        Bundle bundle = new Bundle();
        fragmentFromWhereAction.setArguments(bundle);
        return fragmentFromWhereAction;
    }

    @ViewById(R.id.fragmentFromWhereActionAddress)
    protected EditText address;

    @Click(R.id.fragmentFromWhereActionMyLocation)
    protected void clickMyLocation() {
        ((ActivityMain) getActivity()).add(FromWhereAutoLocation.newInstance());
    }

    @Click(R.id.fragmentFromWhereActionSetLocation)
    protected void clickSetLocation() {
        ((ActivityMain) getActivity()).add(FragmentFromWhere.newInstance());
    }

    @Override
    public void onStart() {
        super.onStart();

        barController = ((ActivityMain) getActivity()).getActionBarController();
        barController.title(getString(R.string.from_where));
        barController.cancelEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        barController.cancelEnabled(false);
    }
}
