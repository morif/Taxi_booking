package ch.crut.taxi.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;

import ch.crut.taxi.R;

/**
 * Created by Alex on 13.11.2014.
 */
public class FragmentRegistration extends Fragment {

    private String emailAddressRegistrationString;
    private String nameRegistrationString;
    private String telephoneFirstRegistrationString;
    private String telephoneSecondRegistrationString;
    private String loginRegistrationString;
    private String passwordRegistrationString;


    @ViewById(R.id.loginRegistrationEdit)
    protected EditText loginRegistrationEditText;

    @ViewById(R.id.passwordRegistrationEdit)
    protected EditText passwordRegistrationEditText;


    @ViewById(R.id.emailAddressRegistrationEdit)
    protected EditText emailAddressRegistrationEditText;

    @ViewById(R.id.nameRegistrationEdit)
    protected EditText nameRegistrationEditText;

    @ViewById(R.id.telephoneFirstRegistrationEdit)
    protected EditText telephoneFirstRegistrationEditText;

    @ViewById(R.id.telephoneSecondRegistrationEdit)
    protected EditText telephoneSecondRegistrationEditText;

    @ViewById(R.id.confirmRegistrationButton)
    protected Button confRegistrationButton;

    @Click(R.id.confirmRegistrationButton)
    protected void clickConfirmButton() {

            passwordRegistrationString = passwordRegistrationEditText.getText().toString();
            loginRegistrationString = loginRegistrationEditText.getText().toString();
            emailAddressRegistrationString = emailAddressRegistrationEditText.getText().toString();
            nameRegistrationString = nameRegistrationEditText.getText().toString();
            telephoneFirstRegistrationString = telephoneFirstRegistrationEditText.getText().toString();
            telephoneSecondRegistrationString = telephoneSecondRegistrationEditText.getText().toString();

            if (emailAddressRegistrationString.length() > 0
                    && nameRegistrationString.length() > 0
                    && telephoneFirstRegistrationString.length() > 0
                    && telephoneSecondRegistrationString.length() > 0) {






        }
    }


}
