package com.example.hibryd.pedidostortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeBebida extends AppCompatActivity{
    private String [] tipoBebida={"Coca cola", "Limón", "Naranja", "Nestea", "cerveza", "Agua"};
    private Spinner spin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin=(Spinner) findViewById(R.id.cmbTamanio);
        spin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoBebida));
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Info:",parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
