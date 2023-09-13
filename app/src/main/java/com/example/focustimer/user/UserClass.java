package com.example.focustimer.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.focustimer.SelectGoalHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

public final class UserClass {
    protected static String userName,email,fullName,gender,date,phoneNo,password;
    public static Goal currentGoal;
    public static List<Goal> goalsList=new ArrayList<>();
    public static int totalFocusTime;

    private static String dbReference="https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/";

    public UserClass() {
    }
    public static void getAndSetUserDataFromDB(String _username, String _fullName, String _email, String _phoneNo, String _date, String _gender,
                                               int _focustime, String _password){

        phoneNo=_phoneNo;
        fullName=_fullName;
        userName=_username;
        gender=_gender;
        email=_email;
        date=_date;
        gender=_gender;
        password=_password;
        totalFocusTime=_focustime;
    }

    public static void updateTotalFocusTimeOnDB(int number){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://focus-timer-8d9d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        reference.child(UserClass.getPhoneNo()).child("focustime").setValue(ServerValue.increment(number/60));
        totalFocusTime+=(number/60);
    }

    public static  Goal getCurrentGoal() {
        return currentGoal;
    }

    public static void setCurrentGoal(Goal currentGoal) {
        UserClass.currentGoal = currentGoal;
    }

    public static void getAndSetGoalInfoFromDB(DataSnapshot snapshot, String _completePhoneNumber, Context context){
        //TODO: implement
        DataSnapshot goalsSnapshot = snapshot.child(_completePhoneNumber).child("goals");
        for (DataSnapshot goalSnapshot : goalsSnapshot.getChildren()) {
            String goalName = goalSnapshot.getKey();
            int dailyTime = goalSnapshot.child("daily_time").getValue(Integer.class);
            int total_time = goalSnapshot.child("totaltime").getValue(Integer.class);
            int focus_duration=goalSnapshot.child("focus_duration").getValue(Integer.class);
            UserClass.goalsList.add(new Goal(goalName,total_time,dailyTime,focus_duration)); //retrieve goal info from db and add into goalslist

        }

    }
    public static void setRecyclerViewCards(ArrayList<SelectGoalHelperClass> goals){
        for (Goal goal : goalsList) {
            // Access and work with the individual goal object
            goals.add(new SelectGoalHelperClass(goal.getName()));

        }
        // Last item at the right edge
        SelectGoalHelperClass rightEdgeItem = new SelectGoalHelperClass("Click to add new goal");
        // Set properties for the right edge item

        // Add any other properties as needed
        goals.add(rightEdgeItem);

    }
    public static Dialog adjustGoalSettings(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Create a LinearLayout to hold the dialog content
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a SeekBar and add it to the LinearLayout
        SeekBar seekBar = new SeekBar(context);
        seekBar.setMax(120); // Set the maximum value for the SeekBar
        seekBar.setProgress(currentGoal.getGoalDuration()); // Set the initial progress
        layout.addView(seekBar);

        // Create a TextView to display the SeekBar value
        final TextView seekBarValueTextView = new TextView(context);
        seekBarValueTextView.setText("Focus Session Duration: " + currentGoal.getGoalDuration()+"Minutes");
        layout.addView(seekBarValueTextView);

        // Set a SeekBar change listener to update the TextView
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValueTextView.setText("Focus Session Duration: " + progress+" Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        builder.setView(layout); // Set the custom layout to the dialog


        builder.setTitle(UserClass.getCurrentGoal().getName());


        // Add an OK button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  the OK button click
                currentGoal.updateFocusDuration(seekBar.getProgress()*60);
                dialog.dismiss(); // Close the dialog if needed
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss(); // Close the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // You can add further customization or button actions here if needed

        return dialog;
    }

    public static Dialog addNewGoalDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Create a LinearLayout to hold the dialog content
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create an EditText for goal name
        EditText goalNameEditText = new EditText(context);
        goalNameEditText.setHint("Goal Name"); // Set a hint for the goal name input
        layout.addView(goalNameEditText);

        // Create a SeekBar for goal duration
        SeekBar goalDurationSeekBar = new SeekBar(context);
        goalDurationSeekBar.setMax(120); // Set the maximum value for the SeekBar
        goalDurationSeekBar.setProgress(30); // Set an initial progress value
        layout.addView(goalDurationSeekBar);

        // Create a TextView to display the SeekBar value
        final TextView seekBarValueTextView = new TextView(context);
        seekBarValueTextView.setText("Goal Duration: " + goalDurationSeekBar.getProgress() + " Minutes");
        layout.addView(seekBarValueTextView);

        // Set a SeekBar change listener to update the TextView
        goalDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValueTextView.setText("Goal Duration: " + progress + " Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        builder.setView(layout); // Set the custom layout to the dialog
        builder.setTitle("Add New Goal");

        // Add an "Add" button
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String goalName = goalNameEditText.getText().toString();
                int goalDuration = goalDurationSeekBar.getProgress();

                if (!goalName.isEmpty()) {
                    // Create a new goal with the entered name and duration
                    Goal newGoal = new Goal(goalName,0,0,goalDuration*60);
                    UserClass.goalsList.add(goalsList.size()-1,newGoal);

                }
                dialog.dismiss(); // Close the dialog
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // You can add further customization or validation logic here if needed

        return dialog;
    }




    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserClass.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserClass.email = email;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        UserClass.fullName = fullName;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        UserClass.gender = gender;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        UserClass.date = date;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(String phoneNo) {
        UserClass.phoneNo = phoneNo;
    }

    public static int getTotalFocusTime() {
        return totalFocusTime;
    }

    public static void setTotalFocusTime(int totalFocusTime) {
        UserClass.totalFocusTime = totalFocusTime;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserClass.password = password;
    }


}
