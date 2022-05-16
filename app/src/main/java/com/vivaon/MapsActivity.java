package com.vivaon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity {
    //Initialize variable
    EditText etSource, etDestination;
    Button btTrack;



    static String origem;
    static String destino;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_mapsactivity);
        //Assign variable
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);


        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get value from edit text
                origem = etSource.getText().toString().trim();
                destino = etDestination.getText().toString().trim();

                //Check condition
                if (origem.equals("") && destino.equals("")) {
                    //When both value blank
                    Toast.makeText(getApplicationContext()
                            , "Enter both location", Toast.LENGTH_SHORT).show();
                } else {
                    //When both value fill
                    // Display Track
                    openHorarios();

                }
            }
        });

    }


    private void openHorarios() {
        Intent intent = new Intent(this, Horario.class);
        startActivity(intent);


    }
    public static String getOrigem() {
        return origem;
    }

    public static String getDestino() {
        return destino;
    }
}
