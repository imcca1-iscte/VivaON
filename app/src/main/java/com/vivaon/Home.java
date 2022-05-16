package com.vivaon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;





public class Home extends AppCompatActivity {

    public static final String Erro = "Cartão não detetado";
    public static final String Lido = "Leitura feita";

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    Tag myTag;
    Context context;

    TextView nfc_content2;
    TextView nfc_content3;

    Button utilizar;
    Button carregar;
    Button remover;
    ImageView code;
    ImageButton buttonSearch;
    ImageButton buttonGConta;
    ImageButton buttonMaps;
    ImageButton logOff;
    String text;
    String finalText;
    String id;
    String validade;
    //Connection c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nfc_content2 = (TextView) findViewById(R.id.textView9);

        utilizar = findViewById(R.id.button3);
        carregar = findViewById(R.id.button6);
        remover = findViewById(R.id.button5);
        //c= new Connection(this);


        context = this;
        Toast.makeText(this, "Encoste o Passe à traseira do telemóvel", Toast.LENGTH_SHORT).show();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Este dispositivo não suporta NFC", Toast.LENGTH_SHORT).show();
            finish();
        }
        try {
            readfromIntent(getIntent());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);


        carregar.setOnClickListener(new View.OnClickListener() {

            String[] sep;


            @Override
            public void onClick(View v) {
                String link = "http://169.254.56.237/passes/carregar.php";
                sep = text.split(",");
                id = sep[0];


                if (!id.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
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
                            data.put("id", id);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }


                new updateData().execute(link);

                openMetodosPagamento();
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sep;

                sep = text.split(",");
                id = sep[0];
                String link = "http://169.254.56.237/passes/remover.php";
                if (!id.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
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
                            data.put("id", id);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }

               // new updateData().execute(link);
                Toast.makeText(getApplicationContext(), "Passe removido com sucesso",
                        Toast.LENGTH_LONG).show();

            }
        });

    }




    private void readfromIntent(Intent intent) throws SQLException, ClassNotFoundException {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void openMetodosPagamento() {
        Intent intent = new Intent(this, MetodosPagamento.class);
        startActivity(intent);


    }
    private void openGConta() {
        Intent intent = new Intent(this, PaginaGestaoConta.class);
        startActivity(intent);
    }

    private void openMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    private void buildTagViews(NdefMessage[] msgs) throws ClassNotFoundException, SQLException {
        if (msgs == null || msgs.length == 0) return;

        text = "";
        //String tagId = new String(msgs[0].getRecords()[0].getType());

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("Unsupported Encoding", e.toString());
        }






        code = findViewById(R.id.imageView);
        finalText = text;

        //c.execute(id);

        String[] sep;

        sep = text.split(",");
        validade = sep[1];

        nfc_content2.setText("Validade: " +validade);




        utilizar.setOnClickListener(new View.OnClickListener() {
            private Object BitMatrix;

            @Override
            public void onClick(View v) {

                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    com.google.zxing.common.BitMatrix matrix = writer.encode(finalText, BarcodeFormat.QR_CODE, 500, 500);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    code.setImageBitmap(bitmap);


                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

        });

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
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        try {
            readfromIntent(intent);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }

    }



      /* class Connection extends AsyncTask<String,Void,String>{

           String [] sep = text.split(",");
           String validade  = sep[1];
            Context ctx;
           Connection(Context ctx)
           {
               this.ctx =ctx;
           }
           @Override
           protected void onPreExecute() {

           }
           @Override
           protected String doInBackground(String... params) {

               String Hurl = "http://192.168.1.90/passes/ler3Nome.php";


               String nome = params[0];

               try {
                   URL url = new URL(Hurl);
                   HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                   httpURLConnection.setRequestMethod("POST");
                   httpURLConnection.setDoOutput(true);
                   httpURLConnection.setDoInput(true);
                   OutputStream outputStream = httpURLConnection.getOutputStream();
                   BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                   String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(nome, "UTF-8");
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
                  nfc_content3.setText(result);
                  nfc_content2.setText(validade);
               }
           }


       }*/







    private void readMode() {
        nfcAdapter.disableForegroundDispatch(this);
    }
}

