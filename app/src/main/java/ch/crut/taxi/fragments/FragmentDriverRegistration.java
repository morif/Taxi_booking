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
@EFragment(R.layout.fragment_driver_registration)
public class FragmentDriverRegistration extends Fragment {

    private ActivityMain activityMain;
    public static final String LOG_TAG = "FragmentDriverRegistration";

    public static FragmentDriverRegistration newInstance() {
        FragmentDriverRegistration fragmentDriverRegistration = new FragmentDriverRegistration_();
        Bundle bundle = new Bundle();
        fragmentDriverRegistration.setArguments(bundle);
        return fragmentDriverRegistration;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    @ViewById(R.id.emailDriverRegistrationEdit)
    protected EditText emailDriverRegistrationEditText;

    @ViewById(R.id.passwordDriverRegistrationEdit)
    protected EditText passwordDriverRegistrationEditText;

    @ViewById(R.id.typeCarDriverRegistrationEdit)
    protected EditText typeCarDriverRegistrationEditText;

    @ViewById(R.id.brendAndMarkDriverRegistrationEdit)
    protected EditText brendAndMarkDriverRegistrationEditTExt;

    @ViewById(R.id.classCarDriverRegistrationEdit)
    protected EditText classCarDriverRegistrationEditText;

    @ViewById(R.id.telephoneFirstDriverRegistrationEdit)
    protected EditText telephoneFirstDriverRegistrationEditText;

    @ViewById(R.id.sonameDriverRegistrationEdit)
    protected EditText sonameDriverRegistrationEditText;

    @ViewById(R.id.townDriverRegistrationEdit)
    protected EditText townDriverRegistrationEditText;

    @ViewById(R.id.startDriverRegistrationButton)
    protected Button startDriverRegistrationButton;

    @Click(R.id.startDriverRegistrationButton)
    protected void clickDriverRegistration(){
        DriverInfo driverInfo = DriverInfo.getDriverInfo();
        ServerRequest.registrationDriver(driverInfo, activityMain, onCompleteListener);
        if(emailDriverRegistrationEditText.getText().length() > 0 &&
               passwordDriverRegistrationEditText.getText().length() > 0 &&
                typeCarDriverRegistrationEditText.getText().length() > 0 &&
                brendAndMarkDriverRegistrationEditTExt.getText().length() > 0 &&
                classCarDriverRegistrationEditText.getText().length() > 0 &&
                telephoneFirstDriverRegistrationEditText.getText().length() > 0 &&
                sonameDriverRegistrationEditText.getText().length() > 0 &&
                townDriverRegistrationEditText.getText().length() > 0){

            driverInfo.setEmailDriver(emailDriverRegistrationEditText.getText().toString());
            driverInfo.setPasswordDriver(passwordDriverRegistrationEditText.getText().toString());
            driverInfo.setTypeCarDriver(typeCarDriverRegistrationEditText.getText().toString());
            driverInfo.setMakeAndModelCarDriver(brendAndMarkDriverRegistrationEditTExt.getText().toString());
            driverInfo.setClassCar(classCarDriverRegistrationEditText.getText().toString());
            driverInfo.setFirstTelephone(telephoneFirstDriverRegistrationEditText.getText().toString());
            driverInfo.setSonameAndNameDriver(sonameDriverRegistrationEditText.getText().toString());
            driverInfo.setTownDriver(townDriverRegistrationEditText.getText().toString());

            Log.d(LOG_TAG, driverInfo.getClassCar());

            ServerRequest.registrationDriver(driverInfo, activityMain, onCompleteListener);


        }



    }



    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {


        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {


            Log.d(LOG_TAG, "json: " + jsonObject.toString());
         //   JSONObject jsonObject1 = jsonObject.getJSONObject("user");
          //  Log.d(LOG_TAG, "json: " + jsonObject1.toString());

        }
    };


}
