package com.example.focustimer.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.focustimer.BottomNavigationBar;
import com.example.focustimer.R;
import com.example.focustimer.user.UserClass;

public class Stats extends AppCompatActivity {
    TextView tvFocusTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        BottomNavigationBar.setupBottomNavigationBar(this, R.id.bottomnav);
        tvFocusTime=findViewById(R.id.txt_focustime);
        tvFocusTime.setText(""+UserClass.getTotalFocusTime());
    }
}