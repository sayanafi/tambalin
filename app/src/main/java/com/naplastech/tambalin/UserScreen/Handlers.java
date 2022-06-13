package com.naplastech.tambalin.UserScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.usermodels;

import java.util.Timer;
import java.util.TimerTask;

public class Handlers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlers);
//        CheckBox setuju = findViewById(R.id.cbsyarat);
//        Button fixSetuju = findViewById(R.id.btnmasuk);

        String nama = getIntent().getStringExtra("nama");
        String telp = getIntent().getStringExtra("notelp");
        String alamat = getIntent().getStringExtra("alamat");
//        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        adddata(password,nama,telp,alamat);

//        fixSetuju.setOnClickListener(view -> {
//            if (setuju.isChecked()) {
//                adddata(email,password,nama,telp,alamat);
//            }else {
//                Toast notif = Toast.makeText(getApplicationContext(),"Mohon Di Setujui Syarat Dan Kesetujuannya", Toast.LENGTH_SHORT);
//                notif.show();
//            }
//        });
    }

    private void adddata(String addpassword, String addnama, String addtelp, String addalamat){
        usermodels akun   = new usermodels();
        akun.setPassword(addpassword);
        akun.setNama(addnama);
        akun.setNotelp(addtelp);
        akun.setAlamat(addalamat);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(addtelp);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(akun);
                Toast toast = Toast.makeText(getApplicationContext(),"Telah Berhasil Daftar",Toast.LENGTH_SHORT);
                toast.show();
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Handlers.this, LoginScreen.class));
                    }
                };
                timer.schedule(timerTask,2000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(),"Database Error",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
