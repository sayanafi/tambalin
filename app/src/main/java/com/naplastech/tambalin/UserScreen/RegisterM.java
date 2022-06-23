package com.naplastech.tambalin.UserScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naplastech.tambalin.MapScreen.MapScreen;
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.usermodels;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterM extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_m);
        String role = getIntent().getStringExtra("role");
        Button btnRegis = findViewById(R.id.buttonRegisterM);
        final EditText editNamaM =findViewById(R.id.editNamaM);
        final EditText ediTelpM =findViewById(R.id.editNomorM);
        final EditText editKotaM =findViewById(R.id.editKotaM);
        final EditText editPassM = findViewById(R.id.editPassM);
        btnRegis.setOnClickListener(view ->  addData(editPassM.getText().toString(),ediTelpM.getText().toString(),editNamaM.getText().toString()));
    }


    private void cekNomor(){
        final EditText editNoM =findViewById(R.id.editNomorM);
        if (editNoM.getText().toString().length()>=10 && editNoM.getText().toString().length()<=14)
        {
            cekValid();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Nomor Tidak Valid", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void cekValid(){
        final EditText editNoTelpM =findViewById(R.id.editNomorM);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Mitra").child(editNoTelpM.getText().toString());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String dbtelp =snapshot.getValue(String.class);
                if (editNoTelpM.getText().toString().equals(dbtelp)){
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
        final EditText editNamaM =findViewById(R.id.editNamaM);
        final EditText editNoM =findViewById(R.id.editNomorM);
        final EditText editKotaM =findViewById(R.id.editKotaM);
        final EditText editPassM = findViewById(R.id.editPassM);
        if (editPassM.getText().toString().length()>=8){
            Intent intent = new Intent(RegisterM.this, com.naplastech.tambalin.UserScreen.Handlers.class);
            intent.putExtra("nama",editNamaM.getText().toString());
            intent.putExtra("notelp",editNoM.getText().toString());
            intent.putExtra("alamat",editKotaM.getText().toString());
            intent.putExtra("password",editPassM.getText().toString());
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Password Minimal 8 Karakter", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void addData(String addpassword, String addtelp, String addnama){
        usermodels akun = new usermodels();
        akun.setPassword(addpassword);
        akun.setNotelp(addtelp);
        akun.setNama(addnama);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://tambalin-79727-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = firebaseDatabase.getReference("Mitra").child(addtelp);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    databaseReference.setValue(akun);
                    Toast.makeText(getApplicationContext(),"Telah Berhasil Daftar",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterM.this, LoginScreen.class));
                }catch (Exception i){
                    Toast.makeText(getApplicationContext(),"Maaf Error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(),"Database Error",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


}

