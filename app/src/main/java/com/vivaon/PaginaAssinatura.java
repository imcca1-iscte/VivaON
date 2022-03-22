package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class PaginaAssinatura extends AppCompatActivity {

    private ImageButton buttonBack;
    private ImageButton buttonGConta;

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
    }

    public void openGestaoConta(){
        Intent intent=new Intent(this,PaginaGestaoConta.class);
        startActivity(intent);
    }

}