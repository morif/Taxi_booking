package ch.crut.taxi.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.crut.taxi.R;


public class AdapterAutoComplete extends SimpleAdapter {

    private final String[] suggestions;
    private final Context context;

    public AdapterAutoComplete(Context context, String[] items) {
        super(context, new ArrayList<HashMap<String, String>>(), 0, new String[]{}, new int[]{R.id.adapterItemAutoCompleteTitle});
        this.suggestions = items;
        this.context = context;
    }


//    private final float _12dpSize;

//    public AdapterAutoComplete(Context context, String[] objects) {
//        super(context, 0, objects);
////        _12dpSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
//        _12dpSize = dpToPx(8);
//    }


    @Override
    public int getCount() {
        return suggestions.length;
    }

    @Override
    public String getItem(int position) {
        return suggestions[position];
    }

    private class ViewHolder {
        public TextView adapterItemAutoCompleteTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String string = getItem(position);

//        ViewHolder holder;
//
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.adapter_item_auto_complete, null);
//
//            holder = new ViewHolder();
//
//            holder.adapterItemAutoCompleteTitle = (TextView) convertView.findViewById(R.id.adapterItemAutoCompleteTitle);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.adapterItemAutoCompleteTitle.setText(string);
//
//        return convertView;


        TextView textView = new TextView(context);
        textView.setText(string);

        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

        textView.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "fonts/SegoePrint.ttf"));
//        textView.setTextSize(_12dpSize);
//        textView.setMaxLines(3);
//        textView.setLines(3);
        textView.setMaxLines(3);
        textView.setPadding(10, 10, 10, 10);
        return textView;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
