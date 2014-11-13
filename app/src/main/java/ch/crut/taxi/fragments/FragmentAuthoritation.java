package ch.crut.taxi.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities;
import ch.crut.taxi.utils.request.Entities;
import ch.crut.taxi.utils.request.ServerRequest;
import ch.crut.taxi.utils.request.UserInfo;

@EFragment(R.layout.fragment_authoritation)
public class FragmentAuthoritation extends Fragment {

    private static final String LOG_TAG = "FragmentAuthoritation";
    private String loginString;
    private String passwordString;
    private String telephoneString;
    private ActivityMain activityMain;

    public static FragmentAuthoritation newInstance() {
        FragmentAuthoritation fragmentAuthoritation = new FragmentAuthoritation_();
        Bundle bundle = new Bundle();
        fragmentAuthoritation.setArguments(bundle);
        return fragmentAuthoritation;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

            UserInfo userInfo = new UserInfo();
            Log.d(LOG_TAG, "json: " + jsonObject.toString());

            JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            Log.d(LOG_TAG, "json: " + jsonObject1.toString());

            userInfo.setEmail(jsonObject1.getString("email"));
            userInfo.setTelephoneFirst(jsonObject1.getString("tel1"));
            userInfo.setTelephoneSecond(jsonObject1.getString("tel2"));
            userInfo.setId(jsonObject1.getString("id"));
            userInfo.setLogin(jsonObject1.getString("login"));
            userInfo.setName(jsonObject1.getString("name"));
           saveUserInfo(userInfo);
             QueryMaster.alert(getActivity(), userInfo.getId());


        }
    };

    @ViewById(R.id.loginEdit)
    protected EditText loginEditText;

    @ViewById(R.id.passwordEdit)
    protected EditText passwordEditText;

    @ViewById(R.id.confirmButton)
    protected Button confButton;

    @Click(R.id.confirmButton)
    protected void clickConfirmButton() {


        loginString = loginEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();

        if (loginString.length() > 0 && passwordString.length() > 0) {

            RequestEntities.Registrer registrer = new RequestEntities.Registrer();
            registrer.setLogin(loginString);
            registrer.setPassword(passwordString);
            ServerRequest.authoritationClient(registrer, activityMain, onCompleteListener);

        }
    }

    private void saveUserInfo(UserInfo userInfo) {
        SharedPreferences sPref = activityMain.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("id", userInfo.getId());
        ed.putString("email", userInfo.getEmail());
        ed.putString("name", userInfo.getName());
        ed.putString("telephoneFirst", userInfo.getTelephoneFirst());
        ed.putString("telephoneSecond", userInfo.getTelephoneSecond());
        ed.putString("login", userInfo.getLogin());
Log.d(LOG_TAG, sPref.getString("name",""));
        ed.commit();

    }


}
