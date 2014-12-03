package ch.crut.taxi.push;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class DeviceKey {

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public void setOnKeyRegisteredListener(OnKeyRegisteredListener onKeyRegisteredListener) {
        this.onKeyRegisteredListener = onKeyRegisteredListener;
    }

    private OnKeyRegisteredListener onKeyRegisteredListener;

    /**
     * Substitute you own sender COLUMN_ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    private static final String SENDER_ID = "316984739251";

    /**
     * Tag used on log messages.
     */
    private static final String TAG = "GCMDemo";

    private GoogleCloudMessaging gcm;
    private Activity activity;

    private String registrationID;

    public DeviceKey(Activity activity) {
        try {
            onKeyRegisteredListener = (OnKeyRegisteredListener) activity;

            this.activity = activity;
            getKey();

        } catch (ClassCastException ex) {
            throw new RuntimeException(activity.toString() + " must implement DeviceKey.OnKeyRegisteredListener");
        }
    }

    private void getKey() {
        // Check device for Play Services APK. If check succeeds, proceed with
        // GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(activity);
            registrationID = getRegistrationId(activity);

            if (registrationID.isEmpty()) {
                registerInBackground();
            } else {
                onKeyRegisteredListener.keyRegistered(registrationID);
            }
        } else {
            onKeyRegisteredListener.GooglePlayServicesUnavailable();
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    /**
     * Gets the current registration COLUMN_ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration COLUMN_ID, or empty string if there is no existing
     * registration COLUMN_ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration COLUMN_ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.d(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration COLUMN_ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(DeviceKey.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration COLUMN_ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration COLUMN_ID, or empty string if there is no existing
     * registration COLUMN_ID.
     */

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration COLUMN_ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {

//        Log.e("", "registerInBackground");

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }

                if (!isNetworkConnected(activity)) {
//                    Log.e("", "QueryMaster.isNetworkConnected false");
                    onKeyRegisteredListener.networkUnavailable();
                    return null;
                }
//                Log.e("", "QueryMaster.isNetworkConnected true");

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activity);
                    }
                    registrationID = gcm.register(SENDER_ID);

                    // You should send the registration COLUMN_ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
//                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
//                    storeRegistrationId(context, registrationID);
                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                    return "";
                }
//                return msg;
                return registrationID;
            }

            @Override
            protected void onPostExecute(Object msg) {
                super.onPostExecute(msg);

                if (msg != null) {
                    if (!msg.toString().isEmpty()) {

                        SharedPreferences sharedPreferences = getGCMPreferences(activity);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PROPERTY_REG_ID, msg.toString());
                        editor.commit();

                        onKeyRegisteredListener.keyRegistered(msg.toString());
                    } else {
                        onKeyRegisteredListener.UnableObtainKey();
                    }
                }
            }
        }.execute(null, null, null);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static interface OnKeyRegisteredListener {

        public void keyRegistered(String key);

        public void GooglePlayServicesUnavailable();

        public void UnableObtainKey();

        public void networkUnavailable();
    }
}
