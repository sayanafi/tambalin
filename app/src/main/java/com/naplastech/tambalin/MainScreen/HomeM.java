package com.naplastech.tambalin.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.MainScreen.Mitra.ProfileM;
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.Mitra;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeM extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_m);

        ImageView btnprofile = findViewById(R.id.profileMenu);

        Integer user_id = getIntent().getIntExtra("user_id", 0);
        final TextView nama_mitra = findViewById(R.id.nameMitra);
        final TextView jum_pesanan = findViewById(R.id.jum_pesanan);
        final TextView penilaian = findViewById(R.id.penilaian);


        btnprofile.setOnClickListener((View.OnClickListener) v -> {
            Intent profil = new Intent(HomeM.this, ProfileM.class);
            profil.putExtra("nama", nama_mitra.getText());
            profil.putExtra("user_id", user_id);
            startActivity(profil);
        });


        ApiInterface apiint = ApiClient.getClient().create(ApiInterface.class);
        Call<Mitra> call = apiint.dataMitra(user_id);
        call.enqueue(new Callback<Mitra>() {
            @Override
            public void onResponse(Call<Mitra> call, Response<Mitra> response) {
                if (response.body().getStatus() == 1){
                    nama_mitra.setText(response.body().getNama());
                    jum_pesanan.setText(response.body().getJum_pesanan().toString());
                    penilaian.setText(response.body().getPenilaian().toString());
                }
            }

            @Override
            public void onFailure(Call<Mitra> call, Throwable t) {

            }
        });


    }

}