package com.example.focustimer;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationBar {
    public static BottomNavigationView bottomNavigationView;
    public static void setupBottomNavigationBar(Activity activity, int bottomNavId) {
        BottomNavigationView bottomNavigationView = activity.findViewById(bottomNavId);
        bottomNavigationView.setItemBackground(null);
        // Manually set the selected item based on the current activity
        if (activity instanceof MainActivity) {
            bottomNavigationView.setSelectedItemId(R.id.dashboard);
        } else if (activity instanceof Stats) {
            bottomNavigationView.setSelectedItemId(R.id.stats);
        } else if (activity instanceof Profile) {
            bottomNavigationView.setSelectedItemId(R.id.profile);
        }
        // Remove the gray shadow or highlight
        bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.dashboard:
                        if (activity instanceof MainActivity) {
                            return true; // Already on the selected activity, no need to create an intent
                        }

                        intent = new Intent(activity, MainActivity.class);
                        break;
                    case R.id.stats:
                        if (activity instanceof Stats) {
                            return true; // Already on the selected activity, no need to create an intent
                        }

                        intent = new Intent(activity, Stats.class);
                        break;
                    case R.id.profile:
                        if (activity instanceof Profile) {
                            return true; // Already on the selected activity, no need to create an intent
                        }

                        intent = new Intent(activity, Profile.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + id);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the back stack
                activity.startActivity(intent);

                // Clear the focus to remove the touch animation
                bottomNavigationView.postDelayed(() -> bottomNavigationView.clearFocus(), 300);

                return true;
            }
        });
    }
}
