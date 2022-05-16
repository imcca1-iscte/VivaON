package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Passes extends AppCompatActivity {

    private Button passe_418,navegante_metropolitano,navegante_municipal,social,sub_23;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passes);
        passe_418=findViewById(R.id.passe_418);
        navegante_metropolitano=findViewById(R.id.NaveganteMetropolitano);
        navegante_municipal=findViewById(R.id.NaveganteMunicipal);
        social=findViewById(R.id.social);
        sub_23=findViewById(R.id.sub23);
        email=getIntent().getExtras().getString("Value");

        passe_418.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });


        navegante_metropolitano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        navegante_municipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        sub_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

    }

    private void openPay() {
        Intent intent= new Intent(this,MetodosPagamento.class);
        intent.putExtra("Value",email);
        String tipo="passe";
        intent.putExtra("Tipo",tipo);
        startActivity(intent);

    }
}