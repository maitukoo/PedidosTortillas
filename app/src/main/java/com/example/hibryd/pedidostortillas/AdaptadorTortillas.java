package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hibryd on 08/12/2016.
 */

public class AdaptadorTortillas extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public AdaptadorTortillas (Activity context,String[] web,Integer[] imageId){
        super(context, R.layout.spinnerrow,web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinnerrow, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.NombreBebida);
        textView.setText(web[position]);

        ImageView imageView = (ImageView)row.findViewById(R.id.icon);
        imageView.setImageResource(imageId[position]);

        return row;
    }

}
