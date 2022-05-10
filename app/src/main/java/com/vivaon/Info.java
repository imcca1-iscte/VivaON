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

    Button fertagusH;
    Button fertagusM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        fertagusH= findViewById(R.id.button7);
        fertagusM= findViewById(R.id.button4);

        fertagusH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.fertagus.pt/pt/horarios/fertagus");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        fertagusM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFertagus();


            }
        });


    }

    private void openFertagus() {

            Intent intent = new Intent(this, InfoFertagus.class);
            startActivity(intent);


    }


}