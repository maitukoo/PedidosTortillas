package com.example.hibryd.pedidostortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeBebida extends AppCompatActivity{
    private Bebida bebida;
    private Datos datos;
    private Button botonAniadir;
    private CheckBox checkCola;
    private CheckBox checkLimon;
    private CheckBox checkNaranja;
    private CheckBox checkNestea;
    private CheckBox checkCerveza;
    private CheckBox checkAgua;
    private EditText cantidadCola;
    private EditText cantidadLimon;
    private EditText cantidadNaranja;
    private EditText cantidadNestea;
    private EditText cantidadCerveza;
    private EditText cantidadAgua;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligebebida);

        botonAniadir=(Button) findViewById(R.id.btnAñadirBebidas);
        checkCola=(CheckBox) findViewById(R.id.ckbCola);
        checkLimon=(CheckBox) findViewById(R.id.ckbLimon);
        checkNaranja=(CheckBox)findViewById(R.id.ckbNaranja);
        checkNestea=(CheckBox) findViewById(R.id.ckbNestea);
        checkCerveza=(CheckBox) findViewById(R.id.cerveza);
        checkAgua=(CheckBox) findViewById(R.id.ckbAgua);
        cantidadCola=(EditText) findViewById(R.id.txtCantidadCola);
        cantidadLimon=(EditText) findViewById(R.id.txtCantidadLimon);
        cantidadNaranja=(EditText) findViewById(R.id.txtCantidadnaranja);
        cantidadNestea=(EditText) findViewById(R.id.txtCantidadNestea);
        cantidadCerveza=(EditText) findViewById(R.id.txtCantidadCerveza);
        cantidadAgua=(EditText) findViewById(R.id.txtCantidadAgua);

        checkCola.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCola.isChecked()){
                    cantidadCola.setEnabled(true);
                }
                else{
                    cantidadCola.setText("");
                    cantidadCola.setEnabled(false);
                }
            }
        });
        checkLimon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkLimon.isChecked()){
                    cantidadLimon.setEnabled(true);
                }
                else{
                    cantidadCola.setText("");
                    cantidadLimon.setEnabled(false);
                }
            }
        });
    }
}
