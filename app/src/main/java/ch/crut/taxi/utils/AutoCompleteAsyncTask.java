package ch.crut.taxi.utils;

import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

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
import java.util.List;

import ch.crut.taxi.fragments.FragmentDirectionAction;
import ch.crut.taxi.utils.google.map.LocationAddress;

public class AutoCompliteAsyncTask extends AsyncTask<String, Void, Object> {

    private static final String TAG = "AutoCompliteAsyncTask";
    private String inputStreetName = "";
    private String language = "fr";
    private int jsonArraySize;
    private String[] listStreetsArray = {""};
    private ProgressBar progressBarInAutoText;
    private FragmentDirectionAction fragmentDirectionAction;
    private Context context;
    private AutoCompleteTextView autoCompleteTextView;

    public AutoCompliteAsyncTask(Context context, FragmentDirectionAction fragmentDirectionAction,
                                 ProgressBar progressBarInAutoText, AutoCompleteTextView autoCompleteTextView) {
        this.context = context;
        this.fragmentDirectionAction = fragmentDirectionAction;
        this.progressBarInAutoText = progressBarInAutoText;
        this.autoCompleteTextView = autoCompleteTextView;
        inputStreetName = autoCompleteTextView.getText().toString();


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
            Log.d(TAG, json.toString());
            jObj = new JSONObject(json);

            is.close();
        } catch (JSONException e) {

            Log.e("log_tag", "Error parsing data " + e.toString());

        } catch (Exception e) {
            Log.e("log_tag", "Exception " + e.toString());
        }

        return jObj;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (!inputStreetName.equals("")) {
            progressBarInAutoText.setVisibility(View.VISIBLE);
            Log.d(TAG, "progresBar");

        }
        Log.d(TAG, "progresBar null");
    }

    @Override
    protected Object doInBackground(String... entity) {
        String desc = null;

        HttpClient httpClient = new DefaultHttpClient();
        Log.d(TAG, "2: " + inputStreetName);
        // HttpGet httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=" + adressStreet + "&sensor=false");
        HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/place/autocomplete/" +
                "json?input=" + inputStreetName + "&types=geocode&language=" + language + "&sensor=true&key=AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI");
        Log.e(TAG, "https://maps.googleapis.com/maps/api/place/autocomplete/" +
                "json?input=" + inputStreetName + "&types=geocode&language=" + language + "&sensor=true&key=AIzaSyDGBEAwvp7c6oLUxi4dGufZD-f3MToTAqI");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            JSONObject jObject = createJSONObject(response);
            JSONArray jsonArray = jObject.getJSONArray("predictions");
            jsonArraySize = jsonArray.length();
            listStreetsArray = new String[jsonArraySize];
            for (int i = 0; i < jsonArraySize; i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d(TAG, "1   " + jsonObject.toString());
                desc = jsonObject.getString("description");
                listStreetsArray[i] = desc;

            }
            autoCompleteTextView.setOnItemClickListener(new AutoCompliteListener());
            if (isCancelled()) return null;
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }


        return new Object();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressBarInAutoText.setVisibility(View.INVISIBLE);
        fragmentDirectionAction.searchStreet(listStreetsArray);
        Log.d(TAG, "11111 " + inputStreetName);

    }

    public class AutoCompliteListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String correctStreetName = autoCompleteTextView.getText().toString();
            double latitude = 0;
            double longitude = 0;
            try {

               Address address = LocationAddress.getFromLoсationName(context, correctStreetName).get(0);
                latitude = address.getLatitude();
                longitude = address.getLongitude();

            } catch (IOException e) {
                e.printStackTrace();
            }
            StreetInfo streetInfo = StreetInfo.getStreetInfo();
            streetInfo.setStreetName(correctStreetName);
            streetInfo.setLatitude(latitude);
            streetInfo.setLongitude(longitude);
            Log.d(TAG, "yesssssssssssssss "+ streetInfo.getLongitude());
        }
    }


}