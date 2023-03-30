package com.example.focustimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    Handler mHandler;
    private int mTimerValue = 0;
    private int mTargetTime = 60*15; // set the target time in seconds here
    Button setGoalButton;
    TextView setGoalWarning;
    Button inputAcceptBtn;
    EditText setGoalEdtText;
    EditText setGoalDurationEdtText;
    ProgressBar mProgressBar;
    TextView progressText;
    RelativeLayout rl;
    NumberPicker durationPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DEFINING SOME UI ELEMENTS
        setGoalButton = findViewById(R.id.id_setGoal); //button for setting a goal
        setGoalWarning = findViewById(R.id.id_txt_setGoal);


        //ASSIGNING TIMER ELEMENTS
         rl = findViewById(R.id.relativeProgressBar); //relativelayout
        mProgressBar = findViewById(R.id.progressBar); //circle timer
        mHandler=new Handler();


        setGoalButton.setOnClickListener(new View.OnClickListener() { //what happens whe you click
            //set goal button
            @Override
            public void onClick(View v) {
                durationPicker = findViewById(R.id.duration_picker);
                durationPicker.setMinValue(1);
                durationPicker.setMaxValue(4);
                durationPicker.setDisplayedValues(new String[] {"5 min", "10 min","15 min","20 min",});
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
        EditText secondInputEditText= findViewById(R.id.edtGoalDuration);
        String secondUserInput = secondInputEditText.getText().toString();
        mTargetTime=adjustNumberPicker(durationPicker)*60;
        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.beep);
        //sending variables to focus activity
        FocusScreen focusScreen=new FocusScreen(firstInput,adjustNumberPicker(durationPicker));
        Intent i = new Intent(getApplicationContext(),FocusScreen.class);
        i.putExtra("duration", adjustNumberPicker(durationPicker)*60);
        i.putExtra("name", firstInput);
        startActivity(i);
        setTimerScreen();
        startTimer(); //starting timer


    }
    private void setNoGoalScreen(){
        setGoalButton.setVisibility(View.VISIBLE);
        setGoalWarning.setVisibility(View.VISIBLE);
        rl.setVisibility(View.GONE);

    }
    private void setTimerScreen(){
        rl.setVisibility(View.VISIBLE);
        inputAcceptBtn.setVisibility(View.GONE);
        setGoalEdtText.setVisibility(View.GONE);
        setGoalDurationEdtText.setVisibility(View.GONE);
        durationPicker.setVisibility(View.GONE);

    }
    private void setGoalInputScreen(){
        durationPicker.setVisibility(View.VISIBLE);
        setGoalButton.setVisibility(View.GONE);
        setGoalWarning.setVisibility(View.GONE);
        setGoalDurationEdtText=findViewById(R.id.edtGoalDuration);
        //setGoalDurationEdtText.setVisibility(View.VISIBLE);
        inputAcceptBtn.setVisibility(View.VISIBLE);
        setGoalEdtText=findViewById(R.id.edtGoalName);



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
    private int adjustNumberPicker(NumberPicker picker){
        switch (picker.getValue()){
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 20;
            // add more cases as needed
            default:
                return 0;
        }
    }
}