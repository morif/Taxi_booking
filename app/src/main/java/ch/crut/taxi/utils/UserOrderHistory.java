package ch.crut.taxi.utils;


public class UserOrderHistory {

    private String howMinutsAgo;
    private String fromStreet;
    private String toStreet;
    private String distance;
    private String price;

    public UserOrderHistory(String howMinutsAgo, String fromStreet, String toStreet, String distance, String price){
        this.howMinutsAgo = howMinutsAgo;
        this.fromStreet = fromStreet;
        this.toStreet = toStreet;
        this.distance = distance;
        this.price = price;
    }


    public String getHowMinutsAgo() {
        return howMinutsAgo;
    }

    public void setHowMinutsAgo(String howMinutsAgo) {
        this.howMinutsAgo = howMinutsAgo;
    }

    public String getFromStreet() {
        return fromStreet;
    }

    public void setFromStreet(String fromStreet) {
        this.fromStreet = fromStreet;
    }

    public String getToStreet() {
        return toStreet;
    }

    public void setToStreet(String toStreet) {
        this.toStreet = toStreet;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
