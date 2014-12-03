package ch.crut.taxi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ch.crut.taxi.R;
import ch.crut.taxi.utils.UserOrderHistory;


public class UserOrderHistoryAdapter extends BaseAdapter implements View.OnClickListener {

    private static final String LOG_TAG = "UserOrderHistoryAdapter";
    private Context context;
    private ArrayList<UserOrderHistory> userOrderHistoriesList;
    private LayoutInflater inflater;

    public UserOrderHistoryAdapter(Context context, ArrayList<UserOrderHistory> userOrderHistoriesList) {

        this.context = context;
        this.userOrderHistoriesList = userOrderHistoriesList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userOrderHistoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return userOrderHistoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_user_order_history, null, false);
        }
        UserOrderHistory userOrderHistory = userOrderHistoriesList.get(position);

        TextView minutsTextView = (TextView) view.findViewById(R.id.adapterUserOrderHistoryMinuts);
        TextView fromStreetTextView = (TextView) view.findViewById(R.id.adapterUserOrderHistoryFromStreet);
        TextView toStreetTextView = (TextView) view.findViewById(R.id.adapterUserOrderHistoryToStreet);
        TextView distanceTextView = (TextView) view.findViewById(R.id.adapterUserOrderHistoryDistance);
        TextView priceTextView = (TextView) view.findViewById(R.id.adapterUserOrderPrice);
        minutsTextView.setText(userOrderHistory.getHowMinutsAgo());
        fromStreetTextView.setText(userOrderHistory.getFromStreet());
        toStreetTextView.setText(userOrderHistory.getToStreet());
        distanceTextView.setText(userOrderHistory.getDistance());
        priceTextView.setText(userOrderHistory.getPrice());

        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "click");

    }
}
