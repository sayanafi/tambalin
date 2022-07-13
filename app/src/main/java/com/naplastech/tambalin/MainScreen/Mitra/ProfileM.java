package com.naplastech.tambalin.MainScreen.Mitra;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class ProfileM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_m);

        String nama = getIntent().getStringExtra("nama");
        final TextView nama_mitra = findViewById(R.id.nameMitra);
        nama_mitra.setText(nama);
    }
}