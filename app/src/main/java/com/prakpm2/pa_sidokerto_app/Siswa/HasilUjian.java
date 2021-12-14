package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prakpm2.pa_sidokerto_app.DashboardSiswa;
import com.prakpm2.pa_sidokerto_app.LoginMainActivity;
import com.prakpm2.pa_sidokerto_app.MainActivity;
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;
import com.prakpm2.pa_sidokerto_app.onboard_1;
import com.prakpm2.pa_sidokerto_app.onboard_2;
import com.prakpm2.pa_sidokerto_app.onboard_3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HasilUjian extends AppCompatActivity {
    TextView txtNilai,txtJawabanBenar;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String ID_UJIAN_H,NISN_H;
    int jumlahSoal = 0;
    Button btn_BackMenu;

    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;
    private String cekResponse;
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/PenilaianUjian.php";
    private String URL_U_Nilai = "http://192.168.43.153/api/PA_Sidokreto_App/UpdateNilai.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_ujian);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtNilai = findViewById(R.id.txtNilai);
        txtJawabanBenar = findViewById(R.id.txtJawabanBenar);
        btn_BackMenu = findViewById(R.id.btnBack);

//        Session
        sessionManager = new SessionManager(this);

//        Intent
        Intent result = getIntent();
        ID_UJIAN_H =  result.getStringExtra("ID_UJIAN_H");
        NISN_H =  result.getStringExtra("NISN_H");

        btn_BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HasilUjian.this, DashboardSiswa.class);
                startActivity(i);
                finish();
            }
        });


//        GetData
        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonLogin = jsonObj.getJSONArray("JawabanUjian");
                    //hitung jumlah baris data
                    int JumlahBenar = 0;
                    for (int i = 0; i < jsonLogin.length(); i++) {
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


                            String KUNCI = jsonData.getString("KUNCI");
                            String JAWABAN_UJIAN = jsonData.getString("JAWABAN_UJIAN");

                            if (KUNCI.equalsIgnoreCase(JAWABAN_UJIAN)){
                                JumlahBenar = JumlahBenar + 1;
                            }

                    }
                    jumlahSoal = jsonLogin.length();
                    cetak(jumlahSoal,JumlahBenar);
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
                data.put("ID_UJIAN_H", ID_UJIAN_H);
                data.put("NISN_H", NISN_H);
                return data;
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    public void cetak(int jumlahSoal,int JumlahBenar){
        int NilaiPerSoal = 100 / jumlahSoal;
        int nilaiUjian = NilaiPerSoal*JumlahBenar;

        txtNilai.setText(String.valueOf(nilaiUjian));
        txtJawabanBenar.setText("Jumlah Benar"+String.valueOf(JumlahBenar)+"/"+jumlahSoal);
        //Update
        stringRequest = new StringRequest(Request.Method.POST, URL_U_Nilai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Update"))
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
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
                data.put("nisn_u", NISN_H);
                data.put("idujian_u", ID_UJIAN_H);
                data.put("nilai_U", String.valueOf(nilaiUjian));
                return data;
            }
        };
        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}