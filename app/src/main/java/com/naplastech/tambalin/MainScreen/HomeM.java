package com.naplastech.tambalin.MainScreen;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        Integer user_id = getIntent().getIntExtra("user_id", 0);
        final TextView nama_mitra = findViewById(R.id.nameMitra);

        Toast toast = Toast.makeText(getApplicationContext(),"Halo : " + user_id,Toast.LENGTH_SHORT);
        toast.show();

        ApiInterface apiint = ApiClient.getClient().create(ApiInterface.class);
        Call<Mitra> call = apiint.dataMitra(user_id);
        call.enqueue(new Callback<Mitra>() {
            @Override
            public void onResponse(Call<Mitra> call, Response<Mitra> response) {
                if (response.body().getStatus() == 1){
                    nama_mitra.setText(response.body().getNama());
                }
            }

            @Override
            public void onFailure(Call<Mitra> call, Throwable t) {

            }
        });


    }

}