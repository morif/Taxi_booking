package ch.crut.taxi.utils;


import android.content.Context;
import android.util.Log;

import ch.crut.taxi.R;
import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.database.DAOFavorite;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.querymaster.QueryMaster;

public class FavoriteHelper {

    public static void add(Context context, NavigationPoint navigationPoint,
                           OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {

        DAOFavorite daoFavorite = TaxiApplication.getDaoFavorite();
        DAOFavorite.FavoriteAddress favoriteAddress = new DAOFavorite.FavoriteAddress();

        favoriteAddress.latitude = (float) navigationPoint.getLatLng().latitude;
        favoriteAddress.longitude = (float) navigationPoint.getLatLng().longitude;
        favoriteAddress.street = navigationPoint.getAddressString();
        favoriteAddress.original = (placeSelectedKey == OnPlaceSelectedListener.PlaceSelectedKeys.ORIGINAL);


        if (daoFavorite.createUnique(favoriteAddress) == -1) {
            QueryMaster.toast(context, R.string.already_favorite);
        } else {
            QueryMaster.toast(context, R.string.added_favorite);
        }
    }
}
