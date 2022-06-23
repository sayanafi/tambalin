package com.naplastech.tambalin.UserScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naplastech.tambalin.MainScreen.HomeM;
import com.naplastech.tambalin.MapScreen.MapScreen;
import com.naplastech.tambalin.R;

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
                    Toast toasti = Toast.makeText(getApplicationContext(),"Data Kosong",Toast.LENGTH_SHORT);
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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://tambalin-79727-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = firebaseDatabase.getReference("pengendara").child(t);
        DatabaseReference databaseReferences = firebaseDatabase.getReference("Mitra").child(t);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String dbpass = snapshot.child("password").getValue(String.class);
                //final String dbpass =snapshot.getValue(String.class);
                if (p.equals(dbpass)) {
                    startActivity(new Intent(LoginScreen.this, MapScreen.class));
                }else {
                    databaseReferences.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final String dbpassword = snapshot.child("password").getValue(String.class);
                            //final String dbpass =snapshot.getValue(String.class);
                            if (p.equals(dbpassword)) {
                                startActivity(new Intent(LoginScreen.this, HomeM.class));
                            }else {
                                Toast toast = Toast.makeText(getApplicationContext(),"password salah",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Database Error "+error,Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(),"Database Error "+error,Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    private void kirimbiodata(String t){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(t).child("nama");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String nama =snapshot.getValue(String.class);
                Intent intent = new Intent(LoginScreen.this, MapScreen.class);
                intent.putExtra("nama",nama);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(),"Berhasil Login",Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(),"Database Error "+error,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}



