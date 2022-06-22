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
import com.naplastech.tambalin.R;
import com.naplastech.tambalin.usermodels;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterM extends AppCompatActivity {

//    long totalSize = 0;
//    String user = "";
//    String password = "";
//    String kota = "";
//    String telp = "";
//    String usaha = "";
//    EditText txtuser;
//    EditText txtpass;
//    EditText txtkota;
//    EditText txttelp;
//    EditText txtnamausaha;
//
//    ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_p);
        String role = getIntent().getStringExtra("role");
        Button btnRegis = findViewById(R.id.buttonRegisterP);
        final EditText editNamaP =findViewById(R.id.editNamaM);
        final EditText ediTelpP =findViewById(R.id.editNomorM);
        final EditText editKotaP =findViewById(R.id.editKotaM);
        final EditText editPassP = findViewById(R.id.editPassM);
        btnRegis.setOnClickListener(view ->  addData(editPassP.getText().toString(),ediTelpP.getText().toString()));
    }


    private void cekNomor(){
        final EditText editNoP =findViewById(R.id.editNomorP);
        if (editNoP.getText().toString().length()>=10 && editNoP.getText().toString().length()<=14)
        {
//            Toast toast = Toast.makeText(getApplicationContext(),"Nyesss",Toast.LENGTH_SHORT);
//            toast.show();
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
//        final EditText edtemail =findViewById(R.id.edittextemail);
        final EditText editNamaP =findViewById(R.id.editNamaP);
        final EditText editNoP =findViewById(R.id.editNomorP);
        final EditText editKotaP =findViewById(R.id.editKotaP);
        final EditText editPassP = findViewById(R.id.editPassP);
        if (editPassP.getText().toString().length()>=8){
            Intent intent = new Intent(RegisterP.this, com.naplastech.tambalin.UserScreen.Handlers.class);
            intent.putExtra("nama",editNamaP.getText().toString());
            intent.putExtra("notelp",editNoP.getText().toString());
            intent.putExtra("alamat",editKotaP.getText().toString());
//            intent.putExtra("email",edtemail.getText().toString());
            intent.putExtra("password",editPassP.getText().toString());
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Password Minimal 8 Karakter", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void addData(String addpassword, String addtelp){
        usermodels akun = new usermodels();
        akun.setPassword(addpassword);
        akun.setNotelp(addtelp);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://tambalin-79727-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = firebaseDatabase.getReference("pengendara").child(addtelp);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    databaseReference.setValue(akun);
                    Toast.makeText(getApplicationContext(),"Telah Berhasil Daftar",Toast.LENGTH_SHORT).show();
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

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.activity_register_m);
//
//        txtuser = findViewById(R.id.editNamaM);
//        txtpass = findViewById(R.id.editPassM);
//        txtkota = findViewById(R.id.editKotaM);
//        txttelp = findViewById(R.id.editNomorM);
//        txtnamausaha = findViewById(R.id.editTempatM);
//
//        findViewById(R.id.buttonRegisterU).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                user = txtuser.getText().toString();
////                password = LoginScreen.md5(txtpass.getText().toString());
//                kota = txtkota.getText().toString();
//                telp = txttelp.getText().toString();
//                usaha = txtnamausaha.getText().toString();
//                try {
//                    new UploadFileToServer().execute();
//                }
//                catch(Exception error1) {
//                    Log.e("er1", "The exception caught while executing the process. (error1)");
//                    error1.printStackTrace();
//                }
//
//
////                if (isEmailValid(email)) {
////                    new UploadFileToServer().execute();
////                } else {
////                    Snackbar.make(findViewById(android.R.id.content), "Format email salah!", Snackbar.LENGTH_LONG).show();
////                }
//            }
//        });
//    }
//
////    boolean isEmailValid(String email) {
////        String regExpn =
////                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
////                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
////                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
////                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
////                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
////                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
////
////        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
////        Matcher matcher = pattern.matcher(email);
////
////        return matcher.matches();
////    }
//
//    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            progDailog = new ProgressDialog(RegisterM.this);
//            progDailog.setMessage("Mendaftar...");
//            progDailog.setIndeterminate(false);
//            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progDailog.setCancelable(false);
//            progDailog.setCanceledOnTouchOutside(false);
//            progDailog.show();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String data = null;
//            try {
//                data = uploadFile();
//            } catch (Exception e) {
//                e.printStackTrace();
//                data = "FAILED";
//            }
//            return data;
//        }
//
//        private String uploadFile() throws Exception {
//            URL url = new URL("https://tambalin.my.id/registermitra.php");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
//                    new AndroidMultiPartEntity.ProgressListener() {
//                        @Override
//                        public void transferred(long num) {
//                            publishProgress((int) ((num / (float) totalSize) * 100));
//                        }
//                    });
//            entity.addPart("nama", new StringBody(user + ""));
//            entity.addPart("kota", new StringBody(kota + ""));
//            entity.addPart("usaha" , new StringBody(usaha + ""));
//            entity.addPart("telp", new StringBody(telp + ""));
//            entity.addPart("password", new StringBody(password + ""));
//
//
//
//            totalSize = entity.getContentLength();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.addRequestProperty("Content-length", totalSize + "");
//            con.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());
//
//            OutputStream os = con.getOutputStream();
//            entity.writeTo(con.getOutputStream());
//            os.close();
//            con.connect();
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            return response.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            progDailog.dismiss();
//            if (result != null) {
//                Log.e("UPLOAD", result);
//                if (result.equalsIgnoreCase("OK")) {
//                    Toast.makeText(RegisterM.this, "Silahkan masuk dengan Nama Pengguna dan Password anda", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(RegisterM.this, LoginScreen.class));
//                } else {
//                    Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG).show();
//                }
//            }
//            super.onPostExecute(result);
//        }
//    }
//}