package com.naplastech.tambalin.FirstScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 5000;

    TextView titlesplash;
    TextView descsplash;
    TextView powered;

    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        titlesplash = findViewById(R.id.titlesplash);
        descsplash = findViewById(R.id.descsplash);
        powered = findViewById(R.id.powered);

        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        titlesplash.setAnimation(sideAnim);
        descsplash.setAnimation(sideAnim);
        powered.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            Intent intent = new Intent(SplashScreen.this, OnboardingScreen.class);
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }
}