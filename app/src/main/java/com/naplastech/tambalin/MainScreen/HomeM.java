package com.naplastech.tambalin.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class HomeM extends AppCompatActivity {

    //eTextView OrderCount = findViewById(R.id.OrderCount);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_m);

        TextView NameMitra = findViewById(R.id.nameMitra);

        Intent intent = getIntent();
        String namaMitra= intent.getStringExtra("namaMitra");

        NameMitra.setText(namaMitra);
        //pesanan(NameMitra.getText().toString());
    }

}