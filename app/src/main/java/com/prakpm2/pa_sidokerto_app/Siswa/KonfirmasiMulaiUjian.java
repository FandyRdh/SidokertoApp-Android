package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class KonfirmasiMulaiUjian extends AppCompatActivity {
    Button btn_KonfrimMulai;
    String NISN,NamaUser;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    TextView JUDUL_UJIAN,JENIS_UJIAN,JADAWAL_MULAI,NAMA_KRY,NAMA_MAPEL,txtNamaKNF,txtNIMKNF;

//    Sql
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonLogin;
    private String jsonStr; //untuk menampung datanya
    private java.net.URL url;
    private HttpURLConnection conn;
    private String cekResponse;
    private String URL_U_Nilai = "http://192.168.43.153/api/PA_Sidokreto_App/UpdateNilai.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_mulai_ujian);

//        sql

        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        NISN = user.get(sessionManager.IdUser);
        NamaUser = user.get(sessionManager.NamaUser);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Ambil Dari Ujian_SIswa
        Intent result = getIntent();
        String judul = result.getStringExtra("JUDUL_UJIAN");
        String jenis = result.getStringExtra("JENIS_UJIAN");
        String jadwal = result.getStringExtra("JADAWAL_MULAI");
        String guru = result.getStringExtra("NAMA_KRY");
        String mapel = result.getStringExtra("NAMA_MAPEL");
        String idujian_2 = result.getStringExtra("ID_UJIAN");
        String jamselesai = result.getStringExtra("JADAWAL_SELESAI");

        JUDUL_UJIAN = findViewById(R.id.txtJudulKNF);
        JENIS_UJIAN = findViewById(R.id.txtJenisUjianKNF);
        JADAWAL_MULAI = findViewById(R.id.txtJammulaiKNF);
        NAMA_KRY = findViewById(R.id.txtPengjarKNF);
        NAMA_MAPEL = findViewById(R.id.txtMapelKNF);
        txtNIMKNF = findViewById(R.id.txtNIMKNF);
        txtNamaKNF = findViewById(R.id.txtNamaKNF);

        JUDUL_UJIAN.setText(String.valueOf(judul));
        JENIS_UJIAN.setText(String.valueOf(jenis));
        JADAWAL_MULAI.setText(String.valueOf(jadwal));
        NAMA_KRY.setText(String.valueOf(guru));
        NAMA_MAPEL.setText(String.valueOf(mapel));
        txtNIMKNF.setText(String.valueOf(NISN));
        txtNamaKNF.setText(String.valueOf(NamaUser));


        btn_KonfrimMulai = findViewById(R.id.btn_KonfrimMulai);
        btn_KonfrimMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KonfirmasiMulaiUjian.this)
                        .setIcon(R.drawable.warning_icon)
                        .setTitle("Peringatan !!!")
                        .setMessage("Kamu yakin memulai ujian ?  ketika ujian di mulai kamu tidak dapat keluar sebelum menyelesaikan ujian.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Kirim Lagi ke Ujian
                                Intent intent = new Intent(KonfirmasiMulaiUjian.this, DetailUjianSiswa.class);
                                intent.putExtra("JUDUL_UJIAN",judul);
                                intent.putExtra("JENIS_UJIAN",jenis);
                                intent.putExtra("JADAWAL_MULAI",jadwal);
                                intent.putExtra("NAMA_MAPEL",mapel);
                                intent.putExtra("NAMA_KRY",guru);
                                intent.putExtra("JADAWAL_SELESAI",jamselesai);
                                intent.putExtra("ID_UJIAN",idujian_2);

                                //Run
                                SetMulai(NISN,idujian_2,"0");

                                //Intent
                                ((Activity) KonfirmasiMulaiUjian.this).startActivityForResult(intent,1);
                                KonfirmasiMulaiUjian.this.startActivity(intent);
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

            public void SetMulai(String NISN_H,String ID_UJIAN_H,String nilaiUjian){
                //Update
                stringRequest = new StringRequest(Request.Method.POST, URL_U_Nilai, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        if (response.equals("Update"))
//                            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
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

        });
    }

}