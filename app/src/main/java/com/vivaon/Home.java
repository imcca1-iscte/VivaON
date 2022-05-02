package com.vivaon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;






public class Home extends AppCompatActivity {

    public static final String Erro = "Cartão não detetado";
    public static final String Lido = "Leitura feita";

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    Tag myTag;
    Context context;
    TextView nfc_content;
    Button utilizar;
    Button carregar;
    Button remover;
    ImageView code;
    ImageButton buttonSearch;
    ImageButton buttonGConta;
    ImageButton buttonMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nfc_content = (TextView) findViewById(R.id.textView);
        utilizar = findViewById(R.id.button3);
        carregar= findViewById(R.id.button6);
        remover= findViewById(R.id.button5);
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
            @Override
            public void onClick(View v) {
                String link = "http://192.168.1.72/passes/carregar.php";
                new updateData().execute(link);
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "http://192.168.1.72/passes/remover.php";
                new updateData().execute(link);
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

        String text = "";
        //String tagId = new String(msgs[0].getRecords()[0].getType());

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("Unsupported Encoding", e.toString());
        }
        nfc_content.setText("Cartão lido com sucesso: ");

        code = findViewById(R.id.imageView);
        String finalText = text;


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

    private void readMode() {
        nfcAdapter.disableForegroundDispatch(this);
    }
}

