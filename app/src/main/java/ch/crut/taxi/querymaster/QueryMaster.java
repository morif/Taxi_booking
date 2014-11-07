package ch.crut.taxi.querymaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class QueryMaster extends Thread {

    /**
     * Ошибка обработки запроса, возможны проблемы с интернетом
     */
    public static final String ERROR_MESSAGE = "Ошибка обработки запроса, возможны проблемы с интернетом";

    /**
     * Ошибка обработки запроса на сервере
     */
    public static final String SERVER_RETURN_INVALID_DATA = "Ошибка обработки запроса на сервере";

    protected static final int QUERY_MASTER_COMPLETE = 1;
    public static final int QUERY_MASTER_ERROR = 2;
    public static final int NETWORK_UNAVAILABLE = 3;

    public static final int QUERY_GET = 23;
    public static final int QUERY_POST = 24;

    public void setProgressDialog() {
        progressHandler.sendEmptyMessage(-1);
    }

    protected ProgressDialog progressDialog;

    private OnCompleteListener onCompleteListener;

    protected Handler resultHandler;
    protected Handler progressHandler;

    private MultipartEntity entity;
    private Context context;

    protected String serverResponse;
    private String url;

    private int queryType;

    private static final int timeoutConnection = 10000;


    /**
     * @param context
     * @param url
     * @param queryType QueryMaster.QUERY_GET or QueryMaster.QUERY_POST
     * @param entity
     */
    public QueryMaster(Context context, String url, int queryType, MultipartEntity entity) {
        this(context, url, queryType);
        this.entity = entity;
    }

    public QueryMaster(Context context, String url, int queryType) {
        this.context = context;
        this.url = url;
        this.queryType = queryType;
        initHandler();
    }

    @Override
    public void run() {
        super.run();
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        if (!isNetworkConnected() && resultHandler != null) {
            resultHandler.sendEmptyMessage(NETWORK_UNAVAILABLE);
            return;
        }
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                timeoutConnection);


        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
//        httpclient.setParams(httpParams);

        HttpPost httpPost;
        HttpGet httpGet;

        HttpResponse response = null;

        try {

            if (queryType == QUERY_GET) {
                httpGet = new HttpGet(url);

                response = httpclient.execute(httpGet);

            } else if (queryType == QUERY_POST) {

                httpPost = new HttpPost(url);
                if (entity != null) {
                    httpPost.setEntity(entity);
                }
                response = httpclient.execute(httpPost);
            }

            serverResponse = response != null ?
                    EntityUtils.toString(response.getEntity()) : null;

            Log.e("", "serverResponse -> " + serverResponse);

            if (successResultListener != null) {
                successResultListener.result(url, serverResponse);
            }

            if (resultHandler != null) {
                resultHandler.sendEmptyMessage(QUERY_MASTER_COMPLETE);
            }

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            if (resultHandler != null) {
                resultHandler.sendEmptyMessage(QUERY_MASTER_ERROR);
            }
        }
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface OnCompleteListener {
        public void complete(String serverResponse);

        public void error(int errorCode);
    }

    private SuccessResultListener successResultListener;

    protected void add(SuccessResultListener successResultListener) {
        this.successResultListener = successResultListener;
    }

    public interface SuccessResultListener {
        /**
         * Method called from request background thread
         *
         * @param result - string result if all is ok
         */
        public void result(String url, String result);
    }

    private void initHandler() {
        resultHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (onCompleteListener != null) {
                    if (msg.what == QUERY_MASTER_COMPLETE) {
                        onCompleteListener.complete(serverResponse);
                    }
                    if (msg.what == QUERY_MASTER_ERROR) {
                        onCompleteListener.error(QUERY_MASTER_ERROR);
                    }
                    if (msg.what == NETWORK_UNAVAILABLE) {
                        onCompleteListener.error(NETWORK_UNAVAILABLE);
                    }
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        };
        progressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                progressDialog = ProgressDialog.show(context, null, "Загрузка...");
            }
        };
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Context getContext() {
        return context;
    }

    public static AlertDialog alert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Ошибка");

        builder.setPositiveButton("Ok", null);
        return builder.show();
    }

    public static AlertDialog alert(Context context, int resID) {
        return alert(context, context.getString(resID));
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int resource) {
        Toast.makeText(context, context.getString(resource), Toast.LENGTH_SHORT).show();
    }

    public static boolean isSuccess(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("status").equalsIgnoreCase("success");
    }

    public static JSONArray getData(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray("data");
    }

    protected void clearHandler() {
        resultHandler = null;
    }
}
