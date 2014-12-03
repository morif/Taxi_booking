package ch.crut.taxi.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.enumeration.NumberPhoto;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.request.DriverInfo;
import ch.crut.taxi.utils.request.ServerRequest;

/**
 * Created by Alex on 26.11.2014.
 */
@EFragment(R.layout.fragment_driver_correct_info)
public class FragmentDriverCorrectInfo extends Fragment {
    private ImageView imageView;
    private static final int REQUEST_CODE = 1;
    private static final String LOG_TAG = "FragmentDriverCorrectInfo";
    private ActivityMain activityMain;
    private byte[] driverPhotoArray;
    private byte[] firstPhotoCarArray;
    private byte[] secondPhotoCarArray;
    private byte[] threedPhotoCarArray;
    private static NumberPhoto numberPhoto;

    public static FragmentDriverCorrectInfo newInstance() {
        FragmentDriverCorrectInfo fragmentDriverCorrectInfo = new FragmentDriverCorrectInfo_();
        Bundle bundle = new Bundle();
        Log.d(LOG_TAG, "4");
        fragmentDriverCorrectInfo.setArguments(bundle);
        return fragmentDriverCorrectInfo;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
        Log.d(LOG_TAG, "1");
    }

    @ViewById(R.id.fragmentDriverCorrectDriverPhoto)
    protected ImageView driverImageView;

    @ViewById(R.id.fragmentDriverCorrectInfoNameDriver)
    protected TextView nameDriverTextView;

    @ViewById(R.id.fragmentDriverCorrectInfoFirstPhotoCar)
    protected ImageView firstImageView;

    @ViewById(R.id.fragmentDriverCorrectInfoSecondPhotoCar)
    protected ImageView secondImageView;

    @ViewById(R.id.fragmentDriverCorrectInfoThreedPhotoCar)
    protected ImageView threedImageView;

    @ViewById(R.id.fragmentDriverCorrectInfoAmendPassword)
    protected Button amndPasswordButton;

    @ViewById(R.id.fragmentDriverCorrectInfoAmendTown)
    protected Button amendTownButton;

    @ViewById(R.id.fragmentDriverCorrectInfoAddTelephone)
    protected Button addTelephoneButton;

    @ViewById(R.id.fragmentDriverCorrectInfoCallSignNumber)
    protected Button callSignNumberButton;

    @ViewById(R.id.fragmentDriverCorrectInfoChieldSet)
    protected CheckBox chieldSetCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoChieldSet)
    protected void clickToChieldSet() {
        if (chieldSetCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoDownloadInCar)
    protected CheckBox downloadInCarCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoDownloadInCar)
    protected void clickLoadInCar() {
        if (downloadInCarCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoAnimalsTransportation)
    protected CheckBox animalTransportationCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoAnimalsTransportation)
    protected void clickAnimalTransportation() {
        if (animalTransportationCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoAirConditioning)
    protected CheckBox airConditionCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoAirConditioning)
    protected void clickLoadInCarAirConditioning() {
        if (airConditionCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoWifiZona)
    protected CheckBox wifiZonaCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoWifiZona)
    protected void clickToWifi() {
        if (wifiZonaCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoNotSmockes)
    protected CheckBox notSmockesCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoNotSmockes)
    protected void clickToNotSmockes() {
        if (notSmockesCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoPaymentCard)
    protected CheckBox paymentCardCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoPaymentCard)
    protected void clickToPaaymentCard() {
        if (paymentCardCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoSeleryTaximer)
    protected CheckBox seleryTaximetrCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoSeleryTaximer)
    protected void clickToSeleryTaximer() {
        if (seleryTaximetrCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoDeliveryChecks)
    protected CheckBox deliveryChecksCheckBox;

    @CheckedChange(R.id.fragmentDriverCorrectInfoDeliveryChecks)
    protected void clickToDeliveryChecks() {
        if (deliveryChecksCheckBox.isChecked()) {

        }
    }

    @ViewById(R.id.fragmentDriverCorrectInfoSaveChange)
    protected Button saveChanges;


    @Click(R.id.fragmentDriverCorrectInfoSaveChange)
    protected void saveAllInfoToDriver() {
        Log.d(LOG_TAG, "start");
        DriverInfo driverInfo = DriverInfo.getDriverInfo();
        ServerRequest.correctInfoToDriver(driverInfo, activityMain, onCompleteListener);
        Log.d(LOG_TAG, "norm_work");
    }


    @Click(R.id.fragmentDriverCorrectDriverPhoto)
    protected void clickToDriverPhoto() {
        imageView = driverImageView;
        numberPhoto = NumberPhoto.PROFILE_DRIVER_PHOTO;
        changePhoto();
    }


    @Click(R.id.fragmentDriverCorrectInfoFirstPhotoCar)
    protected void clickToFirstPhoto() {
        imageView = firstImageView;
        numberPhoto = NumberPhoto.FIRST_PHOTO;

        changePhoto();
    }

    @Click(R.id.fragmentDriverCorrectInfoSecondPhotoCar)
    protected void clickToSecondPhoto() {
        imageView = secondImageView;
        numberPhoto = NumberPhoto.SECOND_PHOTO;
        changePhoto();
    }

    @Click(R.id.fragmentDriverCorrectInfoThreedPhotoCar)
    protected void clickToThreedPhoto() {
        imageView = threedImageView;
        numberPhoto = NumberPhoto.THREE_PHOTO;
        changePhoto();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        Bitmap bitmap = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                stream = activityMain.getContentResolver().openInputStream(data.getData());

                bitmap = BitmapFactory.decodeStream(stream);
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());

                setImageAndConvertInByte(bitmap1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    protected void changePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setImageAndConvertInByte(Bitmap bitmap1) {
        imageView.setBackground(new BitmapDrawable(activityMain.getResources(), bitmap1));
        byte[] arrayBitMap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bos);

        arrayBitMap = bos.toByteArray();
        switch (numberPhoto) {
            case FIRST_PHOTO:
                firstPhotoCarArray = arrayBitMap;
                break;
            case SECOND_PHOTO:
                secondPhotoCarArray = arrayBitMap;
                break;
            case THREE_PHOTO:
                threedPhotoCarArray = arrayBitMap;
                break;
            case PROFILE_DRIVER_PHOTO:
                driverPhotoArray = arrayBitMap;
                break;
        }
    }


    private QueryMaster.OnCompleteListener onCompleteListener = new QueryMaster.OnCompleteListener() {


        @Override
        public void QMcomplete(JSONObject jsonObject) throws JSONException {

            Log.d(LOG_TAG, "json: " + jsonObject.toString());
            //   JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            //  Log.d(LOG_TAG, "json: " + jsonObject1.toString());

        }
    };


}
