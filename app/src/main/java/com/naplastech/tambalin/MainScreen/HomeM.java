package com.naplastech.tambalin.MainScreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class HomeM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_m);
    }
}