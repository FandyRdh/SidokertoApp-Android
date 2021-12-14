package com.prakpm2.pa_sidokerto_app.Siswa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.prakpm2.pa_sidokerto_app.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterNIlai extends RecyclerView.Adapter<AdapterNIlai.MyViewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterNIlai(NilaiUjian mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nilai_siswa, null);
        return new AdapterNIlai.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNIlai.MyViewHolder holder, int position) {
        holder.txtJudulujian.setText(list_data.get(position).get("JUDUL_UJIAN"));
        holder.txtnmmapel.setText(list_data.get(position).get("NAMA_MAPEL"));
        holder.txtnmpengajar.setText(list_data.get(position).get("NAMA_KRY"));
        holder.txtjenisujian.setText(list_data.get(position).get("JENIS_UJIAN"));
        holder.txtNilaiUjian.setText(list_data.get(position).get("NILAI_UJIAN"));

        String a = String.valueOf(list_data.get(position).get("JUDUL_UJIAN"));
        String b = String.valueOf(list_data.get(position).get("NAMA_MAPEL"));
        String c = String.valueOf(list_data.get(position).get("NAMA_KRY"));
        String d = String.valueOf(list_data.get(position).get("JENIS_UJIAN"));
        String e = String.valueOf(list_data.get(position).get("NILAI_UJIAN"));
        String f = String.valueOf(list_data.get(position).get("id_ujian"));

        //1 = lewat
        if (list_data.get(position).get("openDetail").equalsIgnoreCase("1")){
            holder.lyListUjianNilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), DetailJawaban.class);
                    intent.putExtra("JUDUL_UJIAN",a);
                    intent.putExtra("NAMA_MAPEL",b);
                    intent.putExtra("NAMA_KRY",c);
                    intent.putExtra("JENIS_UJIAN",d);
                    intent.putExtra("NILAI_UJIAN",e);
                    intent.putExtra("id_ujian",f);

                    ((Activity) context).startActivityForResult(intent,1);
                    context.startActivity(intent);
                }
            });
        }else{
            holder.lyListUjianNilai.setBackgroundColor(Color.parseColor("#ffc2c2"));
            holder.lyListUjianNilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Ujian Masih Berlangsung, Jawaban masih di kunci", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudulujian,txtnmmapel,txtnmpengajar,txtjenisujian,txtNilaiUjian;
        ConstraintLayout lyListUjianNilai;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtJudulujian = itemView.findViewById(R.id.txt_BS_Judul);
            txtnmmapel = itemView.findViewById(R.id.txt_BS_Mapel);
            txtnmpengajar = itemView.findViewById(R.id.txt_BS_Pembuat);
            txtjenisujian = itemView.findViewById(R.id.txt_BS_JenisUjian);
            txtNilaiUjian = itemView.findViewById(R.id.txt_BS_TglBuat);
            lyListUjianNilai = itemView.findViewById(R.id.ly_BankSoal);
        }
    }
}
