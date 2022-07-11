package com.naplastech.tambalin.UserScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterP extends AppCompatActivity {
    public Boolean cekData = false;
    public Integer tambahData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_p);

        String role = getIntent().getStringExtra("role");
        Button btnRegis = findViewById(R.id.buttonRegisterP);
        final EditText editNamaP = findViewById(R.id.editNamaP);
        final EditText editTelp = findViewById(R.id.editNomorP);
        final EditText editKotaP = findViewById(R.id.editKotaP);
        final EditText editPassP = findViewById(R.id.editPassP);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer jenis_ban = 0;



                // ==== CEK DATA TIDAK KOSONG ====
                if (editNamaP.length() > 3 & editTelp.length() > 10 & editKotaP.length() > 2){
                    cekData = true;
                }else{
                    cekData = false;
                    Toast toast = Toast.makeText(getApplicationContext(),"Pastikan data terisi dengan benar!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                // ==== END OF CEK DATA TIDAK KOSONG ====

                // ==== CEK PANJANG PASSWORD ====
                if (cekData == true){
                    if (editPassP.length() >= 6){
                        cekData = true;
                    }else{
                        cekData = false;
                        Toast toast = Toast.makeText(getApplicationContext(),"Panjang password minimal 6 karakter",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                // ==== END OF CEK PANJANG PASSWORD ====

                /* CEK JENIS BAN
                if (cekData == true){
                    if (pil_non_tubles.isChecked() == true & pil_tubles.isChecked() == false){
                        jenis_ban = 0;
                        cekData = true;
                    }else if (pil_non_tubles.isChecked() == false & pil_tubles.isChecked() == true){
                        jenis_ban = 1;
                        cekData = true;
                    }else if (pil_non_tubles.isChecked() == true & pil_tubles.isChecked() == true){
                        jenis_ban = 2;
                        cekData = true;
                    }else{
                        cekData = false;
                        Toast toast = Toast.makeText(getApplicationContext(),"Pilih jenis ban!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                ==== END OF CEK JENIS BAN ==== */

                // ==== CEK NOMOR HP ====
                if (cekData == true){
                    ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
                    Call<CekNomor> call = apiInt.cekNomor(editTelp.getText().toString());
                    call.enqueue(new Callback<CekNomor>() {
                        @Override
                        public void onResponse(Call<CekNomor> call, Response<CekNomor> response) {
                            if (response.body().getStatus() == 0){
                                // ==== KIRIM DATA ====
                                addData(editPassP.getText().toString(),editTelp.getText().toString(),editNamaP.getText().toString(),editKotaP.getText().toString());
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
        Integer role = 0;
        ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
        Call<InsertDataP> call = apiInt.registrasiPengendara(addnama,addkota,addtelp,addpassword, role);
        call.enqueue(new Callback<InsertDataP>() {
            @Override
            public void onResponse(Call<InsertDataP> call, Response<InsertDataP> response) {
                if (response.body().getStatus() == 1){
                    startActivity(new Intent(RegisterP.this, LoginScreen.class));
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
