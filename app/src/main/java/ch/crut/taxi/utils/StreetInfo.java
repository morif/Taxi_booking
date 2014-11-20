package ch.crut.taxi.utils;

/**
 * Created by Alex on 19.11.2014.
 */
public class StreetInfo {
    private static StreetInfo streetInfo;
    private String streetName;
    private double longitude;
    private double latitude;
    private StreetInfo() {

    }

    public static synchronized StreetInfo getStreetInfo() {
        if (streetInfo == null) {
            streetInfo = new StreetInfo();
        }
        return streetInfo;
    }

    public String getStreetName() {
        return streetName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
