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

import java.util.ArrayList;

import ch.crut.taxi.R;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.lazylist.ImageLoader;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.request.Entities;

@EFragment(R.layout.fragment_driver_taxi_info)
@SmartFragment(title = R.string.driver_info, items = {NBItems.BACK})
public class FragmentDriverTaxiInfo extends NBFragment implements NBItemSelector {


    private Activity activityMain;
    private final static String SEARCH_TAXI = "searchTaxi";
    private Entities.SearchTaxi driver;


    public static FragmentDriverTaxiInfo newInstance(Entities.SearchTaxi driver) {
        FragmentDriverTaxiInfo fragmentDriverTaxiInfo = new FragmentDriverTaxiInfo_();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_TAXI, driver);
        fragmentDriverTaxiInfo.setArguments(bundle);
        return fragmentDriverTaxiInfo;
    }

//    @ViewById(R.id.photoDriverTaxi)
//    protected ImageView photoDriverTaxiImage;
//
//
//    @ViewById(R.id.nameDriverTaxi)
//    protected TextView nameDriverText;
//
//
//    @ViewById(R.id.addServiceButton)
//    protected Button confButton;


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

    @ViewById(R.id.fragmentDriverPhoto)
    protected ImageView driverPhoto;


    @Click(R.id.fragmentDriverInfoComment)
    protected void clickComment() {

    }

//    @Click(R.id.addServiceButton)
//    protected void clickToButton() {
//        Log.d(LOG_TAG, "start now");
//
//
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentDriverTaxiInfo.class);
        activityMain = activity;
        driver = (Entities.SearchTaxi) getArguments().getSerializable(SEARCH_TAXI);

    }


    @Override
    public void onStart() {
        super.onStart();


        taxiDriverInfo(driver);


        ArrayList<String> numbers = new ArrayList<String>();
        if (!driver.tel1.isEmpty()) {
            numbers.add(driver.tel1);
        }
        if (!driver.tel2.isEmpty()) {
            numbers.add(driver.tel2);
        }
        if (!driver.tel3.isEmpty()) {
            numbers.add(driver.tel3);
        }


        LayerDrawable stars = (LayerDrawable) ratingBar_default.getProgressDrawable();

        ratingBar_default.setRating(Float.parseFloat(driver.rating));

        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        for (int i = 0; i < numbers.size(); i++) {
            if (i == 0) {
                firstTextView.setText(numbers.get(0));


                firstLayout.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                secondTextView.setText(numbers.get(1));
                secondLinearLayout.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                threedTextView.setText(numbers.get(2));
                threeLayout.setVisibility(View.VISIBLE);
            }
        }
    }


    private void taxiDriverInfo(Entities.SearchTaxi driver) {

        firstTextView.setText(driver.tel1);
        secondTextView.setText(driver.tel2);
        threedTextView.setText(driver.tel3);
        carBrendText.setText(driver.modelCar);
        colorAutoText.setText(driver.color);
        typeCarText.setText(driver.carClass);
        gosNumberCarText.setText(driver.numberGos);
        yearCarText.setText(driver.yearAuto);

        ImageLoader imageLoader = new ImageLoader(activityMain);

        if (!driver.carPhoto.isEmpty()) {
            imageLoader.DisplayImage(driver.carPhoto, firstPhotoCarImage);
        } else {
            firstPhotoCarImage.setVisibility(View.GONE);
        }
//        imageLoader.DisplayImage(driver.photo2, secondPhotoCarImage);

        if (!driver.driverPhoto.isEmpty()) {
            imageLoader.DisplayImage(driver.driverPhoto, driverPhoto);
        } else {
            driverPhoto.setImageResource(R.drawable.no_foto_taxist);
        }

    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.BACK:
                FragmentHelper.pop(getFragmentManager());
                break;
        }
    }
}
