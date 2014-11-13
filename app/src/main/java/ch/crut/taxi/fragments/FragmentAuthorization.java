package ch.crut.taxi.fragments;


import android.support.v4.app.Fragment;
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
import ch.crut.taxi.interfaces.UserLocationPref_;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities.AuthorizationEntity;
import ch.crut.taxi.utils.TextUtils;
import ch.crut.taxi.utils.request.ServerRequest;

@EFragment(R.layout.fragment_authorization)
public class FragmentAuthorization extends Fragment {

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

    public static interface OnAuthorizationComplete {

        public void complete();

        public void decline();
    }
}
