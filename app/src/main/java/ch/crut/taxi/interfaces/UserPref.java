package ch.crut.taxi.interfaces;

import org.androidannotations.annotations.sharedpreferences.DefaultFloat;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface UserPref {

    @DefaultString("")
    public String id();

    @DefaultString("")
    public String password();

    @DefaultString("")
    public String email();

    @DefaultString("")
    public String name();

    @DefaultString("")
    public String phoneFirst();

    @DefaultString("")
    public String phoneSecond();

    @DefaultFloat(0)
    public float latitude();

    @DefaultFloat(0)
    public float longitude();

    @DefaultString("")
    public String street();

    @DefaultString("")
    public String home();

}
