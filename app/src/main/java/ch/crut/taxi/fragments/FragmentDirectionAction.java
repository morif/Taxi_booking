package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.actionbar.ActionBarController;
import ch.crut.taxi.interfaces.OnPlaceSelectedListener;
import ch.crut.taxi.utils.AutoCompliteAsyncTask;

@EFragment(R.layout.fragment_direction_action)
public class FragmentDirectionAction extends Fragment {

    private static final String LOG_TAG = "FragmentDirectionAction";
    private ActionBarController barController;
    private OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey;
    private String inputStreetName;
    private AutoCompliteAsyncTask autoCompliteAsyncTask;
    public static FragmentDirectionAction newInstance(OnPlaceSelectedListener.PlaceSelectedKeys placeSelectedKey) {
        FragmentDirectionAction fragmentDirectionAction = new FragmentDirectionAction_();

        Bundle bundle = new Bundle();
        bundle.putSerializable(OnPlaceSelectedListener
                .PLACE_SELECTED_KEY_SERIALIZABLE, placeSelectedKey);

        fragmentDirectionAction.setArguments(bundle);

        return fragmentDirectionAction;
    }

    @ViewById(R.id.serchAddressAutoCompleteTextView)
    protected AutoCompleteTextView autoCompleteTextView;

    @ViewById(R.id.progressBarInAutoComplite)
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
        barController = ((ActivityMain) getActivity()).getActionBarController();
        barController.title(getString(R.string.from_where));
        barController.cancelEnabled(true);
        inputStreetName = autoCompleteTextView.getText().toString();
        Log.d(LOG_TAG, "inputString "+inputStreetName);
        autoCompliteAsyncTask = new AutoCompliteAsyncTask(
                FragmentDirectionAction.this, progressBarInAuto, inputStreetName);
        autoCompliteAsyncTask.execute();
    }
    @Override
    public void onPause() {
        super.onPause();
        barController.cancelEnabled(false);
        if(autoCompliteAsyncTask.cancel(true)){
        }
    }
    public void searchStreet(String[] listStreetsArray) {
        ArrayAdapter adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, listStreetsArray);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!autoCompleteTextView.toString().equals("")) {
                    inputStreetName = autoCompleteTextView.getText().toString();
                    autoCompliteAsyncTask = new AutoCompliteAsyncTask(
                            FragmentDirectionAction.this, progressBarInAuto, inputStreetName);
                    autoCompliteAsyncTask.execute(inputStreetName);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        adapter.notifyDataSetChanged();
    }
}
