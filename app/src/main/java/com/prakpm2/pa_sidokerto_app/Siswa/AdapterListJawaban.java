    package com.prakpm2.pa_sidokerto_app.Siswa;


    import android.content.Context;
    import android.graphics.Color;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.constraintlayout.widget.ConstraintLayout;
    import androidx.recyclerview.widget.RecyclerView;

    import com.prakpm2.pa_sidokerto_app.R;

    import java.util.ArrayList;
    import java.util.HashMap;

    public class AdapterListJawaban extends RecyclerView.Adapter<AdapterListJawaban.MyViewHolder>{
        Context context;
        ArrayList<HashMap<String, String>> list_data;

        public AdapterListJawaban(DetailJawaban mainActivity, ArrayList<HashMap<String, String>> list_data) {
            this.context = mainActivity;
            this.list_data = list_data;
        }


        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowjawabanujian, null);
            return new AdapterListJawaban.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterListJawaban.MyViewHolder holder, int position) {
    //        set
            String txtNomor = list_data.get(position).get("txtNomor");
            String JENIS_UJIAN = list_data.get(position).get("JENIS_UJIAN");
            String KUNCI = list_data.get(position).get("KUNCI");
            String JAWABAN_UJIAN = list_data.get(position).get("JAWABAN_UJIAN");


            holder.txtNomorjawaban.setText(txtNomor);
            holder.txtJenisUjianJawaban.setText(JENIS_UJIAN);
            holder.TxtKunciJawaban.setText(KUNCI);
            holder.txtJawabanSiswa.setText(JAWABAN_UJIAN);

            holder.txtSoalUjianJawaban.setText(list_data.get(position).get("SOAL_UJIAN"));
            if (KUNCI.equalsIgnoreCase(JAWABAN_UJIAN)){
                holder.lyListJawaban.setBackgroundColor(Color.parseColor("#cef5a6"));
            }else{
                holder.lyListJawaban.setBackgroundColor(Color.parseColor("#ffc2c2"));
            }
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtNomorjawaban,txtSoalUjianJawaban,txtJenisUjianJawaban,TxtKunciJawaban,txtJawabanSiswa;
            ConstraintLayout lyListJawaban;
            public MyViewHolder(View itemView) {
                super(itemView);
                txtNomorjawaban = itemView.findViewById(R.id.txt_BS_Judul);
                txtSoalUjianJawaban = itemView.findViewById(R.id.txtSoalUjianJawaban);
                txtJenisUjianJawaban = itemView.findViewById(R.id.txtJenisUjianJawaban);
                lyListJawaban = itemView.findViewById(R.id.lyListJawaban);
                TxtKunciJawaban = itemView.findViewById(R.id.TxtKunciJawaban);
                txtJawabanSiswa = itemView.findViewById(R.id.txtJawabanSiswa);



            }
        }
    }
