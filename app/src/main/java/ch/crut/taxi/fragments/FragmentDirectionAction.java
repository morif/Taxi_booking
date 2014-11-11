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
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;

@EFragment(R.layout.fragment_direction_action)
public class FragmentDirectionAction extends Fragment {

    private ActionBarController barController;
    private OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey;

    public static FragmentDirectionAction newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {
        FragmentDirectionAction fragmentDirectionAction = new FragmentDirectionAction_();

        Bundle bundle = new Bundle();
        bundle.putSerializable(OnPlaceSelectedListener
                .PLACE_SELECTED_KEY_SERIALIZABLE, placeSelectedKey);

        fragmentDirectionAction.setArguments(bundle);

        return fragmentDirectionAction;
    }

    @ViewById(R.id.fragmentDirectionActionAddress)
    protected EditText address;

    @Click(R.id.fragmentDirectionActionMyLocation)
    protected void clickMyLocation() {
        final FragmentPlaceSelector placeSelector = FragmentPlaceSelector.newInstance(
                placeSelectedKey, true);
        ((ActivityMain) getActivity()).add(placeSelector);
    }

    @Click(R.id.fragmentDirectionActionSetLocation)
    protected void clickSetLocation() {
        final FragmentPlaceSelector placeSelector = FragmentPlaceSelector
                .newInstance(placeSelectedKey);

        ((ActivityMain) getActivity()).add(placeSelector);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle bundle = getArguments();

        placeSelectedKey = (OnPlaceSelectedListener.PlaceSelectedKeys)
                bundle.getSerializable(OnPlaceSelectedListener.PLACE_SELECTED_KEY_SERIALIZABLE);
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
