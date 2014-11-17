package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.utils.actionbar.NBController;
import ch.crut.taxi.adapters.AdapterAutoComplete;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.utils.AutoCompleteAsyncTask;

@EFragment(R.layout.fragment_direction_action)
public class FragmentDirectionAction extends Fragment {

    //    private static final String LOG_TAG = "FragmentDirectionAction";
    private NBController barController;
    private OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey;
    private String inputStreetName;
    private AutoCompleteAsyncTask autoCompleteAsyncTask;

    public static FragmentDirectionAction newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {
        FragmentDirectionAction fragmentDirectionAction = new FragmentDirectionAction_();

        Bundle bundle = new Bundle();
        bundle.putSerializable(OnPlaceSelectedListener
                .PLACE_SELECTED_KEY_SERIALIZABLE, placeSelectedKey);

        fragmentDirectionAction.setArguments(bundle);

        return fragmentDirectionAction;
    }

    @ViewById(R.id.fragmentDirectionActionAddressAutoCompleteTextView)
    protected AutoCompleteTextView autoCompleteTextView;

    @ViewById(R.id.fragmentDirectionActionProgressAutoComplete)
    protected ProgressBar progressBarInAuto;

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
        super.onAttach(activity);

        Bundle bundle = getArguments();

        placeSelectedKey = (OnPlaceSelectedListener.PlaceSelectedKeys)
                bundle.getSerializable(OnPlaceSelectedListener.PLACE_SELECTED_KEY_SERIALIZABLE);
    }

    @Override
    public void onStart() {
        super.onStart();

        barController = ((ActivityMain) getActivity()).getNBController();
        barController.title(getString(R.string.from_where));
//        barController.cancelEnabled(true);

        inputStreetName = autoCompleteTextView.getText().toString();
//        Log.d(LOG_TAG, "inputString " + inputStreetName);
//        autoCompleteAsyncTask = new AutoCompleteAsyncTask(
//                FragmentDirectionAction.this, progressBarInAuto, inputStreetName);
//        autoCompleteAsyncTask.execute();
        autoCompleteTextView.addTextChangedListener(autoCompleteTextWatcher);
    }

    @Override
    public void onPause() {
        super.onPause();

//        barController.cancelEnabled(false);
        autoCompleteAsyncTask.cancel(true);
    }

    public void searchStreet(String[] listStreetsArray) {
        AdapterAutoComplete adapter = new AdapterAutoComplete(getActivity(), android.R.layout.select_dialog_item, listStreetsArray);

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

        }
    };
}
