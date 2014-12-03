package ch.crut.taxi.utils.request;

/**
 * Created by Alex on 25.11.2014.
 */
public class DriverInfo {
    private static DriverInfo driverInfo;
    private String emailDriver;
    private String passwordDriver;
    private String typeCarDriver;
    private String makeAndModelCarDriver;
    private String classCar;
    private String firstTelephone;
    private String sonameAndNameDriver;
    private String townDriver;



    private DriverInfo(){

    }
    public static synchronized DriverInfo getDriverInfo(){
        if(driverInfo == null){
            driverInfo = new DriverInfo();
        }
        return driverInfo;
    }

    public String getTownDriver() {
        return townDriver;
    }

    public void setTownDriver(String townDriver) {
        this.townDriver = townDriver;
    }

    public String getSonameAndNameDriver() {
        return sonameAndNameDriver;
    }

    public void setSonameAndNameDriver(String sonameAndNameDriver) {
        this.sonameAndNameDriver = sonameAndNameDriver;
    }

    public String getFirstTelephone() {
        return firstTelephone;
    }

    public void setFirstTelephone(String firstTelephone) {
        this.firstTelephone = firstTelephone;
    }

    public String getClassCar() {
        return classCar;
    }

    public void setClassCar(String classCar) {
        this.classCar = classCar;
    }

    public String getMakeAndModelCarDriver() {
        return makeAndModelCarDriver;
    }

    public void setMakeAndModelCarDriver(String makeAndModelCarDriver) {
        this.makeAndModelCarDriver = makeAndModelCarDriver;
    }

    public String getTypeCarDriver() {
        return typeCarDriver;
    }

    public void setTypeCarDriver(String typeCarDriver) {
        this.typeCarDriver = typeCarDriver;
    }

    public String getPasswordDriver() {
        return passwordDriver;
    }

    public void setPasswordDriver(String passwordDriver) {
        this.passwordDriver = passwordDriver;
    }

    public String getEmailDriver() {
        return emailDriver;
    }

    public void setEmailDriver(String emailDriver) {
        this.emailDriver = emailDriver;
    }
}
