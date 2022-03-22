package com.vivaon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Company> {


    public ListAdapter(Context context, ArrayList<Company> companyArrayList){

        super(context,R.layout.list_item, companyArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Company company = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.logo_pic);
        TextView companyName = convertView.findViewById(R.id.companyName);

        imageView.setImageResource(company.imageId);
        companyName.setText(company.name);


        return convertView;
    }
}
