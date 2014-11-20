package ch.crut.taxi.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;

@EFragment(R.layout.fragment_settings)
public class FragmentSettings extends Fragment {

    private ActivityMain activityMain;
private final String LOG_TAG = "FragmentSettings";

    public static FragmentSettings newInstance() {
        FragmentSettings fragmentSettings = new FragmentSettings_();
        Bundle bundle = new Bundle();
        fragmentSettings.setArguments(bundle);
        return fragmentSettings;
    }
    @ViewById(R.id.switcher)
    protected Switch aSwitch;
    @ViewById(R.id.layout)
    protected LinearLayout layout;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onStart() {
        super.onStart();

        Switch switchInput = new Switch(activityMain);
      //  int colorOn = 0xFF0000;
       // int colorOff = 0xFF0000;
       // int colorDisabled = 0xFF0000;
        StateListDrawable thumbStates = new StateListDrawable();
        thumbStates.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(R.color.blue));
        thumbStates.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(R.color.blue));
      //  thumbStates.addState(new int[]{}, new ColorDrawable(activityMain.app.colorOff)); // this one has to come last
        switchInput.setThumbDrawable(thumbStates);
        layout.addView(switchInput);


//    aSwitch.setChecked(true);
//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//               if(isChecked){
//                   Log.d(LOG_TAG, "true");
//               } else{
//                   Log.d(LOG_TAG, "false");
//               }
//            }
//        });

    }




}
