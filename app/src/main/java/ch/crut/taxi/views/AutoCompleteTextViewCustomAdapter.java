package ch.crut.taxi.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import ch.crut.taxi.querymaster.QueryMaster;


public class AutoCompleteTextViewCustomAdapter extends AutoCompleteTextView {

    public AutoCompleteTextViewCustomAdapter(Context context) {
        super(context);
    }

    public AutoCompleteTextViewCustomAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewCustomAdapter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
