package ch.crut.taxi.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;

/**
 * Created by Alex on 26.11.2014.
 */
@EFragment(R.layout.fragment_popup)
public class FragmentPopup extends Fragment {
    private ActivityMain activityMain;

    public static FragmentPopup newInstance() {
        FragmentPopup fragmentPopup = new FragmentPopup_();
        Bundle bundle = new Bundle();
        fragmentPopup.setArguments(bundle);
        return fragmentPopup;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }
@ViewById(R.id.layout)
protected LinearLayout layout;
    @Override
    public void onStart() {
        super.onStart();

        LayoutInflater layoutInflater
                = (LayoutInflater) activityMain
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_fragment, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            }
        });
    }
}
