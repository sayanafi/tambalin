package com.naplastech.tambalin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.naplastech.tambalin.UserScreen.AndroidMultiPartEntity;
import com.naplastech.tambalin.UserScreen.LoginScreen;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MitraRegisterScreen extends AppCompatActivity {

    long totalSize = 0;
    String user = "";
    String password = "";
    String kota = "";
    String telp = "";
    String usaha = "";
    String alamat = "";
    EditText txtuser;
    EditText txtpass;
    EditText txtkota;
    EditText txttelp;
    EditText txtnamausaha;
    EditText txtalamat;

    ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtuser = findViewById(R.id.editTextMitraName);
        txtpass = findViewById(R.id.editTextMitraPassword);
        txtkota = findViewById(R.id.editTextMitraCity);
        txttelp = findViewById(R.id.editTextMitraTelp);
        txtnamausaha = findViewById(R.id.editTextWorkName);
        txtalamat = findViewById(R.id.editTextMitraAdress);

        findViewById(R.id.MitraRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = txtuser.getText().toString();
//                password = LoginScreen.md5(txtpass.getText().toString());
                kota = txtkota.getText().toString();
                telp = txttelp.getText().toString();
                usaha = txtnamausaha.getText().toString();
                alamat = txtalamat.getText().toString();
                try {
                    new UploadFileToServer().execute();
                }
                catch(Exception error1) {
                    Log.e("er1", "The exception caught while executing the process. (error1)");
                    error1.printStackTrace();
                }


//                if (isEmailValid(email)) {
//                    new UploadFileToServer().execute();
//                } else {
//                    Snackbar.make(findViewById(android.R.id.content), "Format email salah!", Snackbar.LENGTH_LONG).show();
//                }
            }
        });
    }

//    boolean isEmailValid(String email) {
//        String regExpn =
//                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
//                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
//                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
//
//        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(email);
//
//        return matcher.matches();
//    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(MitraRegisterScreen.this);
            progDailog.setMessage("Mendaftar...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.setCanceledOnTouchOutside(false);
            progDailog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String data = null;
            try {
                data = uploadFile();
            } catch (Exception e) {
                e.printStackTrace();
                data = "FAILED";
            }
            return data;
        }

        private String uploadFile() throws Exception {
            URL url = new URL("https://tambalin.my.id/registermitra.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            entity.addPart("user", new StringBody(user + ""));
            entity.addPart("kota", new StringBody(kota + ""));
            entity.addPart("usaha" , new StringBody(usaha + ""));
            entity.addPart("alamat", new StringBody(alamat + ""));
            entity.addPart("telp", new StringBody(telp + ""));
            entity.addPart("password", new StringBody(password + ""));



            totalSize = entity.getContentLength();
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.addRequestProperty("Content-length", totalSize + "");
            con.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());

            OutputStream os = con.getOutputStream();
            entity.writeTo(con.getOutputStream());
            os.close();
            con.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();
            if (result != null) {
                Log.e("UPLOAD", result);
                if (result.equalsIgnoreCase("OK")) {
                    Toast.makeText(MitraRegisterScreen.this, "Silahkan masuk dengan Nama Pengguna dan Password anda", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MitraRegisterScreen.this, LoginScreen.class));
                } else {
                    Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}