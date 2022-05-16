package com.vivaon;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Progress extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private String email;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        email=getIntent().getExtras().getString("Value");
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLoadingText = (TextView) findViewById(R.id.textView4);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                p();

            }
        }).start();
    }

    private void p() {
        Intent intent=new Intent(this, Sucess.class);
        intent.putExtra("Value",email);
        startActivity(intent);





    }
}

