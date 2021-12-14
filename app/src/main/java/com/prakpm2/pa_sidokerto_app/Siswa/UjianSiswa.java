package com.prakpm2.pa_sidokerto_app.Siswa;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UjianSiswa extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list_data;

    //deklarasi variabel tambahan
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/tampilUjianSiswa.php";

    //Stringrequest salah satu library volley utk menangkap data
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String JUDUL_UJIAN,JENIS_UJIAN,NAMA_MAPEL,JADAWAL_MULAI,NAMA_KRY;
    String NISN; //variabel ini digunakan untuk menampung isi dari "txtUser_code" dan "txtPass_code"
    SessionManager sessionManager;


    private RecyclerView rv_list_ujian;

    //deklarasi variable untuk Json
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private URL url;
    private HttpURLConnection conn;
    private String cekResponse;

    private String hasil;
    //arrayadapter digunakan untuk menampung data dalam array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_siswa);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sessionManager = new SessionManager(this);
        rv_list_ujian = (RecyclerView) findViewById(R.id.rv_BS);


        list_data = new ArrayList<HashMap<String, String>>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list_ujian.setLayoutManager(llm);


        HashMap<String,String> user = sessionManager.getUserDetail();
        NISN = user.get(sessionManager.IdUser);


        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonLogin = jsonObj.getJSONArray("DataUjian");
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


                        map.put("JUDUL_UJIAN", jsonData.getString("JUDUL_UJIAN"));
                        map.put("JENIS_UJIAN", jsonData.getString("JENIS_UJIAN"));
                        map.put("NAMA_MAPEL", jsonData.getString("NAMA_MAPEL"));
                        map.put("JADAWAL_MULAI", jsonData.getString("JADAWAL_MULAI"));
                        map.put("NAMA_KRY", jsonData.getString("NAMA_KRY"));
                        map.put("ID_UJIAN", jsonData.getString("ID_UJIAN"));

                        list_data.add(map);
                        AdapterUjianSiswa adapter = new AdapterUjianSiswa(UjianSiswa.this, list_data);
                        rv_list_ujian.setAdapter(adapter);

                        JUDUL_UJIAN = jsonData.getString("JUDUL_UJIAN");
                        JENIS_UJIAN = jsonData.getString("JENIS_UJIAN");
                        NAMA_MAPEL = jsonData.getString("NAMA_MAPEL");
                        JADAWAL_MULAI = jsonData.getString("JADAWAL_MULAI");
                        NAMA_KRY = jsonData.getString("NAMA_KRY");

                        //membuat data adapter menggunakan method add()
//                        adapter_login.add("User = " + user + "\n" + "Pass = " + pass);

                        //hasil = hasil + user + "=" + pass + "|"; //hanya dipake buat testing saja
                    }
                    //mengirim data adapter utk di tempatkan ke dalam List view menggunakan method setAdapter()
//                    lvLogin_code.setAdapter(adapter_login);

                    //txtStatus_code.setText("masuk di try JSON = " + hasil);
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
                data.put("NISN", NISN);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}