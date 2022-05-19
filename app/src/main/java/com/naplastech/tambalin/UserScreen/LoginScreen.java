package com.naplastech.tambalin.UserScreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_screen);
    }
}