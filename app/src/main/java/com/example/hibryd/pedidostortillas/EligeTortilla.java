package com.example.hibryd.pedidostortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeTortilla extends AppCompatActivity {
    private String[] tortillas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);

        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinnerrow, R.id.weekofday, tortillas);
        mySpinner.setAdapter(adapter);
    }

}
