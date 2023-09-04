package com.example.focustimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import com.shawnlin.numberpicker.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import androidx.recyclerview.widget.PagerSnapHelper;

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
    RelativeLayout rl;
    NumberPicker durationPicker;

    //define recyclerview objects
    RecyclerView recyclerView;

    SelectGoalAdapter adapter;

    BottomNavigationView bottomNavigationView;
    int focusTime;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromLogin();

        //get recyclerview
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView();

        //BOTTOM NAV VİEW
        bottomNavigationView=findViewById(R.id.bottomnav);
        BottomNavigationBar.setupBottomNavigationBar(this, R.id.bottomnav);

        //DEFINING SOME UI ELEMENTS
        setGoalButton = findViewById(R.id.id_setGoal); //button for setting a goal
        setGoalWarning = findViewById(R.id.id_txt_setGoal);


        //ASSIGNING TIMER ELEMENTS
        rl = findViewById(R.id.relativeProgressBar); //relativelayout
        mProgressBar = findViewById(R.id.progressBar); //circle timer
        setGoalButton.setOnClickListener(new View.OnClickListener() { //what happens whe you click
            //set goal button
            @Override
            public void onClick(View v) {
                durationPicker =(NumberPicker) findViewById(R.id.duration_picker);
                durationPicker.setMinValue(1);
                durationPicker.setMaxValue(4);
                durationPicker.setDisplayedValues(new String[] {"20 min", "30 min","45 min","60 min",});
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
        PagerSnapHelper snapHelper= new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

    }




    private void recyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<SelectGoalHelperClass> goals = new ArrayList<>();
        goals.add(new SelectGoalHelperClass("Dersslerrr"));
        goals.add(new SelectGoalHelperClass("Tenis"));
        goals.add(new SelectGoalHelperClass("Staj"));
        goals.add(new SelectGoalHelperClass("Kitap"));
        adapter=new SelectGoalAdapter(goals);
        recyclerView.setAdapter(adapter);
        CenterItemScrollListener scrollListener = new CenterItemScrollListener(recyclerView);
        recyclerView.addOnScrollListener(scrollListener);

    }

    private void getGoal(){


        EditText firstInputEditText = findViewById(R.id.edtGoalName);
        String firstInput = firstInputEditText.getText().toString(); //this will hold the goal's
        mTargetTime=adjustNumberPicker(durationPicker)*60;
        //sending variables to focus activity
        FocusScreen focusScreen=new FocusScreen(firstInput,adjustNumberPicker(durationPicker));
        Intent i = new Intent(getApplicationContext(),FocusScreen.class);
        i.putExtra("duration", adjustNumberPicker(durationPicker)*60);
        i.putExtra("name", firstInput);
        startActivity(i);
        setTimerScreen();
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
        recyclerView.setVisibility(View.GONE);
    }

    private int adjustNumberPicker(NumberPicker picker){
        switch (picker.getValue()){
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 45;
            case 4:
                return 60;
            // add more cases as needed
            default:
                return 0;
        }
    }
    private void getDataFromLogin(){ //gets user info from login
        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("phoneNo");
        focusTime =  intent.getIntExtra("focustime",0);
    }

}