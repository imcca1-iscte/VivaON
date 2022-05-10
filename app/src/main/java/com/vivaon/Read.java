package com.vivaon;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.Adapter;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class Read extends AppCompatActivity {



    TextView t;
    ListView list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        new Connection().execute();

       list=findViewById(R.id.list);
       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
       list.setAdapter(adapter);
    }

    class Connection extends AsyncTask<String,String,String>{



        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String host = "http://192.168.1.72/passes/ler.php";
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
                t.setText("erro connection");
        }
            return result;
        }

        protected void onPostExecute(String result){

            try {
                JSONObject json= new JSONObject(result);
                int success = json.getInt("success");
                if(success==1)
                {

                    JSONArray viagens = json.getJSONArray("historico");
                    for(int i=0; i< viagens.length(); i++){
                        JSONObject viagem = viagens.getJSONObject(i);
                        String estacao_partida = viagem.getString("estacao_partida");
                        String estacao_chegada = viagem.getString("estacao_chegada");
                        String hora_partida = viagem.getString("hora_partida");
                        String hora_chegada= viagem.getString("hora_chegada");
                        String line= estacao_partida+ "," + hora_partida +"," +estacao_chegada+ "," + hora_chegada;
                        adapter.add(line);
                    }
                }
                else {
                    t.setText("nao e");
                }
            } catch (JSONException e) {
                t.setText("json");
            }
        }
    }




}