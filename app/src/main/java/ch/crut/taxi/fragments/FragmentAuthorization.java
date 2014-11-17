package ch.crut.taxi.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.interfaces.UserLocationPref_;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities.AuthorizationEntity;
import ch.crut.taxi.utils.TextUtils;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.request.ServerRequest;


@EFragment(R.layout.fragment_authorization)
@SmartFragment(title = "FragmentAuthorization", items = {NBItems.SETTING, NBItems.DONE})
public class FragmentAuthorization extends NBFragment implements NBItemSelector {


    public static FragmentAuthorization newInstance() {
        FragmentAuthorization fragmentAuthorization = new FragmentAuthorization_();
        Bundle bundle = new Bundle();
        fragmentAuthorization.setArguments(bundle);
        return fragmentAuthorization;
    }

    @ViewById(R.id.fragmentAuthorizationLogin)
    protected EditText ETlogin;

    @ViewById(R.id.fragmentAuthorizationPassword)
    protected EditText ETpassword;

    @Click(R.id.fragmentAuthorizationConfirm)
    protected void clickConfirmButton() {

        AuthorizationEntity entity = new AuthorizationEntity();
        entity.token = "1111";
        entity.password = TextUtils.get(ETpassword);
        entity.login = TextUtils.get(ETlogin);

        if (!TextUtils.emptyAnimate(ETlogin, ETpassword)) {
            ServerRequest.authorizationClient(entity,
                    ((ActivityMain) getActivity()), onCompleteListener);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentAuthorization.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

            JSONObject user = jsonObject.getJSONObject("user");

            UserLocationPref_ userLocationPref = TaxiApplication.getUserPrefs();

            userLocationPref.edit()
                    .email().put(user.getString("email"))
                    .phoneFirst().put(user.getString("tel1"))
                    .phoneSecond().put(user.has("tel2") ? user.getString("tel2") : "")
                    .id().put(user.getString("id"))
                    .login().put(user.getString("login"))
                    .name().put(user.getString("name")).apply();
        }
    };


    @Override
    public void NBItemSelected(int id) {
        QueryMaster.toast(getActivity(), FragmentAuthorization.this.toString() + ", " + String.valueOf(id));
    }

    public static interface OnAuthorizationComplete {

        public void complete();

        public void decline();
    }
}
