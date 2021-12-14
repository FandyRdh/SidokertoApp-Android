package com.prakpm2.pa_sidokerto_app.Guru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.Siswa.AdapterUjianSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.DetailUjianSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.UjianSiswa;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterBankSoal extends RecyclerView.Adapter<AdapterBankSoal.MyViewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterBankSoal(BankSoal mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_banksoal, null);
        return new AdapterBankSoal.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterBankSoal.MyViewHolder holder, int position) {
        String ID_PAKET = String.valueOf(list_data.get(position).get("ID_PAKET"));
        String NAMA_KRY = String.valueOf(list_data.get(position).get("NAMA_KRY"));
        String NAMA_MAPEL = String.valueOf(list_data.get(position).get("NAMA_MAPEL"));
        String JUDUL_SOAL = String.valueOf(list_data.get(position).get("JUDUL_SOAL"));
        String JENIS_SOAL = String.valueOf(list_data.get(position).get("JENIS_SOAL"));
        String TGL_PEMBUATAN = String.valueOf(list_data.get(position).get("TGL_PEMBUATAN"));
        holder.txt_BS_Judul.setText(JUDUL_SOAL);
        holder.txt_BS_Mapel.setText(NAMA_MAPEL);
        holder.txt_BS_Pembuat.setText(NAMA_KRY);
        holder.txt_BS_JenisUjian.setText(JENIS_SOAL);
        holder.txt_BS_TglBuat.setText(TGL_PEMBUATAN);
        holder.ly_BankSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context.getApplicationContext(), DetailBankSoal.class);
               intent.putExtra("ID_PAKET",ID_PAKET);
               intent.putExtra("NAMA_KRY",NAMA_KRY);
               intent.putExtra("NAMA_MAPEL",NAMA_MAPEL);
               intent.putExtra("JUDUL_SOAL",JUDUL_SOAL);
               intent.putExtra("JENIS_SOAL",JENIS_SOAL);
               intent.putExtra("TGL_PEMBUATAN",TGL_PEMBUATAN);
               ((Activity) context).startActivityForResult(intent,1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_BS_Judul,txt_BS_Mapel,txt_BS_Pembuat,txt_BS_JenisUjian,txt_BS_TglBuat;
        ConstraintLayout ly_BankSoal;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_BS_Judul = itemView.findViewById(R.id.txt_BS_Judul);
            txt_BS_JenisUjian = itemView.findViewById(R.id.txt_BS_JenisUjian);
            txt_BS_Pembuat = itemView.findViewById(R.id.txt_BS_TglBuat);
            txt_BS_Mapel = itemView.findViewById(R.id.txt_BS_Mapel);
            txt_BS_TglBuat = itemView.findViewById(R.id.txt_BS_Pembuat);
            ly_BankSoal = itemView.findViewById(R.id.ly_BankSoal);
        }
    }
}
