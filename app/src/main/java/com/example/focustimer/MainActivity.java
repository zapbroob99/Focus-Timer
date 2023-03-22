package com.example.focustimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button setGoalButton = findViewById(R.id.id_setGoal);
        TextView testText = findViewById(R.id.id_test);
        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoalButton.setVisibility(View.GONE);
                testText.setVisibility(View.VISIBLE);
            }
        });
    }
}