package ch.crut.taxi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


import ch.crut.taxi.R;
import ch.crut.taxi.utils.request.Entities;


public class ListCarsAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Entities.SearchTaxi> searchTaxiList;
    private ImageView imageCar;
    private TextView brendCar;
    private TextView distance;
    private LayoutInflater inflater;


    public ListCarsAdapter(Context context, ArrayList<Entities.SearchTaxi> searchTaxiList) {
        this.context = context;
        this.searchTaxiList = searchTaxiList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return searchTaxiList.size();
    }


    @Override
    public Object getItem(int position) {
        return searchTaxiList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if (view == null) {


            view = inflater.inflate(R.layout.adapter_list_cars, null, false);


        }


        imageCar = (ImageView) view.findViewById(R.id.fragmentListCarsImageCar);
        brendCar = (TextView) view.findViewById(R.id.fragmentListCarsBrendCar);
        distance = (TextView) view.findViewById(R.id.fragmentListCarsDistance);
        Entities.SearchTaxi searchTaxi = searchTaxiList.get(position);

        addResourse(searchTaxi);

        return view;
    }


    private void addResourse(Entities.SearchTaxi searchTaxi) {
        if (searchTaxi.freeDriver()) {


            imageCar.setImageResource(R.drawable.icon_taxi_free);
        } else {
            imageCar.setImageResource(R.drawable.icon_taxi_busy);
        }
        brendCar.setText(searchTaxi.modelCar);
        distance.setText(searchTaxi.distance);
    }
}