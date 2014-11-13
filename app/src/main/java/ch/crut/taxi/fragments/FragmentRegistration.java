package ch.crut.taxi.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities;
import ch.crut.taxi.utils.request.ServerRequest;
import ch.crut.taxi.utils.request.UserInfo;

/**
 * Created by Alex on 13.11.2014.
 */
@EFragment(R.layout.fragment_registration)
public class FragmentRegistration extends Fragment {

    private static final String LOG_TAG = "FragmentRegistration";
    private String emailAddressRegistrationString;
    private String nameRegistrationString;
    private String telephoneFirstRegistrationString;
    private String telephoneSecondRegistrationString;
    private String loginRegistrationString;
    private String passwordRegistrationString;
    private ActivityMain activityMain;


    public static FragmentRegistration newInstance() {
        FragmentRegistration fragmentRegistration = new FragmentRegistration_();
        Bundle bundle = new Bundle();
        fragmentRegistration.setArguments(bundle);
        return fragmentRegistration;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {
            if (QueryMaster.isSuccess(jsonObject)) {

                Log.d(LOG_TAG, jsonObject.toString());
                UserInfo userInfo = new UserInfo();

                Toast.makeText(activityMain, "Вы успешно зарегистрировались", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activityMain, "некорректные данные", Toast.LENGTH_LONG).show();
            }


        }
    };

    @ViewById(R.id.loginRegistrationEdit)
    protected EditText loginRegistrationEditText;

    @ViewById(R.id.passwordRegistrationEdit)
    protected EditText passwordRegistrationEditText;


    @ViewById(R.id.emailAddressRegistrationEdit)
    protected EditText emailAddressRegistrationEditText;

    @ViewById(R.id.nameRegistrationEdit)
    protected EditText nameRegistrationEditText;

    @ViewById(R.id.telephoneFirstRegistrationEdit)
    protected EditText telephoneFirstRegistrationEditText;

    @ViewById(R.id.telephoneSecondRegistrationEdit)
    protected EditText telephoneSecondRegistrationEditText;

    @ViewById(R.id.confirmRegistrationButton)
    protected Button confRegistrationButton;

    @Click(R.id.confirmRegistrationButton)
    protected void clickConfirmButton() {

        passwordRegistrationString = passwordRegistrationEditText.getText().toString();
        loginRegistrationString = loginRegistrationEditText.getText().toString();
        emailAddressRegistrationString = emailAddressRegistrationEditText.getText().toString();
        nameRegistrationString = nameRegistrationEditText.getText().toString();
        telephoneFirstRegistrationString = telephoneFirstRegistrationEditText.getText().toString();
        telephoneSecondRegistrationString = telephoneSecondRegistrationEditText.getText().toString();

        if (emailAddressRegistrationString.length() > 0
                && nameRegistrationString.length() > 0
                && telephoneFirstRegistrationString.length() > 0
                && telephoneSecondRegistrationString.length() > 0
                && loginRegistrationString.length() > 0
                && passwordRegistrationString.length() > 0) {

            RequestEntities.Registrer registrer = new RequestEntities.Registrer();

            Log.d(LOG_TAG, loginRegistrationString + passwordRegistrationString
                    + emailAddressRegistrationString + nameRegistrationString
                    + telephoneFirstRegistrationString + telephoneSecondRegistrationString);

            registrer.setLogin(loginRegistrationString);
            registrer.setPassword(passwordRegistrationString);
            registrer.setEmail(emailAddressRegistrationString);
            registrer.setName(nameRegistrationString);
            registrer.setTelephoneFirst(telephoneFirstRegistrationString);
            registrer.setTelephoneSecond(telephoneSecondRegistrationString);

            ServerRequest.registrationClient(registrer, activityMain, onCompleteListener);

        }
    }




}
