package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MetodosPagamento extends AppCompatActivity {

    private Button cartao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_pagamento);
        cartao=findViewById(R.id.button4);
        cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCartao();
            }
        });
    }

    private void OpenCartao() {
        Intent intent= new Intent(this,CartaoCredito.class);
        startActivity(intent);
    }



}