package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaginaGestaoConta extends AppCompatActivity {

    private Button buttonAssinatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestaoconta);
        buttonAssinatura=(Button)findViewById(R.id.buttonAssin);
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

}