package com.example.hibryd.pedidostortillas;

import android.app.Activity;
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
    public View getView (int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.spinnerrow, null, true);
        TextView txtNombreBebida = (TextView) rowView.findViewById(R.id.NombreBebida);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.icon);
        txtNombreBebida.setText(web[position]);
        imagen.setImageResource(imageId[position]);
        return rowView;

    }

}
