package com.prakpm2.pa_sidokerto_app.Guru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class addSoal extends AppCompatActivity {
    EditText etPilihan1Guru,etPilihan2Guru,etPilihan3Guru,etPilihan4Guru,txtsoalguru;
    TextView txtNoSoalGuru;
    Button btnSimpan1,btnHapus1;
    Spinner dropdown;

    StringRequest stringRequest,stringRequest2;
    RequestQueue requestQueue;


    private String URL = "http://192.168.43.153/api/PA_Sidokreto_App/Guru/InsertData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_soal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtsoalguru = findViewById(R.id.txtsoalguru);
        etPilihan1Guru = findViewById(R.id.etPilihan1Guru);
        etPilihan2Guru = findViewById(R.id.etPilihan2Guru);
        etPilihan3Guru = findViewById(R.id.etPilihan3Guru);
        etPilihan4Guru = findViewById(R.id.etPilihan4Guru);
        txtNoSoalGuru = findViewById(R.id.txtNoSoalGuru);
        dropdown = findViewById(R.id.spinner1);
        btnSimpan1 = findViewById(R.id.btnSimpan1);

        Intent result = getIntent();
        String ID_PAKET = result.getStringExtra("ID_PAKET");

        String[] items = new String[]{"A", "B", "C","D"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        btnSimpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Soal = txtsoalguru.getText().toString().trim();
                String PilA = etPilihan1Guru.getText().toString().trim();
                String PilB = etPilihan2Guru.getText().toString().trim();
                String PilC = etPilihan3Guru.getText().toString().trim();
                String PilD = etPilihan4Guru.getText().toString().trim();

                String JawabanSoal = "";
                String jawabanbenar = dropdown.getSelectedItem().toString();
                if (jawabanbenar.equals("A")){
                    JawabanSoal = etPilihan1Guru.getText().toString().trim();
                }else if (jawabanbenar.equals("B")){
                    JawabanSoal = etPilihan2Guru.getText().toString().trim();
                }else if (jawabanbenar.equals("C")){
                    JawabanSoal = etPilihan3Guru.getText().toString().trim();
                }else if (jawabanbenar.equals("D")){
                    JawabanSoal = etPilihan4Guru.getText().toString().trim();
                }
//                System.out.println(ID_PAKET);
//                System.out.println(Soal);
//                System.out.println(PilA);
//                System.out.println(PilB);
//                System.out.println(PilC);
//                System.out.println(PilD);
//                System.out.println(JawabanSoal);
                InsertData(ID_PAKET,Soal,PilA,PilB,PilC,PilD,JawabanSoal);
                Toast.makeText(addSoal.this, "Soal Ujian Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(addSoal.this.getApplicationContext(), DetailBankSoal.class);
                intent.putExtra("ID_PAKET",ID_PAKET);
                ((Activity) addSoal.this).startActivityForResult(intent,1);
                addSoal.this.startActivity(intent);
                finish();
            }

        });

    }
    public void InsertData(String idp,String so,String a,String b,String c,String d,String key){
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                data.put("ID_PAKET",idp);
                data.put("Soal",so);
                data.put("PilA",a);
                data.put("PilB",b);
                data.put("PilC",c);
                data.put("PilD",d);
                data.put("JawabanSoal",key);
                return data;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}