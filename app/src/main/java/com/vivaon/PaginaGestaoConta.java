package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PaginaGestaoConta extends AppCompatActivity {

    private Button buttonAssinatura;
    private Button historico;
    private ImageButton buttonHome;
    private ImageButton buttonInfo;
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
        buttonInfo= findViewById(R.id.buttonSearch);
        historico= findViewById(R.id.button8);



        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                read();








            }
        });

        buttonAssinatura.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                openAssinatura();
            }
        });

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInfo();

            }
        });




    }

    private void read() {
        Intent intent=new Intent(this, Read.class);
        startActivity(intent);
    }


    public void openAssinatura(){
        Intent intent=new Intent(this, PaginaAssinatura.class);
        startActivity(intent);
    }

    public void openHome(){
        Intent intent=new Intent(this, Home.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}