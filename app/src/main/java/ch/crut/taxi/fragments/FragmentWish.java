package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapter.WishCustomAdapter;

@EFragment(R.layout.fragment_wish)
public class FragmentWish extends Fragment {

    private static final String LOG_TAG = "FragmentWish";
    private ActivityMain activityMain;

    public static FragmentWish newInstance() {
        FragmentWish fragmentWish = new FragmentWish_();
        Bundle bundle = new Bundle();
        fragmentWish.setArguments(bundle);
        return fragmentWish;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
        Log.d(LOG_TAG, "attach");
    }
    @ViewById(R.id.wishListView)
    protected ListView wishList;

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "start");
        WishCustomAdapter wishCustomAdapter = new WishCustomAdapter(activityMain);
        Log.d(LOG_TAG, "start1");
        wishList.setAdapter(wishCustomAdapter);
Log.d(LOG_TAG, "start2");

    }
}
