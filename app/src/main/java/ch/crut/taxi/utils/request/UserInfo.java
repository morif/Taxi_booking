package ch.crut.taxi.utils.request;

/**
 * Created by Alex on 13.11.2014.
 */
public class UserInfo {
    private String id;
    private String email;
    private String name;
    private String telephoneFirst;
    private String telephoneSecond;
    private String login;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneFirst() {
        return telephoneFirst;
    }

    public void setTelephoneFirst(String telephoneFirst) {
        this.telephoneFirst = telephoneFirst;
    }

    public String getTelephoneSecond() {
        return telephoneSecond;
    }

    public void setTelephoneSecond(String telephoneSecond) {
        this.telephoneSecond = telephoneSecond;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
