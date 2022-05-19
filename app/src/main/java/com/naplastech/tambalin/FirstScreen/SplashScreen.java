package com.naplastech.tambalin.FirstScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,OnboardingScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }
}