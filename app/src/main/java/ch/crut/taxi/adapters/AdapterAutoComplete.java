package ch.crut.taxi.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class AdapterAutoComplete extends ArrayAdapter<String> {


    private final float _12dpSize;

    public AdapterAutoComplete(Context context, int resource, String[] objects) {
        super(context, resource, objects);
//        _12dpSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
        _12dpSize = dpToPx(8);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String string = getItem(position);
//        TypedValue.complexToDimensionPixelSize(TypedValue.COMPLEX_UNIT_SP, 12);
        TextView textView = new TextView(getContext());
        textView.setText(string);
        textView.setTextSize(_12dpSize);
        return textView;
//        return super.getView(position, convertView, parent);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
