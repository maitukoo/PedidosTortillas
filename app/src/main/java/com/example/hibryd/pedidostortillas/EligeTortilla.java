package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    String[] web = {"Cola","Kas"};
    Integer[] imageId = {R.drawable.cola,R.drawable.kas};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);


        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        AdaptadorTortillas adapter = new AdaptadorTortillas(this,web,imageId);
        mySpinner.setAdapter(adapter);


    }


}
