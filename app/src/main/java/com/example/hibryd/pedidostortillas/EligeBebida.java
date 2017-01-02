package com.example.hibryd.pedidostortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeBebida extends AppCompatActivity{
    private double precios[]={1.50,1.50,1.50,2,2,1};
    private Bebida bebida;
    private Datos datos;
    private Button botonAniadir;
    private TextView precioTotal;
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
    private TextView precioTotalCola;
    private TextView precioTotalLimon;
    private TextView precioTotalNaranja;
    private TextView precioTotalNestea;
    private TextView precioTotalCerveza;
    private TextView precioTotalAgua;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligebebida);

        //Enlazamos los objetos a las vistas
        botonAniadir = (Button) findViewById(R.id.btnAñadirBebidas);
        checkCola = (CheckBox) findViewById(R.id.ckbCola);
        checkLimon = (CheckBox) findViewById(R.id.ckbLimon);
        checkNaranja = (CheckBox) findViewById(R.id.ckbNaranja);
        checkNestea = (CheckBox) findViewById(R.id.ckbNestea);
        checkCerveza = (CheckBox) findViewById(R.id.ckbCerveza);
        checkAgua = (CheckBox) findViewById(R.id.ckbAgua);
        cantidadCola = (EditText) findViewById(R.id.txtCantidadCola);
        cantidadLimon = (EditText) findViewById(R.id.txtCantidadLimon);
        cantidadNaranja = (EditText) findViewById(R.id.txtCantidadnaranja);
        cantidadNestea = (EditText) findViewById(R.id.txtCantidadNestea);
        cantidadCerveza = (EditText) findViewById(R.id.txtCantidadCerveza);
        cantidadAgua = (EditText) findViewById(R.id.txtCantidadAgua);
        precioTotalCola =(TextView)findViewById(R.id.lblPrecioCola);
        precioTotalLimon=(TextView)findViewById(R.id.lblPrecioLimon);
        precioTotalNaranja=(TextView)findViewById(R.id.lblPrecioNaranja);
        precioTotalNestea=(TextView)findViewById(R.id.lblPrecioNestea);
        precioTotalCerveza=(TextView)findViewById(R.id.lblPrecioCerveza);
        precioTotalAgua=(TextView)findViewById(R.id.lblPrecioAgua);
        precioTotal=(TextView)findViewById(R.id.lblPrecioTotal);

        //Los ponemos en disabled para que no pueda meter ningun valor hasta que selecione alguna bebida.
        cantidadCola.setEnabled(false);
        cantidadLimon.setEnabled(false);
        cantidadNaranja.setEnabled(false);
        cantidadNestea.setEnabled(false);
        cantidadCerveza.setEnabled(false);
        cantidadAgua.setEnabled(false);



        precioTotal.setText("0");

        //Ponemos los listeners para todos los checkBox para que cuando esten checkeados sea posible
        //cambiar el valor de la canitdad.

        checkCola.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCola.isChecked()) {
                    cantidadCola.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadCola.setEnabled(false);
                }
            }
        });
        checkLimon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkLimon.isChecked()) {
                    cantidadLimon.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadLimon.setEnabled(false);
                }
            }
        });

        checkNaranja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkNaranja.isChecked()) {
                    cantidadNaranja.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadNaranja.setEnabled(false);
                }
            }
        });

        checkNestea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkNestea.isChecked()) {
                    cantidadNestea.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadNestea.setEnabled(false);
                }
            }
        });

        checkCerveza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCerveza.isChecked()) {
                    cantidadCerveza.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadCerveza.setEnabled(false);
                }
            }
        });
        checkAgua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkAgua.isChecked()) {
                    cantidadAgua.setEnabled(true);
                } else {
                    cantidadCola.setText("");
                    cantidadAgua.setEnabled(false);
                }
            }
        });

        //Listeners para que cuando introduzca la cantidad de bebida deseada se actualice su precio en cuestion.

        cantidadCola.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //Cuando borra tiene que recalcular los valores.
                    precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString())- Double.parseDouble(precioTotalCola.toString())));
                    precioTotalCola.setText(Double.toString(Double.parseDouble(cantidadCola.getText().toString())* precios[0]));
                }
                else {
                    Double resul = Double.parseDouble(cantidadCola.getText().toString()) * precios[0];
                    precioTotalCola.setText(Double.toString(resul));
                    precioTotal.setText(Double.toString((Double.parseDouble(precioTotal.getText().toString())) + resul));
                }
                return false;
            }
        });


    }
}
