package com.naplastech.tambalin.UserScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.CekNomor;
import com.naplastech.tambalin.api.InsertDataP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterM extends AppCompatActivity {
    public Boolean cekData = false;
    public Integer tambahData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_m);

        ImageView btnback = findViewById(R.id.back);

        Button btnRegis = findViewById(R.id.buttonRegisterM);
        final EditText nama = findViewById(R.id.editNamaM);
        final EditText no_hp = findViewById(R.id.editNomorM);
        final EditText kota = findViewById(R.id.editKotaM);
        final EditText password = findViewById(R.id.editPassM);

        btnback.setOnClickListener((View.OnClickListener) v -> {
            Intent login = new Intent(RegisterM.this, LoginScreen.class);
            startActivity(login);
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==== CEK DATA TIDAK KOSONG ====
                if (nama.length() > 3 & no_hp.length() > 10 & kota.length() > 2){
                    cekData = true;
                }else{
                    cekData = false;
                    Toast toast = Toast.makeText(getApplicationContext(),"Pastikan data terisi dengan benar!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                // ==== END OF CEK DATA TIDAK KOSONG ====

                // ==== CEK PANJANG PASSWORD ====
                if (cekData == true){
                    if (password.length() >= 6){
                        cekData = true;
                    }else{
                        cekData = false;
                        Toast toast = Toast.makeText(getApplicationContext(),"Panjang password minimal 6 karakter",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                // ==== END OF CEK PANJANG PASSWORD ====

                // ==== CEK NOMOR HP ====
                if (cekData == true){
                    ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
                    Call<CekNomor> call = apiInt.cekNomor(no_hp.getText().toString());
                    call.enqueue(new Callback<CekNomor>() {
                        @Override
                        public void onResponse(Call<CekNomor> call, Response<CekNomor> response) {
                            if (response.body().getStatus() == 0){
                                // ==== KIRIM DATA ====
                                addData(password.getText().toString(),no_hp.getText().toString(),nama.getText().toString(),kota.getText().toString());
                                // ==== END OF KIRIM DATA ====
                            }else if(response.body().getStatus() == 1){
                                Toast toast = Toast.makeText(getApplicationContext(),"Nomor HP sudah terdaftar!",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CekNomor> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Terjadi kesalahan : " + t.getMessage(),Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
                // ==== END OF CEK NOMOR HP ====
            }
        });
    }

    private void addData(String addpassword, String addtelp, String addnama, String addkota){
        Integer role = 1;
        ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
        Call<InsertDataP> call = apiInt.registrasiPengendara(addnama,addkota,addtelp,addpassword, role);
        call.enqueue(new Callback<InsertDataP>() {
            @Override
            public void onResponse(Call<InsertDataP> call, Response<InsertDataP> response) {
                if (response.body().getStatus() == 1){
                    startActivity(new Intent(RegisterM.this, LoginScreen.class));
                    Toast toast = Toast.makeText(getApplicationContext(),"Registrasi Berhasil, silahkan login",Toast.LENGTH_SHORT);
                    toast.show();
                }else if (response.body().getStatus() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),"Registrasi gagal, silahkan cek kembali data anda",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<InsertDataP> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Terjadi kesalahan " + t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

