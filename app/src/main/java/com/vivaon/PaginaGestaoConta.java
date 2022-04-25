package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaginaGestaoConta extends AppCompatActivity {

    private Button buttonAssinatura;
    private ImageButton buttonHome;
    private TextView textview3;
    private EditText editText;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestaoconta);
        buttonAssinatura=(Button)findViewById(R.id.buttonAssin);
        buttonHome= (ImageButton) findViewById(R.id.buttonHome);
        /*editText =(EditText) findViewById(R.id.etEmail);*/
        textview3=(TextView) findViewById(R.id.textView3);
        email=getIntent().getExtras().getString("Value");
        textview3.setText(email);



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