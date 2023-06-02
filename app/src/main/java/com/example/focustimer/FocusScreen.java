package com.example.focustimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FocusScreen extends AppCompatActivity {
    private String goalName;
    private int goalDuration;
    MediaPlayer mMediaPlayer;
    Handler mHandler;
    RelativeLayout rl;
    ProgressBar mProgressBar;
    private int mTimerValue = 0;
    private int mTargetTime = 60*15; // set the target time in seconds here
    TextView progressText;
    Button btnEnd;
    public FocusScreen(){
        //empty constructor
    }
    public FocusScreen(String goalName,int goalDuration){
        this.goalDuration=goalDuration;
        this.goalName=goalName;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_screen);
        rl = findViewById(R.id.relativeProgressBar); //relativelayout
        mProgressBar = findViewById(R.id.progressBar); //circle timer
        mHandler=new Handler();

        Intent intent = getIntent();
        mTargetTime = intent.getIntExtra("duration", 0);
         // intent.getStringExtra("name");
        mMediaPlayer = MediaPlayer.create(FocusScreen.this, R.raw.beep);
        startTimer(); //starting timer

        btnEnd=findViewById(R.id.btnEnd);
        //end button function
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerValue=mTargetTime;
            }
        });


    }
    private void startTimer(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mTimerValue++;
                updateTimerText();
                if (mTimerValue < mTargetTime) {
                    startTimer();
                }
                else {
                    mMediaPlayer.start();
                    Toast.makeText(FocusScreen.this, "Congratulations! You have achieved your goal!", Toast.LENGTH_LONG).show();
                    mTimerValue=0;
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent); //TODO: add saved preferences to store session and return
                }
            }
        }, 1000); // delay of 1 second
    }

    private void updateTimerText() {
        int minutes = mTimerValue / 60;
        int seconds = mTimerValue % 60;
        String timeString = String.format("%d:%02d", minutes, seconds);
        progressText=findViewById(R.id.progressText);
        progressText.setText(""+timeString);
        int progress = (int) (((float) mTimerValue / (float) mTargetTime) * 100);
        mProgressBar.setProgress(progress);

    }
    @Override
    protected void onStop() {

        super.onStop();
        mHandler.removeCallbacksAndMessages(null);

    }


}