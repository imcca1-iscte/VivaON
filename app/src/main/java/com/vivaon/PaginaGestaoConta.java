package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PaginaGestaoConta extends AppCompatActivity {

    private Button buttonAssinatura;
    private ImageButton buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestaoconta);
        buttonAssinatura=(Button)findViewById(R.id.buttonAssin);
        buttonHome= (ImageButton) findViewById(R.id.buttonHome);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        buttonAssinatura.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                openAssinatura();
            }
        });
    }

    public void openAssinatura(){
        Intent intent=new Intent(this, PaginaAssinatura.class);
        startActivity(intent);
    }

    public void openHome(){
        Intent intent=new Intent(this, Home.class);
        startActivity(intent);
    }

}