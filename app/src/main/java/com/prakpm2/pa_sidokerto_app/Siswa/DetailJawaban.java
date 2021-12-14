package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
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

public class DetailJawaban extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list_data2;

    //deklarasi variabel tambahan
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/TampilJawabanUjian.php";

    //Stringrequest salah satu library volley utk menangkap data
    StringRequest stringRequest;
    RequestQueue requestQueue;
    SessionManager sessionManager;

    //deklarasi variable untuk Json
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;


    TextView txtNamaUjian2,txtNIlaiUjian3,txtJenisUjian3,txtNamaPengajar3,txtNamaMapel3;
    Button btn_Kembali3;
    RecyclerView rv_list_ujian3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_detail_jawaban);
        txtNamaUjian2 = findViewById(R.id.txtNamaUjian2);
        txtNIlaiUjian3 = findViewById(R.id.txtNIlaiUjian3);
        txtJenisUjian3 = findViewById(R.id.txtJenisUjian3);
        txtNamaPengajar3 = findViewById(R.id.txtNamaPengajar3);
        txtNamaMapel3 = findViewById(R.id.txtNamaMapel3);
        btn_Kembali3 = findViewById(R.id.btn_Kembali3);
        rv_list_ujian3 = findViewById(R.id.rv_list_ujian4);


        String JUDUL_UJIAN = getIntent().getStringExtra("JUDUL_UJIAN");
        String NAMA_MAPEL = getIntent().getStringExtra("NAMA_MAPEL");
        String NAMA_KRY = getIntent().getStringExtra("NAMA_KRY");
        String JENIS_UJIAN = getIntent().getStringExtra("JENIS_UJIAN");
        String NILAI_UJIAN = getIntent().getStringExtra("NILAI_UJIAN");
        String id_ujian = getIntent().getStringExtra("id_ujian");

        txtNamaUjian2.setText(JUDUL_UJIAN);
        txtNIlaiUjian3.setText(NILAI_UJIAN);
        txtJenisUjian3.setText(JENIS_UJIAN);
        txtNamaPengajar3.setText(NAMA_KRY);
        txtNamaMapel3.setText(NAMA_MAPEL);


        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        String nisn = user.get(sessionManager.IdUser);

        list_data2 = new ArrayList<HashMap<String, String>>();
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list_ujian3.setLayoutManager(llm2);

        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonLogin = jsonObj.getJSONArray("JawabanSiswa");

                    System.out.println("Jumlah Data" + jsonLogin.length());
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


//                        String ID_JAWABAN_UJIAN = jsonData.getString("ID_JAWABAN_UJIAN");
//                        String NISN = jsonData.getString("NISN");
//                        String ID_UJIAN = jsonData.getString("ID_UJIAN");
//                        String ID_SOAL = jsonData.getString("ID_SOAL");
                        String JAWABAN_UJIAN = jsonData.getString("JAWABAN_UJIAN");
                        String SOAL_UJIAN = jsonData.getString("SOAL_UJIAN");
                        String JENIS_UJIAN = jsonData.getString("JENIS_UJIAN");
//                        String ID_PAKET = jsonData.getString("ID_PAKET");
//                        String SOAL_UJIAN2 = jsonData.getString("SOAL_UJIAN");
//                        String PILIHAN_2 = jsonData.getString("PILIHAN_2");
//                        String PILIHAN_3 = jsonData.getString("PILIHAN_3");
//                        String PILIHAN_4 = jsonData.getString("PILIHAN_4");
                        String KUNCI = jsonData.getString("KUNCI");

                        int nomor = i + 1;
                        map.put("txtNomor", "No."+nomor);
                        map.put("SOAL_UJIAN", SOAL_UJIAN);
                        map.put("JENIS_UJIAN", JENIS_UJIAN);
                        map.put("JAWABAN_UJIAN", JAWABAN_UJIAN);
                        map.put("KUNCI", KUNCI);

                        list_data2.add(map);
                        AdapterListJawaban adapter = new AdapterListJawaban(DetailJawaban.this, list_data2);
                        rv_list_ujian3.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        //hasil = hasil + user + "=" + pass + "|"; //hanya dipake buat testing saja
                    }
                    System.out.println("Jumlah Data" + jsonLogin.length());
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
                data.put("nisn", nisn);
                data.put("idujian", id_ujian);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}