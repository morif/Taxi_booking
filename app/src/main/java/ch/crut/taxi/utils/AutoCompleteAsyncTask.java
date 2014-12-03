package ch.crut.taxi.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ch.crut.taxi.fragments.FragmentDirectionAction;


public class AutoCompleteAsyncTask extends AsyncTask<String, Void, Object> {

    private static final String TAG = "AutoCompleteAsyncTask";

    private String inputStreetName = "";

    private static final String LANGUAGE = "fr";

    private String[] listStreetsArray = {""};
    private ProgressBar progressBarInAutoText;
    private FragmentDirectionAction fragmentDirectionAction;


    public AutoCompleteAsyncTask(FragmentDirectionAction fragmentDirectionAction,
                                 ProgressBar progressBarInAutoText, String inputStringName) {
        this.fragmentDirectionAction = fragmentDirectionAction;
        this.progressBarInAutoText = progressBarInAutoText;
        this.inputStreetName = inputStringName;
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

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                json = sb.toString();
                jObj = new JSONObject(json);

                is.close();

                return jObj;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        if (!inputStreetName.equals("")) {
            progressBarInAutoText.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected Object doInBackground(String... entity) {


        HttpClient httpClient = new DefaultHttpClient();
//        Log.d(TAG, "2: " + inputStreetName);
        // HttpGet httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=" + adressStreet + "&sensor=false");


        try {

            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .authority("maps.googleapis.com")
                    .path("maps/api/place/autocomplete/json")
                    .appendQueryParameter("input", inputStreetName)
                    .appendQueryParameter("types", "geocode")
                    .appendQueryParameter("language", LANGUAGE)
                    .appendQueryParameter("sensor", "true")
                    .appendQueryParameter("key", "AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI")
                    .build();

//            String safeUrl = URLEncoder.encode("https://maps.googleapis.com/maps/api/place/autocomplete/json?input={" +
//                    inputStreetName + "}&types=geocode&language=" +
//                    LANGUAGE + "&sensor=true&key=AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI", "UTF-8");

            Log.e("", "uri.toString -> " + uri.toString());

            HttpGet httpGet = new HttpGet(uri.toString());

//            Log.e(TAG, "https://maps.googleapis.com/maps/api/place/autocomplete/" +
//                    "json?input=" + inputStreetName + "&types=geocode&language=" + LANGUAGE + "&sensor=true&key=AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI");

            HttpResponse response = httpClient.execute(httpGet);
            JSONObject jObject = createJSONObject(response);
            JSONArray jsonArray = jObject.getJSONArray("predictions");
            int jsonArraySize = jsonArray.length();
//            Log.d(TAG, jsonArray.toString());
//            Log.d(TAG, "-------------------------------------------------" + jsonArray.length());
            listStreetsArray = new String[jsonArraySize];

            for (int i = 0; i < jsonArraySize; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray terms = jsonObject.getJSONArray("terms");

                JSONObject street = terms.length() >= 1 ? terms.getJSONObject(0) : null;
                JSONObject city = terms.length() >= 2 ? terms.getJSONObject(1) : null;

                String streetAndCity = "";
                if (street != null && city != null) {
                    streetAndCity = street.getString("value") + ", " + city.getString("value");
                } else if (street != null) {
                    streetAndCity = street.getString("value");
                }

                listStreetsArray[i] = streetAndCity;

            }
            if (isCancelled()) return null;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        return null;
    }

//    https://maps.googleapis.com/maps/api/place/autocomplete/json?input=RTs%20"Blokbaster",%20Kiev&types=geocode&language=fr&sensor=true&key=AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (!isCancelled()) {
            progressBarInAutoText.setVisibility(View.INVISIBLE);
            fragmentDirectionAction.searchStreet(listStreetsArray);
        }
    }
}