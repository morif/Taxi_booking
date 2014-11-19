package ch.crut.taxi.fragments;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapters.AdapterFavoriteAddresses;
import ch.crut.taxi.database.DAOFavorite;
import ch.crut.taxi.effects.swipedismiss.SwipeDismissListViewTouchListener;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.NavigationPoint;
import ch.crut.taxi.utils.actionbar.NBController;
import ch.crut.taxi.adapters.AdapterAutoComplete;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.utils.AutoCompleteAsyncTask;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.google.map.LocationAddress;
import ch.crut.taxi.views.AutoCompleteTextViewCustomAdapter;

@SmartFragment(items = {NBItems.CANCEL, NBItems.DONE})
@EFragment(R.layout.fragment_direction_action)
public class FragmentDirectionAction extends NBFragment implements NBItemSelector {


    private OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey;
    private String inputStreetName;
    private AutoCompleteAsyncTask autoCompleteAsyncTask;

    private NavigationPoint navigationPoint;

    private AdapterFavoriteAddresses adapterFavoriteAddresses;


    public static FragmentDirectionAction newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {
        FragmentDirectionAction fragmentDirectionAction = new FragmentDirectionAction_();

        Bundle bundle = new Bundle();
        bundle.putSerializable(OnPlaceSelectedListener
                .PLACE_SELECTED_KEY_SERIALIZABLE, placeSelectedKey);

        fragmentDirectionAction.setArguments(bundle);

        return fragmentDirectionAction;
    }

    @ViewById(R.id.fragmentDirectionActionAddressAutoCompleteTextView)
    protected AutoCompleteTextViewCustomAdapter autoCompleteTextView;

    @ViewById(R.id.fragmentDirectionActionProgressAutoComplete)
    protected ProgressBar progressBarInAuto;

    @ViewById(R.id.fragmentDirectionActionFavoriteListView)
    protected ListView favoriteListView;


    @Click(R.id.fragmentDirectionActionMyLocation)
    protected void clickMyLocation() {
        final FragmentPlaceSelector placeSelector = FragmentPlaceSelector.newInstance(
                placeSelectedKey, true);
        ((ActivityMain) getActivity()).add(placeSelector);
    }

    @Click(R.id.fragmentDirectionActionSetLocation)
    protected void clickSetLocation() {
        final FragmentPlaceSelector placeSelector = FragmentPlaceSelector
                .newInstance(placeSelectedKey);

        ((ActivityMain) getActivity()).add(placeSelector);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentDirectionAction.class);

        Bundle bundle = getArguments();

        placeSelectedKey = (OnPlaceSelectedListener.PlaceSelectedKeys)
                bundle.getSerializable(OnPlaceSelectedListener.PLACE_SELECTED_KEY_SERIALIZABLE);

        navigationPoint = new NavigationPoint();

        adapterFavoriteAddresses = new AdapterFavoriteAddresses(activity);
        adapterFavoriteAddresses.setOnClickListener(onFavoriteClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        inputStreetName = autoCompleteTextView.getText().toString();
        autoCompleteTextView.addTextChangedListener(autoCompleteTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AutoCompleteListener(getActivity()));


        favoriteListView.setAdapter(adapterFavoriteAddresses);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        favoriteListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                mAdapter.remove(mAdapter.getItem(position));
                                    adapterFavoriteAddresses.remove(adapterFavoriteAddresses.getItem(position));
                                }
                            }
                        });
        favoriteListView.setOnTouchListener(touchListener);
        favoriteListView.setOnScrollListener(touchListener.makeScrollListener());
    }


    @Override
    public void onPause() {
        super.onPause();

        if (autoCompleteAsyncTask != null)
            autoCompleteAsyncTask.cancel(true);
    }

    public void searchStreet(String[] listStreetsArray) {
        AdapterAutoComplete adapter = new AdapterAutoComplete(getActivity(), listStreetsArray);

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);

        adapter.notifyDataSetChanged();
    }


    private TextWatcher autoCompleteTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!autoCompleteTextView.toString().equals("")) {
                inputStreetName = autoCompleteTextView.getText().toString();
                autoCompleteAsyncTask = new AutoCompleteAsyncTask(
                        FragmentDirectionAction.this, progressBarInAuto, inputStreetName);
                autoCompleteAsyncTask.execute(inputStreetName);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                navigationPoint = new NavigationPoint();

                getNBController().hide(NBItems.DONE);
            }
        }
    };

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.CANCEL:
                FragmentHelper.pop(getFragmentManager());
                break;
            case NBItems.DONE:
                ((ActivityMain) getActivity())
                        .placeSelected(navigationPoint, placeSelectedKey);

                ((ActivityMain) getActivity()).initialScreen();
                break;
        }
    }

    public class AutoCompleteListener implements AdapterView.OnItemClickListener {

        private Context context;

        public AutoCompleteListener(Context context) {
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            QueryMaster.toast(context, "itemClick");

            String correctStreetName = autoCompleteTextView.getText().toString();

            double latitude = 0;
            double longitude = 0;

            try {

                Address address = LocationAddress.getFromLoсationName(context, correctStreetName).get(0);

                latitude = address.hasLatitude() ? address.getLatitude() : 0;
                longitude = address.hasLongitude() ? address.getLongitude() : 0;

                navigationPoint.setLatLng(new LatLng(latitude, longitude));
                navigationPoint.setAddress(address);
                navigationPoint.setAddressString(correctStreetName);

                getNBController().show(NBItems.DONE);

                Log.d("", " lat, lng -> " + latitude + ", " + longitude);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private AdapterFavoriteAddresses.OnClickListener onFavoriteClickListener = new AdapterFavoriteAddresses.OnClickListener() {
        @Override
        public void onClick(DAOFavorite.FavoriteAddress favoriteAddress) {
            QueryMaster.toast(getActivity(), favoriteAddress.street);
        }
    };
}
