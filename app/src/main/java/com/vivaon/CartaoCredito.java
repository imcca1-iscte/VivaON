package com.vivaon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.cardform.view.CardEditText;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.CvvEditText;
import com.braintreepayments.cardform.view.ExpirationDateEditText;
import com.braintreepayments.cardform.view.MobileNumberEditText;
import com.vivaon.R;
import com.vivaon.Sucess;

import java.util.HashMap;
import java.util.Map;


public class CartaoCredito extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;
    String URL ="http://192.168.1.71/passes/regcards.php";
    CardEditText CardN;
    ExpirationDateEditText ExDate;
    CvvEditText ExCVV;
    MobileNumberEditText ExMob;
    String cardnumber,expirationdate,CVV,phonenumber;
    String email;
    String compra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_credito);

        email=getIntent().getExtras().getString("Value");
        compra=getIntent().getExtras().getString("Tipo");


        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        CardN=cardForm.getCardEditText();
        ExDate= cardForm.getExpirationDateEditText();
        ExCVV=cardForm.getCvvEditText();
        ExMob=cardForm.getMobileNumberEditText();


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(true)
                .setup(CartaoCredito.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(CartaoCredito.this);
                    alertBuilder.setTitle("Confirme os dados");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            cardnumber=CardN.getText().toString().trim();
                            expirationdate=ExDate.getText().toString().trim();
                            CVV=ExCVV.getText().toString().trim();
                            phonenumber=ExMob.getText().toString().trim();
                            if(!cardnumber.equals("") && !expirationdate.equals("") && !CVV.equals("") && !phonenumber.equals("")){

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("sucess")) {
                                            progress();
                                            buy.setClickable(true);
                                        } else if (response.equals("failure"))  {
                                            Toast.makeText(CartaoCredito.this, "Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> data = new HashMap<>();
                                        data.put("cardnumber",cardnumber);
                                        data.put("expirationdate",expirationdate);
                                        data.put("CVV",CVV);
                                        data.put("phonenumber",phonenumber);
                                        data.put("tipocompra",compra);
                                        return data;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);



                            }

                        }


                    });
                    alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(CartaoCredito.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void progress() {
        Intent intent= new Intent(this, Progress.class);
        intent.putExtra("Value",email);
        startActivity(intent);


    }


}
