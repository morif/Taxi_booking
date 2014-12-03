package ch.crut.taxi.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapters.WishCustomAdapter;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;


@EFragment(R.layout.fragment_wish)
@SmartFragment(title = R.string.wishes, items = {NBItems.DONE, NBItems.BACK})
public class FragmentWish extends NBFragment implements NBItemSelector {

    private WishCustomAdapter wishCustomAdapter;

    public static FragmentWish newInstance() {
        FragmentWish fragmentWish = new FragmentWish_();
        Bundle bundle = new Bundle();
        fragmentWish.setArguments(bundle);
        return fragmentWish;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentWish.class);

        wishCustomAdapter = new WishCustomAdapter(activity);
    }

    @ViewById(R.id.wishListView)
    protected ListView wishList;


    @Override
    public void onStart() {
        super.onStart();

        wishList.setAdapter(wishCustomAdapter);
    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.DONE:
                ((ActivityMain) getActivity()).setUserWishes(
                        wishCustomAdapter.checkedCheckInWishObjects());
                break;
            case NBItems.CANCEL:

                break;
        }

        FragmentHelper.pop(getFragmentManager());
    }
}
