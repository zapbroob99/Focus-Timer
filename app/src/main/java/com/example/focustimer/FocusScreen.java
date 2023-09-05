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

import com.example.focustimer.user.UserClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class FocusScreen extends AppCompatActivity {
    private String goalName;
    private int goalDuration;
    MediaPlayer mMediaPlayer;
    Handler mHandler;
    RelativeLayout rl;
    ProgressBar mProgressBar;
    private int mTimerValue = 0;
    private int mTargetTime;
    private int currentFocus;
    private boolean cutSession=false;
    TextView progressText;
    Button btnEnd;
    public FocusScreen(){
        //empty constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_screen);

        //hooks
        rl = findViewById(R.id.relativeProgressBar); //relativelayout
        mProgressBar = findViewById(R.id.progressBar); //circle timer
        btnEnd=findViewById(R.id.btnEnd);

        mHandler=new Handler();


        mMediaPlayer = MediaPlayer.create(FocusScreen.this, R.raw.beep);
        startTimer(); //starting timer


        //end button function
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mTimerValue=mTargetTime;
                //TODO: iplement cut session
                cutSession=true;
            }
        });


    }
    private void startTimer(){
        mTargetTime=UserClass.getCurrentGoal().getGoalDuration();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mTimerValue++;
                updateTimerText();
                if (mTimerValue < mTargetTime&&cutSession==false) {
                    startTimer();
                }
                else if(cutSession==true){
                    Toast.makeText(FocusScreen.this, "You didnt complete your session", Toast.LENGTH_LONG).show();
                    cutSession=true;
                    UserClass.updateFocusTimeOnDB(mTargetTime);
                    resetTimer();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent); //TODO: add saved preferences to store session and return
                }
                else {
                    handleSessionSuccessfull();
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

    private void CutSession(){
        //TODO: iplement cut session

    }

    private void resetTimer() {
        mTimerValue = 0;
    }

    private void handleSessionSuccessfull(){
        mMediaPlayer.start();
        UserClass.updateFocusTimeOnDB(mTargetTime);
        Toast.makeText(FocusScreen.this, "Congratulations! You have achieved your goal!", Toast.LENGTH_LONG).show();
        resetTimer();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent); //TODO: add saved preferences to store session and return
    }

    private void addFocusTimeToDB(){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        reference.child(UserClass.getPhoneNo()).child("focustime").setValue(ServerValue.increment(mTargetTime/60));
    }

}