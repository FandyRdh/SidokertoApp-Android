package com.prakpm2.pa_sidokerto_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar1;
    int progressStatus = 0;
    //    TextView textView1, textView2;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Logika Loading
        progressBar1=(ProgressBar)findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100)
                {
                    progressStatus += 2;
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            progressBar1.setProgress(progressStatus);
//                            textView2.setText(progressStatus + "%");
                        }
                    });
                    try
                    {
//                       Atur Kecepatan Loading
                        Thread.sleep(30);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (progressStatus==100)
                {
                    Intent i = new Intent(SplashActivity.this, onboard_1.class);
                    startActivity(i);
                }

                finish();
            }
        }).start();
    }
}

