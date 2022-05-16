package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Info extends AppCompatActivity {


    Button fertagusM;
    Button carrisM;
    Button cpM;
    Button metroM;
    Button mtsM;
    Button transM;
    Button tstM;
    Button vimecaM;
    Button t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        fertagusM= findViewById(R.id.button4);
        carrisM=findViewById(R.id.button8);
        t=findViewById(R.id.button2);


       t.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openWrite();
           }
       });
        /*fertagusM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFertagus();


            }
        });
        vimecaM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVimeca();
            }
        });
        mtsM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMts();
            }
        });

        metroM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMetro();
            }
        });

        tstM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTst();
            }
        });

        transM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrans();
            }
        });
        cpM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCP();
            }
        });

        carrisM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarris();
            }
        });
*/
    }

    private void openWrite() {
        Intent intent = new Intent(this, Write.class);
        startActivity(intent);
    }

    private void openCarris() {
        Intent intent = new Intent(this, InfoCarris.class);
        startActivity(intent);
    }

    private void openCP() {
        Intent intent = new Intent(this, InfoCP.class);
        startActivity(intent);
    }

    private void openTrans() {
        Intent intent = new Intent(this, InfoTrans.class);
        startActivity(intent);
    }

    private void openTst() {
        Intent intent = new Intent(this, InfoTST.class);
        startActivity(intent);
    }

    private void openMetro() {
        Intent intent = new Intent(this, InfoMetro.class);
        startActivity(intent);
    }

    private void openMts() {
        Intent intent = new Intent(this, InfoMTS.class);
        startActivity(intent);
    }

    private void openVimeca() {
        Intent intent = new Intent(this, InfoVimeca.class);
        startActivity(intent);
    }

    private void openFertagus() {

            Intent intent = new Intent(this, InfoFertagus.class);
            startActivity(intent);


    }


}