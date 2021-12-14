package com.prakpm2.pa_sidokerto_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.prakpm2.pa_sidokerto_app.Siswa.NilaiUjian;
import com.prakpm2.pa_sidokerto_app.Siswa.ProfileSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.Ranking;
import com.prakpm2.pa_sidokerto_app.Siswa.UjianSiswa;

import java.util.HashMap;

public class DashboardSiswa extends AppCompatActivity {

    SessionManager sessionManager;
    ImageButton btn_ujian,btn_Nilai,btn_profile,btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_siswa);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sessionManager = new SessionManager(this);
        //sessionManager.checkLogin();
        btn_ujian = findViewById(R.id.btn_ujian);
        btn_Nilai = findViewById(R.id.btn_Nilai);
        btn_logout = findViewById(R.id.btn_logout);
        btn_profile = findViewById(R.id.btn_profile);
        btn_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UjianSiswa.class);
                startActivity(i);
            }
        });

        btn_Nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NilaiUjian.class);
                startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfileSiswa.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_Rank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Ranking.class);
                startActivity(i);
            }
        });


        HashMap<String,String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.IdUser);
        String Nama = user.get(sessionManager.NamaUser);
        String jenis = user.get(sessionManager.FotoUser);
        String foto = user.get(sessionManager.FotoUser);

    }
}