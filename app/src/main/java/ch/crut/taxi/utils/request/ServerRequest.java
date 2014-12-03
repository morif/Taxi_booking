package ch.crut.taxi.utils.request;


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

    public static void searchTaxi(LatLng origin, QueryMaster.OnErrorListener errorListener, QueryMaster.OnCompleteListener onCompleteListener) {
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

        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void authorizationClient(RequestEntities.AuthorizationEntity authorizationEntity,
                                           QueryMaster.OnErrorListener errorListener,
                                           QueryMaster.OnCompleteListener onCompleteListener) {
        final Map<Object, String> map = new HashMap<>();

        map.put("email", authorizationEntity.login);
        map.put("password", authorizationEntity.password);
        map.put("token", authorizationEntity.token);
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.AUTHORIZATION_CLIENT);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);

    }

    private static void sendQuery(QueryMaster.OnErrorListener errorListener, QueryMaster.OnCompleteListener onCompleteListener,
                                  MultipartEntity entity) {
        QueryMaster queryMaster = new QueryMaster(TaxiApplication
                .getRunningActivityContext(), SERVER, QueryMaster.QUERY_POST, entity);
        queryMaster.setProgressDialog();
        queryMaster.setOnErrorListener(errorListener);
        queryMaster.setOnCompleteListener(onCompleteListener);

        queryMaster.start();
    }

    private static MultipartEntity getEntity(Map<Object, String> map, String action) throws UnsupportedEncodingException, JSONException {
        final MultipartEntity multipartEntity = new MultipartEntity();

        JSONObject json = new JSONObject();

        JSONObject data = new JSONObject(map);

        json.put("data", data);
        json.put("action", action);


        multipartEntity.addPart("data",
                new StringBody(json.toString(), Charset.forName("UTF-8")));

        return multipartEntity;
    }

    public static void registrationClient(RequestEntities.RegisterEntity registerEntity, QueryMaster.OnErrorListener errorListener,
                                          QueryMaster.OnCompleteListener onCompleteListener) {

        final Map<Object, String> map = new HashMap<>();

        map.put("password", registerEntity.getPassword());
        map.put("email", registerEntity.getEmail());
        map.put("tel1", registerEntity.getTelephoneFirst());
        map.put("tel2", registerEntity.getTelephoneSecond());
        map.put("name", registerEntity.getName());

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
        public static final String AUTHORIZATION_CLIENT = "users.auth-client";
        public static final String REGISTRATION_CLIENT = "users.registration-client";
    }
}
