package ch.crut.taxi.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ch.crut.taxi.R;


public class AdapterAutoComplete extends ArrayAdapter<String> {


//    private final float _12dpSize;

    public AdapterAutoComplete(Context context, String[] objects) {
        super(context, 0, objects);
//        _12dpSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
//        _12dpSize = dpToPx(8);
    }

    private class ViewHolder {
        public TextView adapterItemAutoCompleteTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String string = getItem(position);

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_item_auto_complete, null);

            holder = new ViewHolder();

            holder.adapterItemAutoCompleteTitle = (TextView) convertView.findViewById(R.id.adapterItemAutoCompleteTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.adapterItemAutoCompleteTitle.setText(string);

        return convertView;

//        TextView textView = new TextView(getContext());
//        textView.setText(string);
//        textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/SegoePrint.ttf"));
////        textView.setTextSize(_12dpSize);
//        textView.setPadding(10, 10, 10, 10);
//        return textView;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
