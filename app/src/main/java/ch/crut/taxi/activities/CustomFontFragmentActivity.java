package ch.crut.taxi.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ch.crut.taxi.utils.FontUtils;


public class CustomFontFragmentActivity extends FragmentActivity {
    // we can't setLayout Factory as its already set by FragmentActivity so we
// use this approach


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(name, context, attrs);
        if (v == null) {
            v = tryInflate(name, context, attrs);
            if (v instanceof TextView) {
                setTypeFace((TextView) v);
            }
        }
        return v;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(View parent, String name, Context context,
                             AttributeSet attrs) {
        View v = super.onCreateView(parent, name, context, attrs);
        if (v == null) {
            v = tryInflate(name, context, attrs);
            if (v instanceof TextView) {
                setTypeFace((TextView) v);
            }
        }
        return v;
    }

    private View tryInflate(String name, Context context, AttributeSet attrs) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = null;
        try {
            v = li.createView(name, null, attrs);
        } catch (Exception e) {
            try {
                v = li.createView("android.widget." + name, null, attrs);
            } catch (Exception igrored) {
            }
        }
        return v;
    }

    private void setTypeFace(TextView tv) {
        tv.setTypeface(FontUtils.getFonts(this, "fonts/SegoePrint.ttf"));
    }
}
