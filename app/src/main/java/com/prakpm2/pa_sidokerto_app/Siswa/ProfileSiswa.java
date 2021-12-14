package com.prakpm2.pa_sidokerto_app.Siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prakpm2.pa_sidokerto_app.DashboardSiswa;
import com.prakpm2.pa_sidokerto_app.LoginMainActivity;
import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.SessionManager;

import java.util.HashMap;

public class ProfileSiswa extends AppCompatActivity {

    SessionManager sessionManager;
    TextView txtNama,txtNISN,txtKelas,txtJenisKlamin,txtAgama,txtAlamat,txtNoHp;
    ImageView img_ProfileSiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_siswa);
        sessionManager = new SessionManager(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        HashMap<String,String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.IdUser);
        String Nama = user.get(sessionManager.NamaUser);

        txtNama = findViewById(R.id.txtNama);
        txtNISN = findViewById(R.id.txtNISN);
        txtKelas = findViewById(R.id.txtKelas);
        txtJenisKlamin = findViewById(R.id.txtJenisKlamin);
        txtAgama = findViewById(R.id.txtAgama);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtNoHp = findViewById(R.id.txtNoHp);


        img_ProfileSiswa = findViewById(R.id.img_ProfileSiswa);
//        Glide.with(this).load("https://picsum.photos/200/300").fitCenter().centerCrop().into(img_ProfileSiswa);


        txtNama.setText(String.valueOf(Nama));
        txtNISN.setText(String.valueOf(id));
        txtKelas.setText(String.valueOf("3-B"));
        txtJenisKlamin.setText(String.valueOf("Laki - Laki"));
        txtAgama.setText(String.valueOf("Kristen"));
        txtAlamat.setText(String.valueOf("Jln.Dr. Wem Tehupeiory"));
        txtNoHp.setText(String.valueOf("081332123123123"));
        findViewById(R.id.btn_Kembali_Profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DashboardSiswa.class);
                startActivity(i);
            }
        });
    }


}