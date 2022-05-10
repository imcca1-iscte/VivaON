package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Pagamento extends AppCompatActivity {

    ImageButton visa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        visa= findViewById(R.id.imageButton);




        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openCartaoCredito();

            }
        });

    }

    private void openCartaoCredito() {
        Intent intent = new Intent(this,CartaoCredito.class
        );
        startActivity(intent);
    }
}




