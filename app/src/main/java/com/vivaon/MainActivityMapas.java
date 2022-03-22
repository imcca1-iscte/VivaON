package com.vivaon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapas.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivityMapas extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.carris,R.drawable.cp,R.drawable.fertagus,R.drawable.metro,R.drawable.transtejo,
                R.drawable.tst,R.drawable.vimeca};
        String[] name = {"Carris","CP","Fertagus","Metro","Transtejo","TST","Vimeca"};

        ArrayList<Company> companyArrayList = new ArrayList<>();

        for(int i = 0;i< imageId.length;i++){

            Company company = new Company(name[i],imageId[i]);
            companyArrayList.add(company);

        }


        ListAdapter listAdapter = new ListAdapter(MainActivityMapas.this, companyArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivityMapas.this, CompanyActivity.class);
                i.putExtra("name",name[position]);
                i.putExtra("imageid",imageId[position]);
                startActivity(i);

            }
        });

    }
}