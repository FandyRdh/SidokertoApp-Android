package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.prakpm2.pa_sidokerto_app.Guru.AdapterBankSoal;
import com.prakpm2.pa_sidokerto_app.Guru.BankSoal;
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class DetailSoal extends AppCompatActivity {
    SessionManager sessionManager;
    TextView txtNoSoal,txtSoalUjian;
    Button btnPilihanA, btnPilihanB,btnPilihanC,btnPilihanD;
    int klik = 0;
    String jawabanSiswa = "";
    String JAWABAN_UJIAN_DS;
    String nisn,ID_SOAL,ID_UJIAN,kunci,jawaban_ujian;

    //
    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/UpdateJawaban.php";
    private String URL_Jawaban = "http://192.168.43.153/api/PA_Sidokreto_App/JawabanSiswaDS.php";

    //VAR SQL
    StringRequest stringRequest,stringRequest2;
    RequestQueue requestQueue;
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;
    private String cekResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_soal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Session
        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        nisn = user.get(sessionManager.IdUser);

        txtNoSoal = findViewById(R.id.txtNoSoal);
        txtSoalUjian = findViewById(R.id.txtSoalUjian);
        btnPilihanA = findViewById(R.id.btnPilihanA);
        btnPilihanB = findViewById(R.id.btnPilihanB);
        btnPilihanC = findViewById(R.id.btnPilihanC);
        btnPilihanD = findViewById(R.id.btnPilihanD);


        String NomorSoal = getIntent().getStringExtra("NomorSoal");
        ID_SOAL = getIntent().getStringExtra("ID_SOAL");
        String Soal = getIntent().getStringExtra("Soal");
        String PILIHAN_1 = getIntent().getStringExtra("PILIHAN_1");
        String PILIHAN_2 = getIntent().getStringExtra("PILIHAN_2");
        String PILIHAN_3 = getIntent().getStringExtra("PILIHAN_3");
        String PILIHAN_4 = getIntent().getStringExtra("PILIHAN_4");
        ID_UJIAN = getIntent().getStringExtra("ID_UJIAN");
        kunci = getIntent().getStringExtra("kunci");
        jawaban_ujian = getIntent().getStringExtra("jawaban_ujian");

        txtNoSoal.setText(NomorSoal);
        txtSoalUjian.setText(Soal);
        btnPilihanA.setText(PILIHAN_1);
        btnPilihanB.setText(PILIHAN_2);
        btnPilihanC.setText(PILIHAN_3);
        btnPilihanD.setText(PILIHAN_4);

        setWarnaButton();
        setKunciWarna();

        btnPilihanA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik =  klik + 1;
                if (klik == 2){
                    setWarnaButton();
                    klik = 0;
                    jawabanSiswa = "";
                }else if (klik == 1){
                    setWarnaButton();
                btnPilihanA.setBackgroundColor(btnPilihanA.getContext().getResources().getColor(R.color.teal_700));
                    jawabanSiswa = PILIHAN_1;
                }
                toast();
            }
        });
        btnPilihanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik =  klik + 1;
                if (klik == 2){
                    setWarnaButton();
                    klik = 0;
                    jawabanSiswa = "";
                }else if (klik == 1){
                    setWarnaButton();
                    btnPilihanB.setBackgroundColor(btnPilihanB.getContext().getResources().getColor(R.color.teal_700));
                    jawabanSiswa = PILIHAN_2;
                }
                toast();
            }
        });
        btnPilihanC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik =  klik + 1;
                if (klik == 2){
                    setWarnaButton();
                    klik = 0;
                    jawabanSiswa = "";
                }else if (klik == 1){
                    setWarnaButton();
                    btnPilihanC.setBackgroundColor(btnPilihanC.getContext().getResources().getColor(R.color.teal_700));
                    jawabanSiswa = PILIHAN_3;
                }
                toast();
            }
        });
        btnPilihanD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik =  klik + 1;
                if (klik == 2){
                    setWarnaButton();
                    klik = 0;
                    jawabanSiswa = "";

                }else if (klik == 1){
                    setWarnaButton();
                    btnPilihanD.setBackgroundColor(btnPilihanD.getContext().getResources().getColor(R.color.teal_700));
                    jawabanSiswa = PILIHAN_4;
                }
                toast();
            }
        });

    }

    public void toast(){
//        Toast.makeText(this, jawabanSiswa, Toast.LENGTH_SHORT).show();


        //set
            //instance of class
            stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                 //here
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
                    data.put("nisn",nisn);
                    data.put("id_soal", ID_SOAL);
                    data.put("id_ujian", ID_UJIAN);
                    data.put("jawabansiswa", jawabanSiswa);
                    return data;
                }
            };
            //request ke Volley
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

    }

    public void setWarnaButton(){
        btnPilihanA.setBackgroundColor(btnPilihanA.getContext().getResources().getColor(R.color.white));
        btnPilihanB.setBackgroundColor(btnPilihanB.getContext().getResources().getColor(R.color.white));
        btnPilihanC.setBackgroundColor(btnPilihanC.getContext().getResources().getColor(R.color.white));
        btnPilihanD.setBackgroundColor(btnPilihanD.getContext().getResources().getColor(R.color.white));
    }

    public void setKunciWarna(){

        stringRequest2 = new StringRequest(Request.Method.POST, URL_Jawaban, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObj = new JSONObject(response);
                    jsonLogin = jsonObj.getJSONArray("DataJawabanSiswaDS");
                    for (int i = 0; i < jsonLogin.length(); i++) {
                         jsonData = jsonLogin.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        System.out.println("Data Jawaban : "+jsonData.getString("JAWABAN_UJIAN"));
                        JAWABAN_UJIAN_DS = jsonData.getString("JAWABAN_UJIAN");
                        Startup(JAWABAN_UJIAN_DS);
                    }
                    Startup(JAWABAN_UJIAN_DS);
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
                System.out.println(nisn +"   "+ID_SOAL);
                data.put("nisn",nisn);
                data.put("id_soal", ID_SOAL);
                return data;
            }
        };
        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest2);


    }

    public void Startup(String P_JAWABAN_UJIAN_DS){

        // SetPilihan->warna
        if (P_JAWABAN_UJIAN_DS.equalsIgnoreCase( btnPilihanA.getText().toString())) {
            btnPilihanA.setBackgroundColor(btnPilihanA.getContext().getResources().getColor(R.color.teal_700));
        }else  if (P_JAWABAN_UJIAN_DS.equalsIgnoreCase( btnPilihanB.getText().toString())) {
            btnPilihanB.setBackgroundColor(btnPilihanB.getContext().getResources().getColor(R.color.teal_700));
        }else  if (P_JAWABAN_UJIAN_DS.equalsIgnoreCase( btnPilihanC.getText().toString())) {
            btnPilihanC.setBackgroundColor(btnPilihanC.getContext().getResources().getColor(R.color.teal_700));
        }else  if (P_JAWABAN_UJIAN_DS.equalsIgnoreCase( btnPilihanD.getText().toString())) {
            btnPilihanD.setBackgroundColor(btnPilihanD.getContext().getResources().getColor(R.color.teal_700));
        }
    }

}