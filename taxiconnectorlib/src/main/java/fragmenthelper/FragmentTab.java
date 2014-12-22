package fragmenthelper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import fragmenthelper.FragmentHelper;


public class FragmentTab extends Fragment {

    private boolean isAttached;

    private Fragment fragment;
    private int container;


    public void onAttach(Activity activity, Fragment fragment, int container) {
        attach(fragment, container);
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isAttached) {
            FragmentHelper.add(getChildFragmentManager(), fragment, container);

            isAttached = true;
        }
    }

    protected void attach(Fragment fragment, int container) {
        this.fragment = fragment;
        this.container = container;
    }
}
