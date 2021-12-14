package com.prakpm2.pa_sidokerto_app.Siswa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import com.prakpm2.pa_sidokerto_app.R;

public class AdapterUjianSiswa extends RecyclerView.Adapter<AdapterUjianSiswa.MyViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterUjianSiswa(UjianSiswa mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ujian_siswa, null);
        return new AdapterUjianSiswa.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterUjianSiswa.MyViewHolder holder, int position) {
        holder.txtNamaUjian.setText(list_data.get(position).get("JUDUL_UJIAN"));
        holder.txtJenisUjian.setText(list_data.get(position).get("JENIS_UJIAN"));
        holder.txtJadwalUjian.setText(list_data.get(position).get("JADAWAL_MULAI"));
        holder.txtNamaMapel.setText(list_data.get(position).get("NAMA_MAPEL"));
        holder.txtNamaPengajar.setText(list_data.get(position).get("NAMA_KRY"));
        holder.lyListUjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), KonfirmasiMulaiUjian.class);
                intent.putExtra("JUDUL_UJIAN",String.valueOf(list_data.get(position).get("JUDUL_UJIAN")));
                intent.putExtra("JENIS_UJIAN",String.valueOf(list_data.get(position).get("JENIS_UJIAN")));
                intent.putExtra("JADAWAL_MULAI",String.valueOf(list_data.get(position).get("JADAWAL_MULAI")));
                intent.putExtra("NAMA_MAPEL",String.valueOf(list_data.get(position).get("NAMA_MAPEL")));
                intent.putExtra("NAMA_KRY",String.valueOf(list_data.get(position).get("NAMA_KRY")));
                intent.putExtra("JADAWAL_SELESAI",String.valueOf(list_data.get(position).get("JADAWAL_SELESAI")));
                intent.putExtra("ID_UJIAN",String.valueOf(list_data.get(position).get("ID_UJIAN")));
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
        TextView  txtNamaUjian,txtJenisUjian,txtJadwalUjian,txtNamaMapel,txtNamaPengajar;
        ConstraintLayout lyListUjian;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNamaUjian = itemView.findViewById(R.id.txt_BS_Judul);
            txtJenisUjian = itemView.findViewById(R.id.txt_BS_JenisUjian);
            txtJadwalUjian = itemView.findViewById(R.id.txt_BS_TglBuat);
            txtNamaMapel = itemView.findViewById(R.id.txt_BS_Mapel);
            txtNamaPengajar = itemView.findViewById(R.id.txt_BS_Pembuat);
            lyListUjian = itemView.findViewById(R.id.ly_BankSoal);

        }
    }
}
