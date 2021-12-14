package com.prakpm2.pa_sidokerto_app.Guru;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.prakpm2.pa_sidokerto_app.R;
import com.prakpm2.pa_sidokerto_app.Siswa.AdapterUjianSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.DetailSoal;
import com.prakpm2.pa_sidokerto_app.Siswa.DetailUjianSiswa;
import com.prakpm2.pa_sidokerto_app.Siswa.UjianSiswa;

import java.util.ArrayList;
import java.util.HashMap;



public class AdapterDetailBankSoal extends RecyclerView.Adapter<AdapterDetailBankSoal.MyViewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterDetailBankSoal(DetailBankSoal mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detailbanksoal, null);
        return new AdapterDetailBankSoal.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDetailBankSoal.MyViewHolder holder, int position) {
        String ID_SOAL = String.valueOf(list_data.get(position).get("ID_SOAL"));
        String ID_PAKET = String.valueOf(list_data.get(position).get("ID_PAKET"));
        String NOMOR_URUT = String.valueOf(list_data.get(position).get("NOMOR_URUT"));
        String SOAL_UJIAN = String.valueOf(list_data.get(position).get("SOAL_UJIAN"));
        String PILIHAN_1 = String.valueOf(list_data.get(position).get("PILIHAN_1"));
        String PILIHAN_2 = String.valueOf(list_data.get(position).get("PILIHAN_2"));
        String PILIHAN_3 = String.valueOf(list_data.get(position).get("PILIHAN_3"));
        String PILIHAN_4 = String.valueOf(list_data.get(position).get("PILIHAN_4"));
        String KUNCI = String.valueOf(list_data.get(position).get("KUNCI"));
        String ASSET_SOAL = String.valueOf(list_data.get(position).get("ASSET_SOAL"));
        String no = String.valueOf(list_data.get(position).get("no"));
        String JENIS_SOAL = String.valueOf(list_data.get(position).get("JENIS_SOAL"));

        holder.txt_DBS_NoSoal.setText("No."+no);
        holder.txt_DBS_JenisPaket.setText(JENIS_SOAL);
        holder.txt_DBS_SoalUjian.setText(SOAL_UJIAN);
        holder.txt_DBS_Pilihan_1.setText(PILIHAN_1);
        holder.txt_DBS_Pilihan_2.setText(PILIHAN_2);
        holder.txt_DBS_Pilihan_3.setText(PILIHAN_3);
        holder.txt_DBS_Pilihan_4.setText(PILIHAN_4);
        holder.txt_DBS_Pilihan_Kunci.setText(KUNCI);
        holder.lyListJawaban1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailSoalGuru.class);
                intent.putExtra("ID_PAKET",String.valueOf(list_data.get(position).get("ID_PAKET")));
                intent.putExtra("ID_SOAL",String.valueOf(list_data.get(position).get("ID_SOAL")));
                intent.putExtra("no",String.valueOf(list_data.get(position).get("no")));
                intent.putExtra("SOAL_UJIAN",String.valueOf(list_data.get(position).get("SOAL_UJIAN")));
                intent.putExtra("PILIHAN_1",String.valueOf(list_data.get(position).get("PILIHAN_1")));
                intent.putExtra("PILIHAN_2",String.valueOf(list_data.get(position).get("PILIHAN_2")));
                intent.putExtra("PILIHAN_3",String.valueOf(list_data.get(position).get("PILIHAN_3")));
                intent.putExtra("PILIHAN_4",String.valueOf(list_data.get(position).get("PILIHAN_4")));
                intent.putExtra("KUNCI",String.valueOf(list_data.get(position).get("KUNCI")));
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
        TextView txt_DBS_NoSoal,txt_DBS_JenisPaket,txt_DBS_SoalUjian,txt_DBS_Pilihan_1,txt_DBS_Pilihan_2,txt_DBS_Pilihan_3,txt_DBS_Pilihan_4,txt_DBS_Pilihan_Kunci;
        ConstraintLayout lyListJawaban1;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_DBS_NoSoal = itemView.findViewById(R.id.txt_DBS_NoSoal);
            txt_DBS_JenisPaket = itemView.findViewById(R.id.txt_DBS_JenisPaket);
            txt_DBS_SoalUjian = itemView.findViewById(R.id.txt_DBS_SoalUjian);
            txt_DBS_Pilihan_1 = itemView.findViewById(R.id.txt_DBS_Pilihan_1);
            txt_DBS_Pilihan_2 = itemView.findViewById(R.id.txt_DBS_Pilihan_2);
            txt_DBS_Pilihan_3 = itemView.findViewById(R.id.txt_DBS_Pilihan_3);
            txt_DBS_Pilihan_4 = itemView.findViewById(R.id.txt_DBS_Pilihan_4);
            txt_DBS_Pilihan_Kunci = itemView.findViewById(R.id.txt_DBS_Pilihan_Kunci);
            lyListJawaban1 = itemView.findViewById(R.id.lyListJawaban1);
        }

    }


}
