package ch.crut.taxi.utils.request;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import ch.crut.taxi.TaxiApplication;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.RequestEntities;

public class ServerRequest {
    public static final String SERVER = "http://beestore.com.ua/taxi/api/main.php";
    private static final String LOG_TAG = "ServerRequest";


//    data={"action":"users.order-taxi","data"{"latStart":"","lngStart":"","radius":"5"}}

    public static void searchTaxi(LatLng origin, QueryMaster.OnErrorListener errorListener,
                                  QueryMaster.OnCompleteListener onCompleteListener) {
        final Map<Object, String> map = new HashMap<>();
        map.put("latStart", String.valueOf(origin.latitude));
        map.put("lngStart", String.valueOf(origin.longitude));
        map.put("radius", "5");

        MultipartEntity entity;

        try {
            entity = getEntity(map, TaxiActions.SEARCH_TAXI);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QueryMaster queryMaster = new QueryMaster(TaxiApplication
                .getRunningActivityContext(), SERVER, QueryMaster.QUERY_POST, entity);
        queryMaster.setProgressDialog();
        queryMaster.setOnErrorListener(errorListener);
        queryMaster.setOnCompleteListener(onCompleteListener);

        queryMaster.start();
    }
public static void authoritationClient(RequestEntities.Registrer registrer, QueryMaster.OnErrorListener errorListener,
                                       QueryMaster.OnCompleteListener onCompleteListener){
    final Map<Object, String> map = new HashMap<>();
    map.put("login", registrer.getLogin());
    map.put("password", registrer.getPassword());
    map.put("token", "1111111111111111111111111");
    MultipartEntity entity;
    try {
        entity = getEntity(map, TaxiActions.AUTORITATION_CLIENT);
    } catch (UnsupportedEncodingException | JSONException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }

    sendQuery(errorListener, onCompleteListener, entity);

}
    public static void sendQuery(QueryMaster.OnErrorListener errorListener, QueryMaster.OnCompleteListener onCompleteListener,
                                 MultipartEntity entity){
        QueryMaster queryMaster = new QueryMaster(TaxiApplication
                .getRunningActivityContext(), SERVER, QueryMaster.QUERY_POST, entity);
        queryMaster.setProgressDialog();
        queryMaster.setOnErrorListener(errorListener);
        queryMaster.setOnCompleteListener(onCompleteListener);

        queryMaster.start();
    }

    public static MultipartEntity getEntity(Map<Object, String> map, String action) throws UnsupportedEncodingException, JSONException {
        final MultipartEntity multipartEntity = new MultipartEntity();

        JSONObject json = new JSONObject();

        JSONObject data = new JSONObject(map);

        json.put("data", data);
        json.put("action", action);

        multipartEntity.addPart("data",
                new StringBody(json.toString(), Charset.forName("UTF-8")));

        return multipartEntity;
    }

    public static void registrationClient(RequestEntities.Registrer registrer, QueryMaster.OnErrorListener errorListener,
            QueryMaster.OnCompleteListener onCompleteListener){

        final Map<Object, String> map = new HashMap<>();
        map.put("login", registrer.getLogin());
        map.put("password", registrer.getPassword());
        map.put("email", registrer.getEmail());
        map.put("tel1", registrer.getTelephoneFirst());
        map.put("tel2", registrer.getTelephoneSecond());
        map.put("name", registrer.getName());
        Log.d(LOG_TAG, registrer.getLogin()
                +registrer.getPassword()
                +registrer.getEmail()
                +registrer.getTelephoneFirst()
                +registrer.getTelephoneSecond()
                +registrer.getName());
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.REGISTRATION_CLIENT);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);

    }


    private static class TaxiActions {
        public static final String SEARCH_TAXI = "users.order-taxi";
        public static final String AUTORITATION_CLIENT = "users.auth-client";
        public static final String REGISTRATION_CLIENT = "users.registration-client";
    }
}
