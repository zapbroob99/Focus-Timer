package com.example.focustimer.loginsignup;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.focustimer.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {
    //variables
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    Button callOtpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);
        //hooks
        scrollView=findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber=findViewById(R.id.signup_phone_number);
        callOtpButton=findViewById(R.id.signup_next_button);
        callOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePhoneNumber()){
                    return;
                }
                //get all the values passed from previos screens using intent
                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _username = getIntent().getStringExtra("username");
                String _password = getIntent().getStringExtra("password");
                String _date = getIntent().getStringExtra("date");
                String _gender = getIntent().getStringExtra("gender");

                //Get complete phone number
                String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
                //Remove first zero if entered!
                if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                    _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
                }
                //Complete phone number
                final String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

                Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);
                intent.putExtra("fullName",_fullName);
                intent.putExtra("email",_email);
                intent.putExtra("username",_username);
                intent.putExtra("password",_password);
                intent.putExtra("date",_date);
                intent.putExtra("gender",_gender);
                intent.putExtra("phoneNo",_phoneNo);
                startActivity(intent);
            }
        });


    }



    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        }  else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }







}