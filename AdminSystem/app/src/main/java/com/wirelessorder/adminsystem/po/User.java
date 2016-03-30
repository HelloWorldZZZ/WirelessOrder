package com.wirelessorder.adminsystem.po;

/**
 * Created by triplez on 16-3-30.
 */
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userPhone;

    public User() {
    }

    public User(String userName, String userPassword,
                String userPhone) {
        super();
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }



}
