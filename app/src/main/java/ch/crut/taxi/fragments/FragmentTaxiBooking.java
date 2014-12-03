package ch.crut.taxi.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import actionbar.NBFragment;
import actionbar.SmartFragment;
import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.dialogs.TimePickerFragment;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
//import actionbar.SmartFragment;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.TaxiBookingHelper;
import ch.crut.taxi.utils.WishObjects;
import actionbar.NBItemSelector;
import actionbar.NBItems;

@SmartFragment(title = R.string.taxi_booking, items = {NBItems.SETTING})
@EFragment(R.layout.fragment_taxi_booking)
public class FragmentTaxiBooking extends NBFragment implements NBItemSelector {

//    private NBController barController;

    private NavigationPoint originalPoint;
    private NavigationPoint destinationPoint;

    public static FragmentTaxiBooking newInstance() {
        FragmentTaxiBooking fragmentTaxiBooking = new FragmentTaxiBooking_();
        Bundle bundle = new Bundle();
        fragmentTaxiBooking.setArguments(bundle);
        return fragmentTaxiBooking;
    }

    @ViewById(R.id.fragmentTaxiBookingOrigin)
    protected EditText originAddress;

    @ViewById(R.id.fragmentTaxiBookingDestination)
    protected EditText destinationAddress;

    @ViewById(R.id.fragmentTaxiBookingOrderTime)
    protected TextView orderTime;

    @ViewById(R.id.animalImage)
    protected ImageView animalImageView;

    @ViewById(R.id.notSmokesImage)
    protected ImageView notSmokesImageView;

    @ViewById(R.id.wifiImage)
    protected ImageView wifiImageView;

    @ViewById(R.id.airConditionImage)
    protected ImageView airConditionImageView;

    @ViewById(R.id.chieldImage)
    protected ImageView chieldImageView;

    @ViewById(R.id.paymentImage)
    protected ImageView paymentImageView;


    @Click(R.id.fragmentTaxiBookingOriginButton)
    protected void clickOriginal() {
        final OnPlaceSelectedListener.PlaceSelectedKeys originalKey = OnPlaceSelectedListener.PlaceSelectedKeys.ORIGINAL;
        ((ActivityMain) getActivity()).add(FragmentDirectionAction.newInstance(originalKey));
    }

    @Click(R.id.fragmentTaxiBookingDestinationButton)
    protected void clickDestination() {
        final OnPlaceSelectedListener.PlaceSelectedKeys destinationKey = OnPlaceSelectedListener.PlaceSelectedKeys.DESTINATION;
        ((ActivityMain) getActivity()).add(FragmentDirectionAction.newInstance(destinationKey));
    }

    @Click(R.id.fragmentTaxiBookingSearchAuto)
    protected void clickSearchAuto() {


        if (TaxiApplication.isUserAuthorized()) {
            if (dataEntered()) {
                ((ActivityMain) getActivity()).add(FragmentTaxiSearch.newInstance());
            } else {
                QueryMaster.toast(getActivity(), R.string.enter_all_data);
            }
        } else {
            ((ActivityMain) getActivity()).add(FragmentAuthorization.newInstance());
        }
    }

    @Click(R.id.fragmentTaxiBookingAddWishes)
    protected void clickWishes() {
        ((ActivityMain) getActivity()).add(FragmentWish.newInstance());
    }

    private boolean dataEntered() {
        return !((ActivityMain) getActivity()).getTaxiBookingHelper().isEmpty();
    }

    @Click(R.id.fragmentTaxiBookingOrderTime)
    protected void clickOrderTime() {
        new TimePickerFragment(getActivity(), onOrderTimePicked).show(getFragmentManager(), "");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentTaxiBooking.class);
    }

    @Override
    public void onStart() {
        super.onStart();
//        barController = ((ActivityMain) getActivity()).getNBController();
//        barController.title(getString(R.string.taxi_booking));
//        barController.settingEnabled(true);


        TaxiBookingHelper bookingHelper = ((ActivityMain) getActivity()).getTaxiBookingHelper();

        originalPoint = bookingHelper.original;
        destinationPoint = bookingHelper.destination;


        if (!originalPoint.isEmpty()) {
            originAddress.setText(originalPoint.getAddressString());
        }

        if (!destinationPoint.isEmpty()) {
            destinationAddress.setText(destinationPoint.getAddressString());
        }

        orderTime.setText(getOrderTime());

        creatingWish(((ActivityMain) getActivity()).getUserWishes());
    }

    @Override
    public void onPause() {
        super.onPause();
//        barController.settingEnabled(false);
    }

    public void setFrom(String fromAddress) {
        this.originAddress.setText(fromAddress);
    }


    public static String millisecToDate(long milliseconds) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(milliseconds);
        return formatDate.format(date);
    }

    public static String getOrderTime() {
        return millisecToDate(new Date().getTime() + (15 * 60000));
    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.SETTING:
                ((ActivityMain) getActivity()).add(FragmentSettings.newInstance());
                break;
        }
    }

    private TimePickerDialog.OnTimeSetListener onOrderTimePicked = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String time = "";
            if (hourOfDay < 10)
                time += "0" + hourOfDay;
            else
                time += hourOfDay;
            time += ":";
            if (minute < 10)
                time += "0" + minute;
            else
                time += minute;

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

            orderTime.setText(formatDate.format(new Date()) + " " + time);
        }
    };

    public void creatingWish(ArrayList<WishObjects> wishObjectsArrayList) {
        if (wishObjectsArrayList != null) {

            int wishObjectSize = wishObjectsArrayList.size();

            switch (wishObjectSize) {
                case 1:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    notSmokesImageView.setImageResource(wishObjectsArrayList.get(1).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    notSmokesImageView.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    notSmokesImageView.setImageResource(wishObjectsArrayList.get(1).getImageViewResurs());
                    wifiImageView.setImageResource(wishObjectsArrayList.get(2).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    notSmokesImageView.setVisibility(View.VISIBLE);
                    wifiImageView.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    notSmokesImageView.setImageResource(wishObjectsArrayList.get(1).getImageViewResurs());
                    wifiImageView.setImageResource(wishObjectsArrayList.get(2).getImageViewResurs());
                    airConditionImageView.setImageResource(wishObjectsArrayList.get(3).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    notSmokesImageView.setVisibility(View.VISIBLE);
                    wifiImageView.setVisibility(View.VISIBLE);
                    airConditionImageView.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    notSmokesImageView.setImageResource(wishObjectsArrayList.get(1).getImageViewResurs());
                    wifiImageView.setImageResource(wishObjectsArrayList.get(2).getImageViewResurs());
                    airConditionImageView.setImageResource(wishObjectsArrayList.get(3).getImageViewResurs());
                    chieldImageView.setImageResource(wishObjectsArrayList.get(4).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    notSmokesImageView.setVisibility(View.VISIBLE);
                    wifiImageView.setVisibility(View.VISIBLE);
                    airConditionImageView.setVisibility(View.VISIBLE);
                    chieldImageView.setVisibility(View.VISIBLE);


                    break;
                case 6:
                    animalImageView.setImageResource(wishObjectsArrayList.get(0).getImageViewResurs());
                    notSmokesImageView.setImageResource(wishObjectsArrayList.get(1).getImageViewResurs());
                    wifiImageView.setImageResource(wishObjectsArrayList.get(2).getImageViewResurs());
                    airConditionImageView.setImageResource(wishObjectsArrayList.get(3).getImageViewResurs());
                    chieldImageView.setImageResource(wishObjectsArrayList.get(4).getImageViewResurs());
                    paymentImageView.setImageResource(wishObjectsArrayList.get(5).getImageViewResurs());
                    animalImageView.setVisibility(View.VISIBLE);
                    notSmokesImageView.setVisibility(View.VISIBLE);
                    wifiImageView.setVisibility(View.VISIBLE);
                    airConditionImageView.setVisibility(View.VISIBLE);
                    chieldImageView.setVisibility(View.VISIBLE);
                    paymentImageView.setVisibility(View.VISIBLE);


                    break;
            }
        }
    }
}


