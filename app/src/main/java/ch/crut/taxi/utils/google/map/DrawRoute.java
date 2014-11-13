package ch.crut.taxi.utils.google.map;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DrawRoute {


    private static final String TAG = "DRAW_ROUTE";
    private static int DRAW_LINE_DELAY_NONE = 30;

    private Handler handlePoliline = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            DrawPolyline((ArrayList<LatLng>) msg.obj);
        }
    };
    private GoogleMap googleMap;

    public DrawRoute(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void draw(final LatLng origin, final LatLng destination) {
        DrawDirection(origin, destination, "driving");
    }

    public void DrawDirection(final LatLng origin, final LatLng destination, String mode) {

        final String ROUTES = "routes";
        final String STEPS = "steps";
        final String LEGS = "legs";
        final String POLYLINE = "polyline";

        final String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
                + origin.latitude
                + ","
                + origin.longitude
                + "&destination="
                + destination.latitude
                + ","
                + destination.longitude
                + "&sensor=true&mode=" + mode;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response = httpClient.execute(httpGet);

                    JSONObject serverResponseJson = createJSONObject(response);

                    JSONArray jRoutes = serverResponseJson.getJSONArray(ROUTES);

                    if (jRoutes.length() > 0) {

                        JSONObject jRoutesObj = jRoutes.getJSONObject(0);
                        JSONArray jLegsArr = jRoutesObj.getJSONArray(LEGS);
                        JSONObject jLegsObj = jLegsArr.getJSONObject(0);
                        JSONArray jStepsArr = jLegsObj.getJSONArray(STEPS);
                        final int length = jStepsArr.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject jOneStep = jStepsArr.getJSONObject(i);
                            JSONObject jPolylineObj = jOneStep
                                    .getJSONObject(POLYLINE);

                            try {
                                Thread.sleep(DRAW_LINE_DELAY_NONE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            decodePoly(jPolylineObj.getString("points"));
                        }
                    }
                } catch (IOException | JSONException e) {
                    Log.e(TAG, e.toString());
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();

        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        Message msg = new Message();
        msg.obj = poly;

        handlePoliline.sendMessage(msg);

        return poly;
    }

    public void DrawPolyline(ArrayList<LatLng> latLngList) {
        PolylineOptions options = new PolylineOptions().width(5)
                .color(Color.BLUE).geodesic(false);
        for (LatLng latLng : latLngList) {
            options.add(latLng);
        }
        googleMap.addPolyline(options);
    }

    public static JSONObject createJSONObject(HttpResponse httpResponse) {
        InputStream is = null;
        String json = "";
        JSONObject jObj = null;
        StringBuilder sb = null;

        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8);
                sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                // is.close();
            }
            json = sb.toString();

            jObj = new JSONObject(json);

            if (is != null) {
                is.close();
            }
        } catch (JSONException e) {

            Log.e("log_tag", "Error parsing data " + e.toString());

        } catch (Exception e) {
            Log.e("log_tag", "Exception " + e.toString());
        }

        return jObj;
    }
}
