package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MetodosPagamento extends AppCompatActivity {

    private Button cartao;
    private Button paypal;
    private String email;
    private String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_pagamento);
        cartao=findViewById(R.id.button4);
        paypal=findViewById(R.id.button8);
        cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCartao();
            }
        });

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPaypal();
            }
        });
    }

    private void OpenPaypal() {
        Intent intent= new Intent(this,Pagamento.class);
        startActivity(intent);
    }

    private void OpenCartao() {
        email= getIntent().getExtras().getString("Value");
        tipo=getIntent().getExtras().getString("Tipo");

        Intent intent= new Intent(this,CartaoCredito.class);
        intent.putExtra("Value",email);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }



}