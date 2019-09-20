package com.example.genuva.firebase;

public class UserModel {
    String UserName , UserPhoneNumber ,UserImageUrl ;


    public UserModel(String userName, String userPhoneNumber, String userImageUrl) {
        UserName = userName;
        UserPhoneNumber = userPhoneNumber;
        UserImageUrl = userImageUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }
}
