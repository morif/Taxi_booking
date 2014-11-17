package ch.crut.taxi.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import ch.crut.taxi.utils.actionbar.NBConnector;
import ch.crut.taxi.utils.actionbar.NBController;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.utils.actionbar.NBItemSelector;

public class NBFragment extends Fragment {


    private NBController NBController;
    private SmartFragment annotationFragment;

    public void onAttach(Activity activity, Class<? extends NBFragment> instance) {
        this.onAttach(activity, null, instance);
    }

    public void onAttach(Activity activity, NBItemSelector itemSelector, Class<? extends NBFragment> instance) {
        super.onAttach(activity);

        this.annotationFragment = instance.getAnnotation(SmartFragment.class);

        this.NBController = ((NBConnector) activity).nbConnected();


        if (itemSelector != null) {
            this.NBController.set(itemSelector);

        } else if (activity instanceof NBItemSelector) {
            this.NBController.set((NBItemSelector) activity);
        }

        init();
    }

    private void init() {
        if (annotationFragment != null) {
            NBController.title(annotationFragment.title());

            NBController.set(annotationFragment.items());
        } else {
            NBController.title("NuLL");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        NBController.title("");
        NBController.hide(annotationFragment.items());
    }
}
