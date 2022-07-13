package com.naplastech.tambalin.MainScreen.Mitra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;

public class ProfileM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_m);

        Button btnlokasi = findViewById(R.id.btnTambahLokasi);

        String nama = getIntent().getStringExtra("nama");
        Integer user_id = getIntent().getIntExtra("user_id",0);
        final TextView nama_mitra = findViewById(R.id.nameMitra);
        nama_mitra.setText(nama);

        btnlokasi.setOnClickListener((View.OnClickListener) v -> {
            Intent tmbhlokasi = new Intent(ProfileM.this, LokasiM.class);
            tmbhlokasi.putExtra("user_id", user_id);
            startActivity(tmbhlokasi);
        });
    }
}