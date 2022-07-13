package com.naplastech.tambalin.MainScreen.Mitra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.EditBengkel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiM extends AppCompatActivity {
    public Integer jenis_ban = 0;
    public Integer layanan_tambahan = 0;
    public Boolean cek_data = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lokasi_m);

        Integer user_id = getIntent().getIntExtra("user_id",0);

        final EditText nama_bengkel = findViewById(R.id.editTempatM);
        final EditText alamat = findViewById(R.id.editAlamat);
        final CheckBox non_tubles = findViewById(R.id.pilNonTublesM);
        final CheckBox tubles = findViewById(R.id.pilTublesM);
        final CheckBox ban_dalam = findViewById(R.id.pilLayananBDM);
        final CheckBox ban_luar = findViewById(R.id.pilLayananBLM);
        Button btn = findViewById(R.id.btnTambahLokasi);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CEK INPUT
                if (nama_bengkel.length() > 5 & alamat.length() > 10){
                    cek_data = true;
                }else{
                    cek_data = false;
                    Toast toast = Toast.makeText(getApplicationContext(),"Silahkan isi nama tempat dan alamat lengkap!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                // END OF CEK INPUT

                // CEK TUBLES
                if (cek_data == true){
                    if (non_tubles.isChecked() == true & tubles.isChecked() == false){
                        jenis_ban = 0;
                        cek_data = true;
                    }else if(non_tubles.isChecked() == false & tubles.isChecked() == true){
                        jenis_ban = 1;
                        cek_data = true;
                    }else if(non_tubles.isChecked() == true & tubles.isChecked() == true){
                        jenis_ban = 2;
                        cek_data = true;
                    }else if(non_tubles.isChecked() == false & tubles.isChecked() == false){
                        cek_data = false;
                        Toast toast = Toast.makeText(getApplicationContext(),"Silahkan pilih jenis ban",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                // END OF CEK TUBLES

                // CEK LAYANAN TAMBAHAN
                if (cek_data == true){
                    if (ban_dalam.isChecked() == false & ban_luar.isChecked() == false){
                        layanan_tambahan = 0;
                        cek_data = true;
                    }else if(ban_dalam.isChecked() == true & ban_luar.isChecked() == false){
                        layanan_tambahan = 1;
                        cek_data = true;
                    }else if(ban_dalam.isChecked() == false & ban_luar.isChecked() == true){
                        layanan_tambahan = 2;
                        cek_data = true;
                    }else if(ban_dalam.isChecked() == true & ban_luar.isChecked() == true){
                        layanan_tambahan = 3;
                        cek_data = true;
                    }
                }
                // END OF LAYANAN TAMBAHAN

                if (cek_data == true){
                    ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
                    Call<EditBengkel> call = apiInt.editBengkel(user_id, nama_bengkel.getText().toString(), alamat.getText().toString(), jenis_ban, layanan_tambahan);
                    call.enqueue(new Callback<EditBengkel>() {
                        @Override
                        public void onResponse(Call<EditBengkel> call, Response<EditBengkel> response) {
                            if (response.body().getStatus() == 1){
                                Toast toast = Toast.makeText(getApplicationContext(),"Data bengkel berhasil ditambahkan",Toast.LENGTH_SHORT);
                                toast.show();
                            }else{
                                Toast toast = Toast.makeText(getApplicationContext(),"Tambah data bengkel gagal! Silahkan periksa kembali data yang dimasukkan",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EditBengkel> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada server : " + t.getMessage(),Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        });


    }
}