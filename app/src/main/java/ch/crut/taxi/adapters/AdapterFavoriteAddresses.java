package ch.crut.taxi.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.database.DAOFavorite;

public class AdapterFavoriteAddresses extends ArrayAdapter {

//    private final DAOFavorite daoFavorite;

    private static final int RESOURCE_ID = R.layout.item_favorite_address;
    private final Context context;

    private ArrayList<DAOFavorite.FavoriteAddress> displayedItems;

    public AdapterFavoriteAddresses(Context context) {
        super(context, 0);

        this.context = context;

        DAOFavorite daoFavorite = TaxiApplication.getDaoFavorite();
        this.displayedItems = (ArrayList<DAOFavorite.FavoriteAddress>) daoFavorite.readAllAsc();
    }

    private class ViewHolder {
        public TextView addressName;
    }

    public void remove(DAOFavorite.FavoriteAddress object) {
        TaxiApplication.getDaoFavorite().deleteWhere(DAOFavorite.COLUMN_ID,
                String.valueOf(object.getID()));
        displayedItems.remove(object);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return displayedItems.size();
    }

    @Override
    public DAOFavorite.FavoriteAddress getItem(int position) {
        return displayedItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DAOFavorite.FavoriteAddress favoriteAddress = getItem(position);

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(RESOURCE_ID, null);

            holder.addressName = (TextView) convertView.findViewById(R.id.itemFavoriteAddressName);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.addressName.setText(favoriteAddress.street);


//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickListener != null) {
//                    onClickListener.onClick(favoriteAddress);
//                }
//            }
//        });

        return convertView;
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public static interface OnClickListener {
        public void onClick(DAOFavorite.FavoriteAddress favoriteAddress);
    }
}

