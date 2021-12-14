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
    
    import com.prakpm2.pa_sidokerto_app.R;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    
    public class AdapterListSoal extends RecyclerView.Adapter<AdapterListSoal.MyViewHolder> {
        Context context;
        ArrayList<HashMap<String, String>> list_data2;
    
        public AdapterListSoal(DetailUjianSiswa mainActivity, ArrayList<HashMap<String, String>> list_data) {
            this.context = mainActivity;
            this.list_data2 = list_data;
        }
    
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_soal, null);
            return new AdapterListSoal.MyViewHolder(view);
        }
    
        @Override
        public void onBindViewHolder(AdapterListSoal.MyViewHolder holder, int position) {
            String NomorSoal = list_data2.get(position).get("txtNomor");
            String txtSoal = list_data2.get(position).get("txtSoal");
    
            holder.txtNomor.setText(NomorSoal);
            holder.txtSoal.setText(txtSoal);
            holder.txtJenisUjian2.setText(list_data2.get(position).get("txtJenisUjian2"));
            holder.lyListSoal.setOnClickListener(v -> {
                Intent intent = new Intent(context.getApplicationContext(), DetailSoal.class);
                intent.putExtra("NomorSoal",String.valueOf(NomorSoal));
                intent.putExtra("Soal",String.valueOf(txtSoal));
                intent.putExtra("PILIHAN_1",String.valueOf(list_data2.get(position).get("PILIHAN_1")));
                intent.putExtra("PILIHAN_2",String.valueOf(list_data2.get(position).get("PILIHAN_2")));
                intent.putExtra("PILIHAN_3",String.valueOf(list_data2.get(position).get("PILIHAN_3")));
                intent.putExtra("PILIHAN_4",String.valueOf(list_data2.get(position).get("PILIHAN_4")));
                intent.putExtra("ID_SOAL",String.valueOf(list_data2.get(position).get("ID_SOAL")));
                intent.putExtra("ID_UJIAN",String.valueOf(list_data2.get(position).get("ID_UJIAN")));
                intent.putExtra("kunci",String.valueOf(list_data2.get(position).get("kunci")));
                intent.putExtra("jawaban_ujian",String.valueOf(list_data2.get(position).get("jawaban_ujian")));
                ((Activity) context).startActivityForResult(intent,1);
                context.startActivity(intent);
            });
    
        }
    
        @Override
        public int getItemCount() {
            return list_data2.size();
        }
    
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtNomor,txtSoal,txtJenisUjian2;
            ConstraintLayout lyListSoal;
    
            public MyViewHolder(View itemView) {
                super(itemView);
                txtNomor = itemView.findViewById(R.id.txt_BS_Judul);
                txtSoal = itemView.findViewById(R.id.txtSoalUjianJawaban);
                txtJenisUjian2 = itemView.findViewById(R.id.txtJenisUjianJawaban);
                lyListSoal = itemView.findViewById(R.id.lyListJawaban);
    
            }
        }
    }