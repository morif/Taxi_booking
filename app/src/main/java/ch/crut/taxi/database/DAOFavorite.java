package ch.crut.taxi.database;


import android.content.Context;

import ch.crut.taxi.TaxiApplication;
import sqlitesimple.library.SQLiteSimpleDAO;
import sqlitesimple.library.annotations.Column;
import sqlitesimple.library.annotations.Table;

public class DAOFavorite extends SQLiteSimpleDAO<DAOFavorite.FavoriteAddress> {

    public static final Class<FavoriteAddress> tableClass = FavoriteAddress.class;

    private static final String TABLE = "Table_FavoriteAddress";

    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_USER_ID = "COLUMN_USER_ID";
    public static final String COLUMN_LATITUDE = "COLUMN_LATITUDE";
    public static final String COLUMN_LONGITUDE = "COLUMN_LONGITUDE";
    public static final String COLUMN_STREET = "COLUMN_STREET";
    public static final String COLUMN_CITY = "COLUMN_CITY";

    public DAOFavorite(Context context) {
        super(tableClass, context);
    }

    @Override
    public long create(FavoriteAddress object) {
        object.userID = TaxiApplication.getUserPrefs().id().get();
        return super.create(object);
    }

    @Table(name = TABLE)
    public static class FavoriteAddress {

        @Column(name = COLUMN_ID, isPrimaryKey = true, isAutoincrement = true)
        private String id;

        @Column(name = COLUMN_USER_ID)
        private String userID;

        @Column(name = COLUMN_LATITUDE)
        public float latitude;

        @Column(name = COLUMN_LONGITUDE)
        public float longitude;

        @Column(name = COLUMN_STREET)
        public String street;

        @Column(name = COLUMN_CITY)
        public String city;
    }
}
