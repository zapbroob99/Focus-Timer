package com.example.focustimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.focustimer.loginsignup.LoginScreen;

public class LoadingScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 1600;
    Animation topAnim, bottomAnim;
    ImageView img_logo;
    TextView tv_name, tv_slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bot_animation);
        img_logo=findViewById(R.id.iv_logo);
        tv_name=findViewById(R.id.tv_name);
        tv_slogan=findViewById(R.id.tv_slogan);

        img_logo.setAnimation(topAnim);
        tv_name.setAnimation(bottomAnim);
        tv_slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingScreen.this, LoginScreen.class);
                Pair[] pairs = new Pair[2];
                pairs[0]=new Pair<View,String>(img_logo,"logo_image");
                pairs[1]=new Pair<View,String>(tv_name,"logo_text");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoadingScreen.this,pairs);
                startActivity(intent,options.toBundle());

            }
        },SPLASH_SCREEN);
    }
}