package ecnu.pb.wireless_order.model;

public class UserModel {

    private int isVip;
    private int user_id;
    private String user_name;
    private String user_password;
    private String user_phone;
    private int LoginSuccess;
    private int RegSuccess;

    public UserModel() {}

    public int getId() {
        return user_id;
    }

    public String getName() {
        return user_name;
    }

    public int getIsVip() {
        return isVip;
    }

    public String getPhone() {
        return user_phone;
    }

    public int getLoginSuccess() {
        return LoginSuccess;
    }

    public int getRegSuccess() {
        return RegSuccess;
    }
}
