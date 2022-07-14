package com.naplastech.tambalin.UserScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.naplastech.tambalin.MainScreen.HomeM;
import com.naplastech.tambalin.MainScreen.HomeP;
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_screen);
        EditText txttelp = findViewById(R.id.editTelp);
        EditText txtpass = findViewById(R.id.editPass);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txttelp.getText().toString().isEmpty() || txtpass.getText().toString().isEmpty()){
                    Toast toasti = Toast.makeText(getApplicationContext(),"Username atau Password tidak boleh kosong",Toast.LENGTH_SHORT);
                    toasti.show();
                }else{
                    cekdata(txttelp.getText().toString(),txtpass.getText().toString());
                }
            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
                builder.setTitle("Daftar Akun");
                builder.setMessage("Kamu bisa memilih mendaftar sebagai Mitra dan Pengendara");
                builder.setPositiveButton("Pengendara",new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        Intent intent = new Intent(LoginScreen.this, RegisterP.class);
                        intent.putExtra("role","pengendara");
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNeutralButton("Batal",new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Mitra",new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {

                        Intent intent = new Intent(LoginScreen.this, RegisterM.class);
                        intent.putExtra("role","Mitra");
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void cekdata(String t,String p){
        ApiInterface apiint = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginRequest> call = apiint.login(t,p);
        call.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.body().getStatus() == 1){
                    if (response.body().getRole() == 0) {
                        Intent pengendara = new Intent(LoginScreen.this, HomeP.class);
                        pengendara.putExtra("user_id",response.body().getId_user());
                        startActivity(pengendara);
                        finish();
                    }else if (response.body().getRole() == 1){
                        Intent mitra = new Intent(LoginScreen.this, HomeM.class);
                        mitra.putExtra("user_id",response.body().getId_user());
                        startActivity(mitra);
                        finish();
                    }
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Login Gagal, Username atau Password salah",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Terjadi kesalahan " + t.getMessage(),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}



