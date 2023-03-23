package com.example.focustimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    TextView mTimerTextView; //variable for the timer
    MediaPlayer mMediaPlayer;
    Handler mHandler;
    private int mTimerValue = 0;
    private int mTargetTime = 60*15; // set the target time in seconds here
    Button setGoalButton;
    TextView setGoalWarning;
    Button inputAcceptBtn;
    EditText setGoalEdtText;
    EditText setGoalDurationEdtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DEFINING SOME UI ELEMENTS
        setGoalButton = findViewById(R.id.id_setGoal); //button for setting a goal
        setGoalWarning = findViewById(R.id.id_txt_setGoal);


        //ASSIGNING TIMER ELEMENTS
        mTimerTextView=findViewById(R.id.tvTimer);
        mHandler=new Handler();


        setGoalButton.setOnClickListener(new View.OnClickListener() { //what happens whe you click
            //set goal button
            @Override
            public void onClick(View v) {
                setGoalInputScreen();

            }
        });
        inputAcceptBtn=findViewById(R.id.btInputAccept);
        inputAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGoal();
            }
        });
    }
    private void getGoal(){
        EditText firstInputEditText = findViewById(R.id.edtGoalName);
        String firstInput = firstInputEditText.getText().toString(); //this will hold the goal's
        // name when user enters
        EditText secondInputEditText= findViewById(R.id.edtGoalDuration);
        String secondUserInput = secondInputEditText.getText().toString();
        int secondInput = Integer.parseInt(secondUserInput);
        mTargetTime=secondInput;
        mTimerTextView.setText(firstInput.toString());
        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.beep);
        setTimerScreen();
        startTimer();


    }
    private void setNoGoalScreen(){
        setGoalButton.setVisibility(View.VISIBLE);
        setGoalWarning.setVisibility(View.VISIBLE);
        mTimerTextView.setVisibility(View.GONE);
    }
    private void setTimerScreen(){
        mTimerTextView.setVisibility(View.VISIBLE);
        inputAcceptBtn.setVisibility(View.GONE);
        setGoalEdtText.setVisibility(View.GONE);
        setGoalDurationEdtText.setVisibility(View.GONE);
    }
    private void setGoalInputScreen(){
        setGoalButton.setVisibility(View.GONE);
        setGoalWarning.setVisibility(View.GONE);
        setGoalDurationEdtText=findViewById(R.id.edtGoalDuration);
        setGoalDurationEdtText.setVisibility(View.VISIBLE);
        inputAcceptBtn.setVisibility(View.VISIBLE);
        setGoalEdtText=findViewById(R.id.edtGoalName);
        setGoalEdtText.setVisibility(View.VISIBLE);

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
                    Toast.makeText(MainActivity.this, "Congratulations! You have achieved your goal!", Toast.LENGTH_LONG).show();
                    setNoGoalScreen();
                    mTimerValue=0;
                }
            }
        }, 1000); // delay of 1 second
    }

    private void updateTimerText() {
        int minutes = mTimerValue / 60;
        int seconds = mTimerValue % 60;
        String timeString = String.format("%d:%02d", minutes, seconds);
        mTimerTextView.setText(timeString);
    }
    @Override
    protected void onStop() {

        super.onStop();
        mHandler.removeCallbacksAndMessages(null);

    }
}