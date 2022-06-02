package com.naplastech.tambalin.UserScreen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.naplastech.tambalin.MapScreen.MapScreen;
import com.naplastech.tambalin.R;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginScreen extends AppCompatActivity {

    long totalSize = 0;
    String user = "";
    String password = "";
    EditText txtuser;
    EditText txtpass;
    ProgressDialog progDailog;

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_screen);
        txtuser = findViewById(R.id.editTextUser);
        txtpass = findViewById(R.id.editTextPassword);

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
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = txtuser.getText().toString();
                password = md5(txtpass.getText().toString());
                new UploadFileToServer().execute();
            }
        });
    }

        private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
            @Override
            protected void onPreExecute() {
                progDailog = new ProgressDialog(LoginScreen.this);
                progDailog.setMessage("Masuk...");
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
            URL url = new URL("https://tambalin.my.id/login.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            entity.addPart("user", new StringBody(user + ""));
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
                if (!result.equalsIgnoreCase("FAILED")) {
                    String[] hasil = result.split("\\|");
                    SharedPreferences.Editor mEditor = getSharedPreferences("MOBILE", 0).edit();
                    mEditor.putString("user", hasil[0]).apply();
//                    mEditor.putString("email", hasil[1]).apply();
//                    mEditor.putString("ponsel", hasil[2]).apply();

                    Intent intent = new Intent(LoginScreen.this, MapScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Pengguna atau Kata sandi salah!", Snackbar.LENGTH_LONG).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}