package com.example.focustimer.loginsignup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.appwidget.AppWidgetHost;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.focustimer.MainActivity;
import com.example.focustimer.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {

    //variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressBar;
    Button loginBtn,forgetBtn,goToSignUpBtn;
    CheckBox rememberMe;
    EditText phoneEditText,passwordEditText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //hooks
        countryCodePicker=findViewById(R.id.login_country_code_picker);
        phoneNumber=findViewById(R.id.login_phone);
        password=findViewById(R.id.login_password);
        loginBtn=findViewById(R.id.btn_go);
        forgetBtn=findViewById(R.id.btn_forget);
        goToSignUpBtn=findViewById(R.id.btn_signup);
        //TODO: rememberMe=findViewById(R.id.checkBox);
        phoneEditText=findViewById(R.id.phone_edittext);
        passwordEditText=findViewById(R.id.password_edittext);


        loginBtn.setOnClickListener(new View.OnClickListener() {

            //Login the user in app
            @Override
            public void onClick(View v) {
                letTheUserLoggedIn();
            }
        });
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class)); //TODO: add forget
            }
        });
        goToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });




    }
    //login the user in app
    private void letTheUserLoggedIn() {

        if(!isConnected(this)){
            showCustomDialog();
        }

        if(!validateFields()){
            return;
        }

        //get data
        String _phoneNumber=phoneNumber.getEditText().getText().toString().trim();
        String _password=password.getEditText().getText().toString().trim();
        if(_phoneNumber.charAt(0)=='0'){
            _phoneNumber =_phoneNumber.substring(1);
        }
        String _completePhoneNumber = "+"+countryCodePicker.getSelectedCountryCode()+_phoneNumber;





        //database
        Query checkUser = FirebaseDatabase.getInstance("https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(_password)){
                        password.setError(null);
                        password.setErrorEnabled(false);
                        String _username=snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                        String _fullname=snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email=snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo=snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _date=snapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                        String _gender=snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);


                    }
                    else {

                        Toast.makeText(LoginScreen.this,"Password Does Not match!" , Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(LoginScreen.this,"No such User Exist!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    } //end method

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),SignUp.class));
                        finish();
                    }
                });
    }

    private boolean isConnected(LoginScreen login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifiConn!=null&&wifiConn.isConnected()||(mobileConn!=null&&mobileConn.isConnected())){
            return true;
        }
        else{
            return false;
        }


    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        if(_phoneNumber.isEmpty()){
            phoneNumber.setError("Cannot Empty");
            phoneNumber.requestFocus();
        }
        else if(_password.isEmpty()){
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        }
        return true;


    }
}