package ch.crut.taxi.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.actionbar.NBConnector;
import ch.crut.taxi.utils.actionbar.NBController;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.utils.actionbar.NBItemSelector;

public class NBFragment extends Fragment {


    private NBController NBController;
    private SmartFragment annotationFragment;

    public void onAttach(Activity activity, Class<? extends NBFragment> instance) {
        super.onAttach(activity);

        this.annotationFragment = instance.getAnnotation(SmartFragment.class);

        this.NBController = ((NBConnector) activity).nbConnected();
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
    }

    @Override
    public void onPause() {
        super.onPause();

        NBController.title("");
        NBController.hide(annotationFragment.items());
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (getActivity() instanceof NBItemSelector) {
            NBController.clickListener((NBItemSelector) getActivity());
        }
    }

    private void init() {
        if (annotationFragment != null && NBController != null) {
            NBController.title(getString(annotationFragment.title()));

            NBController.set(annotationFragment.items());

        } else {
            QueryMaster.toast(getActivity(), NBFragment.this.toString() + " was not annotated");
        }

        if (NBFragment.this instanceof NBItemSelector) {
            this.NBController.clickListener((NBItemSelector) this);

        } else if (getActivity() instanceof NBItemSelector) {
            this.NBController.clickListener((NBItemSelector) getActivity());
        }
    }

    public NBController getNBController() {
        return NBController;
    }

}
