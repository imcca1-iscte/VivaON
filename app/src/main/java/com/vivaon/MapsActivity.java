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
    EditText etSource,etDestination;
    Button btTrack;

    ArrayAdapter<String> adapter;
    ListView lista;


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
                String origem = etSource.getText().toString().trim();
                String destino = etDestination.getText().toString().trim();

                //Check condition
                if (origem.equals("") && destino.equals("")) {
                    //When both value blank
                    Toast.makeText(getApplicationContext()
                            , "Enter both location", Toast.LENGTH_SHORT).show();
                } else {
                    //When both value fill
                    // Display Track
                    DisplayTrack(origem,destino);
                }
            }
        });

    }

    private void DisplayTrack(String origem, String destino) {

        String host = "http://192.168.1.72/passes/ler2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, host, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("origem", origem);
                data.put("destino", destino);


                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        new updateData().execute(host);
        new Connection().execute();
        lista= findViewById(R.id.lista);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lista.setAdapter(adapter);
        openHorarios();

    }

    private void openHorarios() {
        Intent intent = new Intent(this, Horario.class);
        startActivity(intent);
    }


    class Connection extends AsyncTask<String,String,String> {




            @Override
            protected String doInBackground(String... strings) {
                String result = "";
                String host = "http://192.168.1.72/passes/ler2.php";
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(URI.create(host));
                    HttpResponse response = client.execute(request);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuilder stringBuilder= new StringBuilder();
                    String line = "";

                    while((line = reader.readLine()) != null){
                        stringBuilder.append(line);
                        break;
                    }
                    reader.close();
                    result= stringBuilder.toString();
                }catch (Exception e){

                }
                return result;
            }

            protected void onPostExecute(String result){

                try {
                    JSONObject json= new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1)
                    {

                        JSONArray viagens = json.getJSONArray("horario");
                        for(int i=0; i< viagens.length(); i++){
                            JSONObject viagem = viagens.getJSONObject(i);
                            String origem = viagem.getString("origem");
                            String destino = viagem.getString("destino");
                            String hora_origem = viagem.getString("hora_origem");
                            String hora_destino= viagem.getString("hora_destino");
                            String line= "Próximos horários:" + hora_origem;
                            adapter.add(line);
                        }
                    }
                    else {

                    }
                } catch (JSONException e) {

                }
            }
        }
    public class updateData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;

            try {
                URL url;
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                } else {
                    InputStream err = conn.getErrorStream();
                }
                return "Done";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }
    }
