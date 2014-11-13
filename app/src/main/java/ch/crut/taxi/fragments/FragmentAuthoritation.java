package ch.crut.taxi.fragments;


import android.support.v4.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;

@EFragment(R.layout.fragment_authoritation)
public class FragmentAuthoritation extends Fragment {

    private String emailAddressString;
    private String nameString;
    private String telephoneString;


    @ViewById(R.id.emailAddressEdit)
    protected EditText emailAddressEditText;

    @ViewById(R.id.nameEdit)
    protected EditText nameEditText;

    @ViewById(R.id.telephoneEdit)
    protected EditText telephoneEditText;

    @ViewById(R.id.confirmButton)
    protected Button confButton;

    @Click(R.id.confirmButton)
    protected void clickConfirmButton() {

        emailAddressString = emailAddressEditText.getText().toString();
        nameString = nameEditText.getText().toString();
        telephoneString = telephoneEditText.getText().toString();
        if (emailAddressString.length() > 0 && nameString.length() > 0 && telephoneString.length() > 0) {


        }
    }
}
