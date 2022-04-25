package com.vivaon;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListMaps extends AppCompatActivity {

    ListView listView;
    String[] MapsNames={"Carris","CP","Fertagus","Metro","Transtejo","TST","Vimeca"};
    int[] MapsImages= {R.drawable.carris,R.drawable.cp,R.drawable.fertagus,R.drawable.metro,R.drawable.transtejo,R.drawable.tst,R.drawable.vimeca};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_maps);

        listView=findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();



    }

    private class CustomAdapter {








    }
}