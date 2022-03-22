package com.vivaon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapas.databinding.ActivityUserBinding;

public class CompanyActivity extends AppCompatActivity {

    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){


            int imageid = intent.getIntExtra("imageid",R.drawable.carris);
            binding.profileImage.setImageResource(imageid);


        }

    }
}