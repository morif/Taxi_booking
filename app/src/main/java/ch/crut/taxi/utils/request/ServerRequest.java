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
                                           QueryMaster.OnCompleteListener onCompleteListener) {
        final Map<Object, String> map = new HashMap<>();
        map.put("login", registrer.getLogin());
        map.put("password", registrer.getPassword());
        map.put("token", "1111111111111111111111111");
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.AUTHORITATION_CLIENT);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void sendQuery(QueryMaster.OnErrorListener errorListener, QueryMaster.OnCompleteListener onCompleteListener,
                                 MultipartEntity entity) {
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
                                          QueryMaster.OnCompleteListener onCompleteListener) {

        final Map<Object, String> map = new HashMap<>();
        map.put("login", registrer.getLogin());
        map.put("password", registrer.getPassword());
        map.put("email", registrer.getEmail());
        map.put("tel1", registrer.getTelephoneFirst());
        map.put("tel2", registrer.getTelephoneSecond());
        map.put("name", registrer.getName());
        Log.d(LOG_TAG, registrer.getLogin()
                + registrer.getPassword()
                + registrer.getEmail()
                + registrer.getTelephoneFirst()
                + registrer.getTelephoneSecond()
                + registrer.getName());
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.REGISTRATION_CLIENT);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void changeRatingDriver(float rating, String idDriver, String idUser, String text, QueryMaster.OnErrorListener errorListener,
                                          QueryMaster.OnCompleteListener onCompleteListener) {
        final Map<Object, String> map = new HashMap<>();
        map.put("voted", "" + rating);
        map.put("idDriver", idDriver);
        map.put("iduser", idUser);
        map.put("text", text);
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.RATING_DRIVER);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void getGlobalRatingDriver(String id, String idDriver, QueryMaster.OnErrorListener errorListener,
                                             QueryMaster.OnCompleteListener onCompleteListener) {

        final Map<Object, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("idDriver", idDriver);
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.GET_GLOBAL_RATING_DRIVER);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void changeOlderPasswordUser(UserInfo userInfo, QueryMaster.OnErrorListener errorListener,
                                               QueryMaster.OnCompleteListener onCompleteListener) {

        final Map<Object, String> map = new HashMap<>();
        map.put("email", "" + "userInfo.");
        map.put("oldPass", "1111111111");
        map.put("newPass", "11111");
        map.put("type", "user");
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.EDIT_PASSWORD);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        sendQuery(errorListener, onCompleteListener, entity);
    }

    public static void authoritationDriver(String email, String password, QueryMaster.OnErrorListener errorListener,
                                           QueryMaster.OnCompleteListener onCompleteListener){
        final Map<Object, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.AUTHORITATION_DRIVER);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);

    }

    public static void registrationDriver(DriverInfo driverInfo, QueryMaster.OnErrorListener errorListener,
                                               QueryMaster.OnCompleteListener onCompleteListener){
        final Map<Object, String> map = new HashMap<>();
        map.put("email", "sanya@ukr.net");
        map.put("password", "1111111111");
        map.put("car_type", "1111111111");
        map.put("model_car", "1111111111");
        map.put("car_class", "1111111111");
        map.put("tel1", "1111111111");
        map.put("name", "1111111111");
        map.put("city", "1111111111");
        map.put("tokens", "1111111111");
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.REGISTRTION_DRIVER);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);

    }

    public static void correctInfoToDriver(DriverInfo driverInfo, QueryMaster.OnErrorListener errorListener,
                                          QueryMaster.OnCompleteListener onCompleteListener){
        final Map<Object, String> map = new HashMap<>();
        map.put("id", "42");
        map.put("email", "sanyaB@ukr.net");
        map.put("password", "1111111111");
        map.put("tel1", "1111111111");
        map.put("tel2", "1111111111");
        map.put("tel3", "1111111111");
        map.put("fio", "deressf sfdfdser");
        map.put("car_type", "1111111111");
        map.put("number_gos", "1111111111");
        map.put("model_car", "1");
        map.put("car_class", "1");
        map.put("color", "1");
        map.put("year_m", "1");
        map.put("carPhoto", "1");
        map.put("driverPhoto", "1");
        map.put("city", "1111111111");
        map.put("country", "1");
        map.put("child_seat", "1");
        map.put("luggage", "1");
        map.put("animals", "1");
        map.put("wi_fi", "1");
        map.put("condition", "1");
        map.put("longe_for_smokers", "1");
        map.put("plastic_card", "1");
        map.put("bill", "1");
        map.put("taximeter", "1");
        map.put("other", "1");
        MultipartEntity entity;
        try {
            entity = getEntity(map, TaxiActions.EDIT_DATA_DRIVER);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        sendQuery(errorListener, onCompleteListener, entity);

    }


    private static class TaxiActions {
        public static final String SEARCH_TAXI = "users.order-taxi";
        public static final String AUTHORITATION_CLIENT = "users.auth-client";
        public static final String REGISTRATION_CLIENT = "users.registration-client";
        public static final String RATING_DRIVER = "users.rating-driver";
        public static final String GET_GLOBAL_RATING_DRIVER = "users.rating-driver";
        public static final String EDIT_PASSWORD = "users.edit-password";
        public static final String REGISTRTION_DRIVER = "users.registration-driver";
        public static final String AUTHORITATION_DRIVER = "users.auth-driver";
        public static final String EDIT_DATA_DRIVER = "users.edit-data-driver";
    }
}
