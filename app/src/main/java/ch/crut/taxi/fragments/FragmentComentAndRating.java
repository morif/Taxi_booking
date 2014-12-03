package ch.crut.taxi.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.querymaster.QueryMaster;


@EFragment(R.layout.fragment_coment_and_rating)
public class FragmentComentAndRating extends Fragment {
    private ActivityMain activityMain;
    private String LOG_TAG = "FragmentComentAndRating";
    private float ratingSars;

    public static FragmentComentAndRating newInstance() {
        FragmentComentAndRating fragmentComentAndRating = new FragmentComentAndRating_();
        Bundle bundle = new Bundle();
        fragmentComentAndRating.setArguments(bundle);
        return fragmentComentAndRating;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    @ViewById(R.id.fragmentComentRatingbarChanges)
    protected RatingBar ratingBar;

    @ViewById(R.id.fragmentComentAndRatingComitFromUser)
    protected TextView comitFromUser;

    @Override
    public void onStart() {
        super.onStart();
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratingSars = rating;
                Log.d(LOG_TAG, "rating " + rating);
//                UserInfo userInfo = UserInfo.getUserInfo();

//                ServerRequest.changeRatingDriver(ratingSars, "idDriver", userInfo.getId(), "", activityMain, onCompleteListener);
            }
        });
    }

    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {
        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {


            Log.d(LOG_TAG, "json: " + jsonObject.toString());

            //  JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            //  Log.d(LOG_TAG, "json: " + jsonObject1.toString());
            // QueryMaster.alert(getActivity(), userInfo.getId());

        }
    };

}
