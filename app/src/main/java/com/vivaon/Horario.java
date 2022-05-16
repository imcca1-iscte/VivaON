package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Horario extends AppCompatActivity {


    ArrayAdapter<String> adapter;
    ListView lista;
    private String ResponseString;
    TextView t;
    String origem,destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);
        origem=MapsActivity.getOrigem();
        destino= MapsActivity.getDestino();
        Connection c= new Connection(this);
        c.execute(origem,destino);



        /*lista = (ListView) findViewById(R.id.lista);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lista.setAdapter(adapter);*/



    }
        class Connection extends AsyncTask<String,Void, String> {

            AlertDialog alertDialog;
            Context ctx;
            Connection(Context ctx)
            {
                this.ctx =ctx;
            }
            @Override
            protected void onPreExecute() {
                alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Próximo Horario:");
            }
            @Override
            protected String doInBackground(String... params) {

                String Hurl = "http://192.168.1.90/passes/ler2.php";


                String origem = params[0];
                String destino = params[1];
                try {
                    URL url = new URL(Hurl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("origem", "UTF-8") + "=" + URLEncoder.encode(origem, "UTF-8") + "&" +
                            URLEncoder.encode("destino", "UTF-8") + "=" + URLEncoder.encode(destino, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
            @Override
            protected void onPostExecute(String result) {
                if(result.equals("Registration Success..."))
                {
                    Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                }
                else
                {
                    alertDialog.setMessage(result);
                    alertDialog.show();
                }
            }

        }

    }




