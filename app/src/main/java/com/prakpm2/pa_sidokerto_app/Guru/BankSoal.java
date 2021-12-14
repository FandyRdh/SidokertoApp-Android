package com.prakpm2.pa_sidokerto_app.Guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;
import com.prakpm2.pa_sidokerto_app.Siswa.AdapterUjianSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.UjianSiswa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankSoal extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list_data;



    //URL
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/Guru/BankSoal.php";

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

    //VAR ATRIBUT
    RecyclerView rv_BS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_soal);
        //SESSION SET
            sessionManager = new SessionManager(this);
            HashMap<String,String> user = sessionManager.getUserDetail();
            //NISN = user.get(sessionManager.IdUser);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //SET VAR
            rv_BS = findViewById(R.id.rv_BS);

            //SET RV
            list_data = new ArrayList<HashMap<String, String>>();
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_BS.setLayoutManager(llm);


        //sql
        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonLogin = jsonObj.getJSONArray("BankSoal");
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("ID_PAKET", jsonData.getString("ID_PAKET"));
                        map.put("NAMA_KRY", jsonData.getString("NAMA_KRY"));
                        map.put("NAMA_MAPEL", jsonData.getString("NAMA_MAPEL"));
                        map.put("JUDUL_SOAL", jsonData.getString("JUDUL_SOAL"));
                        map.put("JENIS_SOAL", jsonData.getString("JENIS_SOAL"));
                        map.put("TGL_PEMBUATAN", jsonData.getString("TGL_PEMBUATAN"));

                        list_data.add(map);
                        AdapterBankSoal adapter = new AdapterBankSoal(BankSoal.this, list_data);
                        rv_BS.setAdapter(adapter);

//                        JUDUL_UJIAN = jsonData.getString("JUDUL_UJIAN");
                        //txtStatus_code.setText("masuk di try JSON = " + hasil);
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
//                data.put("NISN", NISN);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}