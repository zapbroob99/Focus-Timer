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

import com.example.focustimer.user.UserClass;
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

                setGoalInputScreen();
                getGoal();

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create an ArrayList for your goals
        ArrayList<SelectGoalHelperClass> goals = new ArrayList<>();

        // Add items manually at both edges
        // First item at the left edge
        SelectGoalHelperClass leftEdgeItem = new SelectGoalHelperClass("Click to Add new goal");
        // Set properties for the left edge item (e.g., title, description, etc.)
        // Add any other properties as needed
        goals.add(leftEdgeItem);

        // Add your dynamically generated goals (you might fetch them from a data source)
        UserClass.setRecyclerViewCards(goals);

        // Last item at the right edge
        SelectGoalHelperClass rightEdgeItem = new SelectGoalHelperClass("Click to add new goal");
        // Set properties for the right edge item

        // Add any other properties as needed
        goals.add(rightEdgeItem);

        // Create and set the adapter
        adapter = new SelectGoalAdapter(goals);
        recyclerView.setAdapter(adapter);

        // Add the scroll listener
        CenterItemScrollListener scrollListener = new CenterItemScrollListener(recyclerView, this);
        recyclerView.addOnScrollListener(scrollListener);
    }


    private void getGoal(){


        EditText firstInputEditText = findViewById(R.id.edtGoalName);
        String firstInput = firstInputEditText.getText().toString(); //this will hold the goal's
        //sending variables to focus activity
        FocusScreen focusScreen=new FocusScreen();
        Intent i = new Intent(getApplicationContext(),FocusScreen.class);
        startActivity(i);
        overridePendingTransition(0, 0);
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


    }
    private void setGoalInputScreen(){

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