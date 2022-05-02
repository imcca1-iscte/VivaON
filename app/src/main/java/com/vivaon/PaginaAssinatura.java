package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class PaginaAssinatura extends AppCompatActivity {

    private ImageButton buttonBack;
    private ImageButton buttonGConta;
    private Button pacotefamiliar1,pacotefamiliar2,ilimitado,estudante,cultura;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura);
        buttonBack= (ImageButton)findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGestaoConta();
            }
        });
        buttonGConta = (ImageButton)findViewById(R.id.buttonGestaoConta);
        buttonGConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGestaoConta();
            }
        });
        pacotefamiliar1=findViewById(R.id.pacotefamiliar1);
        pacotefamiliar2=findViewById(R.id.pacotefamiliar2);
        estudante=findViewById(R.id.pacoteestudante);
        ilimitado=findViewById(R.id.pacoteilimitado);
        cultura=findViewById(R.id.pacotecultura);

        pacotefamiliar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        pacotefamiliar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        estudante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        ilimitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });

        cultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPay();
            }
        });









    }

    private void openPay() {
        Intent intent=new Intent(this,MetodosPagamento.class);
        startActivity(intent);
    }

    public void openGestaoConta(){
        Intent intent=new Intent(this,PaginaGestaoConta.class);
        startActivity(intent);
    }

}