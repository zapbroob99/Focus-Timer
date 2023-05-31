package com.example.focustimer.loginsignup;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.focustimer.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {
    Button btnUpdate;
    TextInputLayout newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        btnUpdate=findViewById(R.id.set_new_password_btn);
        newPassword=findViewById(R.id.new_password);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from fields
                String _newPassword = newPassword.getEditText().getText().toString().trim();
                String _phoneNumber = getIntent().getStringExtra("phoneNo");

                //Update data in Firebase
                DatabaseReference reference = FirebaseDatabase.getInstance("https://city-guide-a5ee6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
                reference.child(_phoneNumber).child("password").setValue(_newPassword);
                startActivity(new Intent(getApplicationContext(),ForgerPasswordSuccessMessage.class));
                finish();

            }
        });
    }

}