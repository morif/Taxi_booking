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
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.request.ServerRequest;
//import ch.crut.taxi.utils.request.UserInfo;


@EFragment(R.layout.fragment_change_password)
public class FragmentChangePassword extends Fragment {

    private ActivityMain activityMain;
    private static final String LOG_TAG = "FragmentChangePassword";

    public static FragmentChangePassword newInstance() {
        FragmentChangePassword fragmentChangePassword = new FragmentChangePassword_();
        Bundle bundle = new Bundle();
        fragmentChangePassword.setArguments(bundle);
        return fragmentChangePassword;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    @ViewById(R.id.fragmentChangesPasswordNewPassword)
    EditText newPasswordEditText;
    @ViewById(R.id.fragmentChangesPasswordOlderPassword)
    EditText olderPasswordEdit;
    @ViewById(R.id.fragmentChangesPasswordCorrectNewPassword)
    EditText correctPasswordEdit;
    @ViewById(R.id.fragmentChangesPasswordButton)
    Button setPusswordButton;

    @Click(R.id.fragmentChangesPasswordButton)
    protected void changeOldPassword() {

//        UserInfo userInfo = UserInfo.getUserInfo();


//        ServerRequest.changeOlderPasswordUser(userInfo, activityMain, onCompleteListener);
    }


    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {


        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

            Log.d(LOG_TAG, "json: " + jsonObject.toString());

            QueryMaster.alert(getActivity(), "ok");

        }
    };
}
