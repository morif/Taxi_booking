package ch.crut.taxi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ch.crut.taxi.R;

/**
 * Created by Alex on 17.11.2014.
 */
public class MyBaseAdapter extends BaseAdapter {
    private static final String LOG_TAG = "MyBaseAdapter";
    private LayoutInflater inflater;
    private Context context;

private ArrayList<String> telephoneNumbersList;
    public MyBaseAdapter(Context context, ArrayList<String> telephoneNumbersList){
        this.context = context;
        this.telephoneNumbersList = telephoneNumbersList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
   Log.d(LOG_TAG, "size: " + telephoneNumbersList.size());
        return telephoneNumbersList.size();
    }
    @Override
    public Object getItem(int position) {
        return telephoneNumbersList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Log.d(LOG_TAG, ""+ position);
        TextView textView;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_view, null, false);
        }
        textView =(TextView) view.findViewById(R.id.numberTaxiDriver);

        Log.d(LOG_TAG, telephoneNumbersList.get(position));

        textView.setText(telephoneNumbersList.get(position));
        return view;
    }
}
