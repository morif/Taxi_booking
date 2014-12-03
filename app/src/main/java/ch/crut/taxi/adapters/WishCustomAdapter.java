package ch.crut.taxi.adapters;

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


public class WishCustomAdapter extends BaseAdapter {
    private static final String LOG_TAG = "WishCustomAdapter";
    private LayoutInflater inflater;

    private ArrayList<WishObjects> wishObjectList;

    private static final ArrayList<WishObjects> globalItems;

    private final int AIR_CONDITIONING = 0;
    private final int ANIMALS_TRANSPORTATION = 1;
    private final int CHILD_SEAT = 2;
    private final int NOT_SMOKES = 3;
    private final int WIFI_ZONA = 4;
    private final int PAYMENT_CARD = 5;

    static {
        globalItems = new ArrayList<>();

        WishObjects airConditioningObj = new WishObjects(R.drawable.condition, R.string.air_conditioning);
        WishObjects animalTransportationObj = new WishObjects(R.drawable.animal, R.string.animals_transportation);
        WishObjects chieldSeatObj = new WishObjects(R.drawable.child, R.string.child_set);
        WishObjects notSmokesObj = new WishObjects(R.drawable.smocing, R.string.not_smokes);
        WishObjects wifiZonaObj = new WishObjects(R.drawable.wifi_zona, R.string.wifi_zona);
        WishObjects paymentCardObj = new WishObjects(R.drawable.card, R.string.payment_card);


        globalItems.add(wifiZonaObj);
        globalItems.add(notSmokesObj);
        globalItems.add(animalTransportationObj);
        globalItems.add(airConditioningObj);
        globalItems.add(chieldSeatObj);
        globalItems.add(paymentCardObj);
    }


    public WishCustomAdapter(Context context) {

        wishObjectList = new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        creatingWishObjects();
    }


    private void creatingWishObjects() {
        wishObjectList.addAll(globalItems);
    }


    @Override
    public int getCount() {
//        Log.d(LOG_TAG, "size " + wishObjectList.size());
        return wishObjectList.size();


    }


    @Override
    public Object getItem(int position) {
//        Log.d(LOG_TAG, "position " + position);
        return wishObjectList.get(position);
    }


    @Override
    public long getItemId(int position) {
//        Log.d(LOG_TAG, "pos " + position);
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ImageView wishImageView;
        TextView wishTextView;
        CheckBox checkBox;


        if (view == null) {

            view = inflater.inflate(R.layout.item_adapter_wish, null, false);


        }
        WishObjects wish = wishObjectList.get(position);
        wishImageView = (ImageView) view.findViewById(R.id.wishImageView);
        wishImageView.setImageResource(wish.getImageViewResurs());


        wishTextView = (TextView) view.findViewById(R.id.wishTextView);
        wishTextView.setHint(wish.getWishNameResurse());


        checkBox = (CheckBox) view.findViewById(R.id.idCheckBox);
        checkBox.setOnCheckedChangeListener(new CheckedListener(wish));
        checkBox.setChecked(wish.isAddWish());
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

    public ArrayList<WishObjects> checkedCheckInWishObjects() {

        ArrayList<WishObjects> wishObjectTrueList = new ArrayList<>();

        for (WishObjects aWishObjectsList : wishObjectList) {
            if (aWishObjectsList.isAddWish()) {
                wishObjectTrueList.add(aWishObjectsList);
            }
        }

        return wishObjectTrueList;
    }


}
