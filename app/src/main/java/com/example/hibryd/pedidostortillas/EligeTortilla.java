package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeTortilla extends AppCompatActivity {

    String[] nombre = {"Tortilla","Kas"};
    Integer[] imageId = {R.drawable.cola,R.drawable.kas};
    private Datos[] arrayParametros = new Datos[3];


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);
        Bundle bnd = getIntent().getExtras();
        arrayParametros = (Datos[]) bnd.get("array");
        String s = arrayParametros[0].getIdentificador();
        Log.e("tu madre",s);


        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        AdaptadorTortillas adapter = new AdaptadorTortillas(this,nombre,imageId);
        mySpinner.setAdapter(adapter);
    }
}
