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

public class ServerRequest {
    public static final String SERVER = "http://beestore.com.ua/taxi/api/main.php";


//    data={"action":"users.order-taxi","data"{"latStart":"","lngStart":"","radius":"5"}}

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

//        for (Object key : map.keySet()) {
//            json.put((String) key, map.get(key));
//        }
//
//        JSONObject data = new JSONObject(map);


//        multipartEntity.addPart("action", new StringBody(action));
        multipartEntity.addPart("data",
                new StringBody(json.toString(), Charset.forName("UTF-8")));

        return multipartEntity;
    }

    private static class TaxiActions {
        public static final String SEARCH_TAXI = "users.order-taxi";
    }
}
