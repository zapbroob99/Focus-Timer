package com.example.focustimer.user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public final class UserClass {
    public static String userName,email,fullName,gender,date,phoneNo,password;
    public static int totalFocusTime;

    private static String dbReference="https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/";

    public UserClass() {
    }
    public static void setUserData(String _username, String _fullName, String _email, String _phoneNo, String _date, String _gender,
            int _focustime, String _password){

        phoneNo=_phoneNo;
        fullName=_fullName;
        userName=_username;
        gender=_gender;
        email=_email;
        date=_date;
        gender=_gender;
        password=_password;
        totalFocusTime=_focustime;
    }

    public static void updateFocusTimeOnDB(int number){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        reference.child(UserClass.getPhoneNo()).child("focustime").setValue(ServerValue.increment(number/60));
        totalFocusTime+=(number/60);
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserClass.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserClass.email = email;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        UserClass.fullName = fullName;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        UserClass.gender = gender;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        UserClass.date = date;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(String phoneNo) {
        UserClass.phoneNo = phoneNo;
    }

    public static int getTotalFocusTime() {
        return totalFocusTime;
    }

    public static void setTotalFocusTime(int totalFocusTime) {
        UserClass.totalFocusTime = totalFocusTime;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserClass.password = password;
    }
}
