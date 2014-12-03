package ch.crut.taxi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.crut.taxi.R;
import ch.crut.taxi.utils.WishObjects;

/**
 * Created by Alex on 18.11.2014.
 */
public class WishCustomAdapter extends BaseAdapter {
    private static final String LOG_TAG = "WishCustomAdapter";
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<WishObjects> wishObjectList;
    private final int AIR_CONDITIONING = 0;
    private final int ANIMALS_TRANSPORTATION = 1;
    private final int CHILD_SEAT = 2;
    private final int NOT_SMOKES = 3;
    private final int WIFI_ZONA = 4;
    private final int PAYMENT_CARD = 5;

    public WishCustomAdapter(Context context) {
        this.context = context;
        wishObjectList = new ArrayList<WishObjects>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        creatingWishObjects();
    }

    private void creatingWishObjects() {
        WishObjects airConditioningObj = new WishObjects(R.drawable.condition, R.string.air_conditioning);
        WishObjects animalTransportationObj = new WishObjects(R.drawable.animal, R.string.animals_transportation);
        WishObjects chieldSeatObj = new WishObjects(R.drawable.child, R.string.child_set);
        WishObjects notSmokesObj = new WishObjects(R.drawable.not_smokes, R.string.not_smokes);
        WishObjects wifiZonaObj = new WishObjects(R.drawable.wifi_zona, R.string.wifi_zona);
        WishObjects paymentCardObj = new WishObjects(R.drawable.card, R.string.payment_card);

        wishObjectList.add(wifiZonaObj);
        wishObjectList.add(notSmokesObj);
        wishObjectList.add(animalTransportationObj);
        wishObjectList.add(airConditioningObj);
        wishObjectList.add(chieldSeatObj);
        wishObjectList.add(paymentCardObj);

    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "size " + wishObjectList.size());
        return wishObjectList.size();

    }

    @Override
    public Object getItem(int position) {
        Log.d(LOG_TAG, "position " + position);
        return wishObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(LOG_TAG, "pos " + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ImageView wishImageView;
        TextView wishTextView;
        CheckBox checkBox;

        if (view == null) {

            view = inflater.inflate(R.layout.adapter_wish, null, false);

        }
        WishObjects wishObjects = wishObjectList.get(position);
        wishImageView = (ImageView) view.findViewById(R.id.wishImageView);
        wishImageView.setImageResource(wishObjects.getImageViewResurs());

        wishTextView = (TextView) view.findViewById(R.id.wishTextView);
        wishTextView.setHint(wishObjects.getWishNameResurse());

        checkBox = (CheckBox) view.findViewById(R.id.idCheckBox);
        checkBox.setOnCheckedChangeListener(new CheckedListener(wishObjects));
        return view;
    }

    private class CheckedListener implements CompoundButton.OnCheckedChangeListener {

        private WishObjects wishObjects;

        public CheckedListener(WishObjects wishObjects) {
            this.wishObjects = wishObjects;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            wishObjects.setAddWish(isChecked);

        }
    }
    private ArrayList<WishObjects> checkedCheckInWishObjects(ArrayList<WishObjects> wishObjectsList){
        int wishObjectsListSize = wishObjectsList.size();
        ArrayList<WishObjects> wishObjectTrueList = new ArrayList<WishObjects>();
        for(int i = 0; i < wishObjectsListSize; i ++){
            if(wishObjectsList.get(i).isAddWish()){
                wishObjectTrueList.add(wishObjectsList.get(i));
            }

        }

        return wishObjectTrueList;
    }


}
