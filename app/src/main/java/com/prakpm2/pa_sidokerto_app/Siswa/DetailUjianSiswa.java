package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailUjianSiswa extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list_data2;
    TextView txtNamaUjian1,txtJenisUjian1,txtJadwalUjian1,txtNamaMapel1,txtNamaPengajar1;
    RecyclerView rv_list_soal;

    //deklarasi variabel tambahan
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/tampilListSoal.php";

    //Stringrequest salah satu library volley utk menangkap data
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String JUDUL_UJIAN,JENIS_UJIAN,NAMA_MAPEL,JADAWAL_MULAI,NAMA_KRY;
    String NISN; //variabel ini digunakan untuk menampung isi dari "txtUser_code" dan "txtPass_code"
    SessionManager sessionManager;



    //deklarasi variable untuk Json
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;
    private String cekResponse;

    private String hasil;
    String idujian_2;
    //arrayadapter digunakan untuk menampung data dalam array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ujian_siswa);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtNamaUjian1 = findViewById(R.id.txtNamaUjian1);
        txtJenisUjian1 = findViewById(R.id.txtJenisUjianJawaban);
        txtJadwalUjian1 = findViewById(R.id.txtJadwalUjian1);
        txtNamaMapel1 = findViewById(R.id.txtNamaMapel1);
        txtNamaPengajar1 = findViewById(R.id.txtNamaPengajar1);

        String judul = getIntent().getStringExtra("JUDUL_UJIAN");
        String jenis = getIntent().getStringExtra("JENIS_UJIAN");
        String jadwal = getIntent().getStringExtra("JADAWAL_MULAI");
        String guru = getIntent().getStringExtra("NAMA_KRY");
        String mapel = getIntent().getStringExtra("NAMA_MAPEL");
        idujian_2 = getIntent().getStringExtra("ID_UJIAN");
        String jamselesai = getIntent().getStringExtra("JADAWAL_SELESAI");

        txtNamaUjian1.setText(judul);
        txtJenisUjian1.setText(jenis);
        txtJadwalUjian1.setText(jadwal);
        txtNamaMapel1.setText(mapel);
        txtNamaPengajar1.setText(guru);

        sessionManager = new SessionManager(this);
        rv_list_soal = (RecyclerView) findViewById(R.id.rv_BS);

        list_data2 = new ArrayList<HashMap<String, String>>();
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list_soal.setLayoutManager(llm2);

        HashMap<String,String> user = sessionManager.getUserDetail();
        NISN = user.get(sessionManager.IdUser);

        findViewById(R.id.btnSimpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Primitif = new Intent( DetailUjianSiswa.this,HasilUjian.class);
                Primitif.putExtra("ID_UJIAN_H",idujian_2);
                Primitif.putExtra("NISN_H",NISN);
                startActivity(Primitif);
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
                    jsonLogin = jsonObj.getJSONArray("DataListSoal");
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


                        String ID_SOAL = jsonData.getString("ID_SOAL");
                        String ID_PAKET = jsonData.getString("ID_PAKET");
                        String SOAL_UJIAN = jsonData.getString("SOAL_UJIAN");
                        String PILIHAN_1 = jsonData.getString("PILIHAN_1");
                        String PILIHAN_2 = jsonData.getString("PILIHAN_2");
                        String PILIHAN_3 = jsonData.getString("PILIHAN_3");
                        String PILIHAN_4 = jsonData.getString("PILIHAN_4");
                        String kunci = jsonData.getString("kunci");
                        String jawaban_ujian = jsonData.getString("jawaban_ujian");

                        int nomor = i + 1;
                        map.put("txtNomor", "No."+nomor);
                        map.put("txtSoal", SOAL_UJIAN);
                        map.put("txtJenisUjian2", jenis);
                        map.put("PILIHAN_1", PILIHAN_1);
                        map.put("PILIHAN_2", PILIHAN_2);
                        map.put("PILIHAN_3", PILIHAN_3);
                        map.put("PILIHAN_4", PILIHAN_4);
                        map.put("ID_PAKET", ID_PAKET);
                        map.put("ID_SOAL", ID_SOAL);
                        map.put("ID_UJIAN", idujian_2);
                        map.put("kunci", kunci);
                        map.put("jawaban_ujian", jawaban_ujian);

                        list_data2.add(map);
                        AdapterListSoal adapter = new AdapterListSoal(DetailUjianSiswa.this, list_data2);
                        rv_list_soal.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
        @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
//            Primitif.putExtra("ID_UJIAN_H",idujian_2);
                data.put("idujian", idujian_2);
                data.put("NISN_H", NISN);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.warning_icon)
                .setTitle("Pringatan !!!")
                .setMessage("Jika Keluar aplikasi kamu akan menyelesaikan sesi ujian kamu dan tidak dapat ujian mengerjakan kembali")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Primitif = new Intent( DetailUjianSiswa.this,HasilUjian.class);
                        Primitif.putExtra("ID_UJIAN_H",idujian_2);
                        Primitif.putExtra("NISN_H",NISN);
                        startActivity(Primitif);
                        finish();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}