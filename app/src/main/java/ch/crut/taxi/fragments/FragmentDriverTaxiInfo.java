package ch.crut.taxi.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;

import java.util.ArrayList;

import ch.crut.taxi.R;
import ch.crut.taxi.lazylist.ImageLoader;
import ch.crut.taxi.utils.RequestEntities;
import ch.crut.taxi.utils.request.Entities;

@EFragment(R.layout.fragment_driver_taxi_info)
public class FragmentDriverTaxiInfo extends Fragment {
    private Activity activityMain;
    private final static String SEARCH_TAXI = "searchTaxi";
    private final String LOG_TAG = "FragmentDriverTaxiInfo";
    private Entities.SearchTaxi driver;

    public static FragmentDriverTaxiInfo newInstance(Entities.SearchTaxi driver) {
        FragmentDriverTaxiInfo fragmentDriverTaxiInfo = new FragmentDriverTaxiInfo_();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_TAXI, driver);
        fragmentDriverTaxiInfo.setArguments(bundle);
        return fragmentDriverTaxiInfo;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = activity;
        driver = (Entities.SearchTaxi) getArguments().getSerializable(SEARCH_TAXI);

    }


    @Override
    public void onStart() {
        super.onStart();


        taxiDriverInfo(driver);

        ArrayList<String> numberList = new ArrayList<String>();
        numberList.add(0, "11111111");
        numberList.add(1, "222222");
        numberList.add(2, "12121212");

        LayerDrawable stars = (LayerDrawable) ratingBar_default.getProgressDrawable();
        float a = new Float(3.5);
        ratingBar_default.setRating(a);
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


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

    @ViewById(R.id.addServiceButton)
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

    @ViewById(R.id.ratingbar_default)
    protected RatingBar ratingBar_default;

    @ViewById(R.id.carBrendTextView)
    protected TextView carBrendText;

    @ViewById(R.id.colorAutoTextView)
    protected TextView colorAutoText;

    @ViewById(R.id.typeCarTextView)
    protected TextView typeCarText;

    @ViewById(R.id.gosNumberCarTextView)
    protected TextView gosNumberCarText;

    @ViewById(R.id.yearCarTextView)
    protected TextView yearCarText;

    @ViewById(R.id.firstPhotoCar)
    protected ImageView firstPhotoCarImage;

    @ViewById(R.id.secondPhotoCar)
    protected ImageView secondPhotoCarImage;

    @Click(R.id.addServiceButton)
    protected void clickToButton() {
        Log.d(LOG_TAG, "start now");

    }


    private void taxiDriverInfo(Entities.SearchTaxi driver) {

        nameDriverText.setText(driver.name);
        firstTextView.setText(driver.tel1);
        secondTextView.setText(driver.tel2);
        threedTextView.setText(driver.tel3);
        carBrendText.setText(driver.modelCar);
        colorAutoText.setText(driver.color);
        typeCarText.setText(driver.carClass);
        gosNumberCarText.setText(driver.numberGos);
        yearCarText.setText(driver.yearAuto);
        ImageLoader imageLoader = new ImageLoader(activityMain);
    //    imageLoader.DisplayImage(driver.photo1, firstPhotoCarImage);
     //   imageLoader.DisplayImage(driver.photo2, secondPhotoCarImage);

    }


}
