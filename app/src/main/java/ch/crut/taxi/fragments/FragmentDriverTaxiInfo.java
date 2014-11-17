package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapter.MyBaseAdapter;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.request.ServerRequest;
import ch.crut.taxi.utils.request.UserInfo;

@EFragment(R.layout.fragment_driver_taxi_info)
public class FragmentDriverTaxiInfo extends Fragment {
    private Activity activityMain;

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

        }
    };

    public static FragmentDriverTaxiInfo newInstance() {
        FragmentDriverTaxiInfo fragmentDriverTaxiInfo = new FragmentDriverTaxiInfo_();
        Bundle bundle = new Bundle();
        fragmentDriverTaxiInfo.setArguments(bundle);
        return fragmentDriverTaxiInfo;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = activity;


    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<String> numberList = new ArrayList<String>();
        numberList.add(0, "11111111");
        numberList.add(1, "222222");
        numberList.add(2, "12121212");

        for (int i = 0; i < numberList.size(); i++) {

            if (i == 0) {
                firstTextView.setText(numberList.get(0));

                firstLayout.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                secondTextView.setText(numberList.get(1));
                secondLinearLayout.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                threedTextView.setText(numberList.get(2));
                threeLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    @ViewById(R.id.photoDriverTaxi)
    protected ImageView photoDriverTaxiImage;

    @ViewById(R.id.nameDriverTaxi)
    protected TextView nameDriverText;

    @ViewById(R.id.confirmButton)
    protected Button confButton;

    @ViewById(R.id.firstTelephoneLinearLayout)
    protected LinearLayout firstLayout;
    @ViewById(R.id.secondTelephoneLinearLayout)
    protected LinearLayout secondLinearLayout;
    @ViewById(R.id.threedTelephoneLinearLayout)
    protected LinearLayout threeLayout;

    @ViewById(R.id.firstTelephoneText)
    protected TextView firstTextView;


    @ViewById(R.id.secondTelephoneText)
    protected TextView secondTextView;

    @ViewById(R.id.threedTelephoneText)
    protected TextView threedTextView;


    private void requestToServer() {
        ServerRequest.carDriverInfo((QueryMaster.OnErrorListener) activityMain, onCompleteListener);

    }
}
