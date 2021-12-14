package com.prakpm2.pa_sidokerto_app.Guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prakpm2.pa_sidokerto_app.MainActivity;
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailBankSoal extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list_data;
    private FloatingActionButton btnadd;

    //URL
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/Guru/DetailBankSoal.php";

    //VAR SQL
    StringRequest stringRequest;
    RequestQueue requestQueue;
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;
    private String cekResponse;

    //VAR SESSION
    SessionManager sessionManager;
    RecyclerView rv_DBS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bank_soal);
        rv_DBS = findViewById(R.id.rv_DBS);
        btnadd = findViewById(R.id.btnadd);

        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        //NISN = user.get(sessionManager.IdUser);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //SET VAR


        //SET RV
        list_data = new ArrayList<HashMap<String, String>>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_DBS.setLayoutManager(llm);




        //Intent
        String ID_PAKET = getIntent().getStringExtra("ID_PAKET");
        String NAMA_KRY = getIntent().getStringExtra("NAMA_KRY");
        String NAMA_MAPEL = getIntent().getStringExtra("NAMA_MAPEL");
        String JUDUL_SOAL = getIntent().getStringExtra("JUDUL_SOAL");
        String JENIS_SOAL = getIntent().getStringExtra("JENIS_SOAL");
        String TGL_PEMBUATAN = getIntent().getStringExtra("TGL_PEMBUATAN");
        //sql


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBankSoal.this.getApplicationContext(), addSoal.class);
                intent.putExtra("ID_PAKET",ID_PAKET);
                ((Activity) DetailBankSoal.this).startActivityForResult(intent,1);
                DetailBankSoal.this.startActivity(intent);
                finish();
            }
        });











        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonLogin = jsonObj.getJSONArray("DetailBankSoal");
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        int no = i+1;
                        map.put("ID_SOAL", jsonData.getString("ID_SOAL"));
                        map.put("ID_PAKET", jsonData.getString("ID_PAKET"));
                        map.put("NOMOR_URUT", jsonData.getString("NOMOR_URUT"));
                        map.put("SOAL_UJIAN", jsonData.getString("SOAL_UJIAN"));
                        map.put("PILIHAN_1", jsonData.getString("PILIHAN_1"));
                        map.put("PILIHAN_2", jsonData.getString("PILIHAN_2"));
                        map.put("PILIHAN_3", jsonData.getString("PILIHAN_3"));
                        map.put("PILIHAN_4", jsonData.getString("PILIHAN_4"));
                        map.put("KUNCI", jsonData.getString("KUNCI"));
                        map.put("ASSET_SOAL", jsonData.getString("ASSET_SOAL"));
                        map.put("no", String.valueOf(no));
                        map.put("JENIS_SOAL", " ");


                        list_data.add(map);
                        AdapterDetailBankSoal adapter = new AdapterDetailBankSoal(DetailBankSoal.this, list_data);
                        rv_DBS.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                txtStatus_code.setText("masuk di method onErrorResponse()");

            }
        })
        {
            //anda cukup menuliskan "map", lalu enter. Otomatis method getParams akan terbentuk
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<>();
                data.put("ID_PAKET", ID_PAKET);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        Intent i = new Intent(DetailBankSoal.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}