package ch.crut.taxi.utils.request;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entities {

    public static class SearchTaxi implements Serializable {

        private static final long serialVersionUID = 42L;

        public String taximeter;
        public String bill;
        public String condition;
        public String lng;
        public String lat;
        public String tel1;
        public String tel2;
        public String tel3;
        public String id;
        public String wi_fi;
        public String carClass;
        public String plastic_card;
        public String color;
        public String name;
        public String lounge_for_smokers;
        public String yearAuto;
        public String modelCar;
        public String numberGos;
        public String status ="1";
        public String rating;
        public String driverPhoto;
        public String carPhoto;
        public String distance;
        public String count;
        public SearchTaxi(){}

        public SearchTaxi(JSONObject jsonObject) throws JSONException {
            this.id = jsonObject.getString("id");
            this.taximeter = jsonObject.getString("taximeter");
            this.bill = jsonObject.getString("bill");
            this.condition = jsonObject.getString("condition");
            this.lat = jsonObject.getString("lat");
            this.lng = jsonObject.getString("lng");
            this.tel1 = jsonObject.getString("tel1");
            this.tel2 = jsonObject.has("tel2") ? jsonObject.getString("tel2") : "";
            this.tel3 = jsonObject.has("tel3") ? jsonObject.getString("tel3") : "";
            this.wi_fi = jsonObject.getString("wi_fi");
            this.carClass = jsonObject.getString("carClass");
            this.plastic_card = jsonObject.getString("plastic_card");
            this.color = jsonObject.getString("color");
            this.name = jsonObject.getString("name");
            this.lounge_for_smokers = jsonObject.getString("lounge_for_smokers");
            this.yearAuto = jsonObject.getString("yearAuto");
            this.modelCar = jsonObject.getString("modelCar");
            this.numberGos = jsonObject.getString("numberGos");
            this.status = jsonObject.getString("status");
            this.rating = jsonObject.getString("rating");
            this.driverPhoto = jsonObject.getString("driverPhoto");
            this.carPhoto = jsonObject.getString("carPhoto");
            this.distance = jsonObject.getString("distance");
            this.count = jsonObject.getString("count");
        }

        public static List<SearchTaxi> get(JSONArray data) throws JSONException {
            if (data != null) {
                List<SearchTaxi> list = new ArrayList<>();
                int length = data.length();

                for (int i = 0; i < length; i++) {
                    SearchTaxi searchTaxi = new SearchTaxi(data.getJSONObject(i));
                    list.add(searchTaxi);
                }
                return list;
            }
            return null;
        }

        public LatLng getLatLng() {
            return new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        }

        public boolean freeDriver() {
            return status.equalsIgnoreCase("1");
        }
    }
}
