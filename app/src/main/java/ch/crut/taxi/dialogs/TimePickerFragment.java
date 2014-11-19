package ch.crut.taxi.dialogs;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    private final Context context;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public TimePickerFragment(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.context = context;
        this.onTimeSetListener = onTimeSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(context, onTimeSetListener, calendar.get((Calendar.HOUR_OF_DAY)), calendar.get(Calendar.MINUTE),
                true);
    }
}
