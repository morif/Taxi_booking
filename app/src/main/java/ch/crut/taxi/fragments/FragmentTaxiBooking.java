package ch.crut.taxi.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.dialogs.TimePickerFragment;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.TaxiBookingHelper;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;

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
            ((ActivityMain) getActivity()).add(FragmentTaxiSearch.newInstance());
        } else {
            ((ActivityMain) getActivity()).add(FragmentAuthorization.newInstance());
        }
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

        if (originalPoint != null) {
            originAddress.setText(originalPoint.getAddressString());
        }

        if (destinationPoint != null) {
            destinationAddress.setText(destinationPoint.getAddressString());
        }

        orderTime.setText(getOrderTime());
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
                QueryMaster.toast(getActivity(), "settings");
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
}


