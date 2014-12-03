package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.request.DriverInfo;
import ch.crut.taxi.utils.request.ServerRequest;
import ch.crut.taxi.utils.request.UserInfo;

/**
 * Created by Alex on 25.11.2014.
 */
@EFragment(R.layout.fragment_driver_authoritation)
public class FragmentDrivierAuthoritation extends Fragment {


    private static final String LOG_TAG = "FragmentDriverAuthoritation";
    private ActivityMain activityMain;

    public static FragmentDrivierAuthoritation newInstance() {
        FragmentDrivierAuthoritation fragmentDrivierAuthoritation = new FragmentDrivierAuthoritation_();
        Bundle bundle = new Bundle();
        fragmentDrivierAuthoritation.setArguments(bundle);
        return fragmentDrivierAuthoritation;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    @ViewById(R.id.emailDriverEdit)
    protected EditText emailDriverEditText;

    @ViewById(R.id.passwordDriverEdit)
    protected EditText passwordDriverEditText;

    @ViewById(R.id.entranceDriverButton)
    protected Button entranceDriverButton;

    @ViewById(R.id.registrationDriverButton)
    protected Button registrationDriverButton;

    @Click(R.id.entranceDriverButton)
    protected void entranceInAuthoritation() {
        if (emailDriverEditText.getText().length() > 0 && passwordDriverEditText.getText().length() > 0) {
            Log.d(LOG_TAG, "not 0");
           String email = emailDriverEditText.getText().toString();
            String password = passwordDriverEditText.getText().toString();


            ServerRequest.authoritationDriver(email, password, activityMain, onCompleteListener);
            Log.d(LOG_TAG, "started");
        }
    }
@Click(R.id.registrationDriverButton)
protected void registrationDriver(){

}

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

            Log.d(LOG_TAG, "json: " + jsonObject.toString());
            // JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            //  Log.d(LOG_TAG, "json: " + jsonObject1.toString());

        }
    };

}
