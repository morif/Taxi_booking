package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.interfaces.UserPref_;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities;
import ch.crut.taxi.utils.TextUtils;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.request.ServerRequest;


@SmartFragment(items = NBItems.CANCEL, title = R.string.registration)
@EFragment(R.layout.fragment_registration)
public class FragmentRegistration extends NBFragment implements NBItemSelector {

    private static final String LOG_TAG = "FragmentRegistration";
    private ActivityMain activityMain;


    public static FragmentRegistration newInstance() {
        FragmentRegistration fragmentRegistration = new FragmentRegistration_();
        Bundle bundle = new Bundle();
        fragmentRegistration.setArguments(bundle);
        return fragmentRegistration;
    }


    @ViewById(R.id.loginRegistrationEdit)
    protected EditText ETlogin;

    @ViewById(R.id.passwordRegistrationEdit)
    protected EditText ETpassword;

    @ViewById(R.id.emailAddressRegistrationEdit)
    protected EditText ETemail;

    @ViewById(R.id.nameRegistrationEdit)
    protected EditText ETname;

    @ViewById(R.id.telephoneFirstRegistrationEdit)
    protected EditText ETphone;

    @ViewById(R.id.telephoneSecondRegistrationEdit)
    protected EditText ETphoneSecond;


    @Click(R.id.confirmRegistrationButton)
    protected void clickConfirmButton() {
        confirm();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentRegistration.class);
        activityMain = (ActivityMain) activity;
    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.CANCEL:
                FragmentHelper.pop(getFragmentManager());
                break;
        }
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {
//            QueryMaster.alert(getActivity(), jsonObject.toString());
            if (QueryMaster.isSuccess(jsonObject)) {

                UserPref_ userLocationPref = TaxiApplication.getUserPrefs();

                userLocationPref.edit()
                        .login().put(TextUtils.get(ETlogin))
                        .password().put(TextUtils.get(ETpassword))
                        .apply();

                QueryMaster.toast(getActivity(), R.string.register_success);

                FragmentHelper.replace(getFragmentManager(),
                        FragmentAuthorization.newInstance(),
                        ((ViewGroup) getView().getParent()).getId());
            } else {
                QueryMaster.toast(getActivity(), R.string.uncorrect_data);
            }
        }
    };

    private void confirm() {
        if (!TextUtils.emptyAnimate(ETlogin, ETpassword, ETemail, ETname, ETphone, ETphoneSecond)) {

            String passwordRegistrationString = ETpassword.getText().toString();
            String loginRegistrationString = ETlogin.getText().toString();
            String emailAddressRegistrationString = ETemail.getText().toString();
            String nameRegistrationString = ETname.getText().toString();
            String telephoneFirstRegistrationString = ETphone.getText().toString();
            String telephoneSecondRegistrationString = ETphoneSecond.getText().toString();

            RequestEntities.RegisterEntity registrer = new RequestEntities.RegisterEntity();

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
