package name.walnut.kanjian.app.resource.dto;

/**
 * Created by walnut on 15/10/12.
 */
public class LoginParam {

    private String phone;
    private String password;
    private String deviceToken;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
