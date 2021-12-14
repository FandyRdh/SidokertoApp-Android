package com.prakpm2.pa_sidokerto_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginMainActivity extends AppCompatActivity {
    Button btn_Masuk;
    EditText etPw,etId;


    //deklarasi variabel tambahan
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/login.php";
    //Catatan: URL di atas tolong disesuaikan dengan IP4 kalian, cek di Command Prompt, ketik ipconfig
    //Stringrequest salah satu library volley utk menangkap data
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String user, pass; //variabel ini digunakan untuk menampung isi dari "txtUser_code" dan "txtPass_code"
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sessionManager = new SessionManager(this);

        btn_Masuk = findViewById(R.id.btn_Masuk);
        etPw = findViewById(R.id.etPw);
        etId = findViewById(R.id.etId);
        btn_Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = etId.getText().toString().trim();
                pass = etPw.getText().toString().trim();

                if (user.equals("") && pass.equals(""))
                {
                    Toast.makeText(LoginMainActivity.this, "Tidak ada inputan", Toast.LENGTH_SHORT).show();
                }else
                {
                    stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response ", response);
                            try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                                for (int a = 0; a < jsonArray.length(); a++) {

                                    JSONObject json = jsonArray.getJSONObject(a);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    String IdUser = json.getString("IdUser");
                                    String NamaUser = json.getString("NamaUser");
                                    String FotoUser = json.getString("FotoUser");
                                    String JenisUser = json.getString("JenisUser");
                                    System.out.println(IdUser+" "+NamaUser+" "+FotoUser+" "+JenisUser+" Login");
                                    sessionManager.createSession(IdUser,NamaUser,FotoUser,JenisUser);

                                    if (JenisUser.equals("siswa")){
                                        Intent i = new Intent(getApplicationContext(), DashboardSiswa.class);
                                        startActivity(i);
                                        finish();
                                    }else if (JenisUser.equals("guru")){
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        //Jika Tidak Di temukan
                                        Toast.makeText(LoginMainActivity.this, "NISN atau Password yang anda masukan salah", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        //anda cukup menuliskan "map", lalu enter. Otomatis method getParams akan terbentuk
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //jika script masih ada warning, brarti blom di transfer menggunakan method POST
                            //oleh karena itu, dibutuhkan 3 baris script ini untuk mengirim data ke login.php
                            Map<String, String> data = new HashMap<>();
                            data.put("user", user);
                            data.put("pass", pass);
                            return data;
                        }
                    };

                    //request ke Volley
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }

            }//end klik btn login
        });
    }
}