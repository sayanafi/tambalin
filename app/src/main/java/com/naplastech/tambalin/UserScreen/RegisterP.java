package com.naplastech.tambalin.UserScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.PengendaraReq;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterP extends AppCompatActivity {


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
        btnRegis.setOnClickListener(view ->  addData(editPassP.getText().toString(),editTelp.getText().toString(),editNamaP.getText().toString(),editKotaP.getText().toString()));
    }


    private void cekNomor(){
        final EditText editNoP =findViewById(R.id.editNomorP);
        if (editNoP.getText().toString().length()>=10 && editNoP.getText().toString().length()<=14)
        {
            cekValid();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Nomor Tidak Valid", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void cekValid(){
        final EditText editNoTelpP =findViewById(R.id.editNomorP);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("pengendara").child(editNoTelpP.getText().toString());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String dbtelp =snapshot.getValue(String.class);
                if (editNoTelpP.getText().toString().equals(dbtelp)){
                    Toast toast = Toast.makeText(getApplicationContext(),"Nomor Telah Terdaftar !",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    //cekPassword();
                    Toast toast = Toast.makeText(getApplicationContext(),"nyee",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(),"Database Error ",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void cekPassword(){
        final EditText editNamaP =findViewById(R.id.editNamaP);
        final EditText editNoP =findViewById(R.id.editNomorP);
        final EditText editKotaP =findViewById(R.id.editKotaP);
        final EditText editPassP = findViewById(R.id.editPassP);
        if (editPassP.getText().toString().length()>=8){
            Intent intent = new Intent(RegisterP.this, com.naplastech.tambalin.UserScreen.Handlers.class);
            intent.putExtra("nama",editNamaP.getText().toString());
            intent.putExtra("notelp",editNoP.getText().toString());
            intent.putExtra("alamat",editKotaP.getText().toString());
            intent.putExtra("password",editPassP.getText().toString());
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Password Minimal 8 Karakter", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void addData(String addpassword, String addtelp, String addnama, String addkota){
        Integer jenis_ban = 1;
        Integer role = 0;
        ApiInterface apiInt = ApiClient.getClient().create(ApiInterface.class);
        Call<PengendaraReq> call = apiInt.registrasiPengendara(addnama,addkota,jenis_ban,addtelp,addpassword, role);
        call.enqueue(new Callback<PengendaraReq>() {
            @Override
            public void onResponse(Call<PengendaraReq> call, Response<PengendaraReq> response) {
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
            public void onFailure(Call<PengendaraReq> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Terjadi kesalahan " + t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


}
