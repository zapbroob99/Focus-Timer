package com.example.focustimer.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.focustimer.BottomNavigationBar;
import com.example.focustimer.R;
import com.example.focustimer.user.UserClass;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationBar.setupBottomNavigationBar(this, R.id.bottomnav);

    }
}