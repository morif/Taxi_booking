package ch.crut.taxi.utils;


import android.util.Log;

import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.database.DAOFavorite;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;

public class FavoriteHelper {

    public static void add(
            NavigationPoint navigationPoint,
            OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {

        DAOFavorite daoFavorite = TaxiApplication.getDaoFavorite();
        DAOFavorite.FavoriteAddress favoriteAddress = new DAOFavorite.FavoriteAddress();

        favoriteAddress.latitude = (float) navigationPoint.latLng.latitude;
        favoriteAddress.longitude = (float) navigationPoint.latLng.longitude;
        favoriteAddress.street = navigationPoint.addressString;

        daoFavorite.create(favoriteAddress);

        Log.e("", "daoFavorite count -> " + daoFavorite.getCount());
    }

}
