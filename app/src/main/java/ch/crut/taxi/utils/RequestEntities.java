package ch.crut.taxi.utils;


public class RequestEntities {

    public static class Registrer{
    private String login;
    private String password;
    private String email;
    private String telephoneFirst;
    private String telephoneSecond;
    private String name;

    public  String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }}
}
