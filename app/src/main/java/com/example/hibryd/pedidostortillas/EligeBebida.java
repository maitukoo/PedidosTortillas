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
        precioTotal=(TextView)findViewById(R.id.lblPrecioTotal);

        //Los ponemos en disabled para que no pueda meter ningun valor hasta que selecione alguna bebida.
        cantidadCola.setEnabled(false);
        cantidadLimon.setEnabled(false);
        cantidadNaranja.setEnabled(false);
        cantidadNestea.setEnabled(false);
        cantidadCerveza.setEnabled(false);
        cantidadAgua.setEnabled(false);


        //Ponemos los listeners para todos los checkBox para que cuando esten checkeados sea posible
        //cambiar el valor de la canitdad.

        checkCola.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCola.isChecked()) {
                    cantidadCola.setEnabled(true);
                } else {
                    if (!cantidadCola.getText().toString().equals("")) {
                        double cantidad = Double.parseDouble(cantidadCola.getText().toString());
                        double precioTotalS = Double.parseDouble(precioTotal.getText().toString());
                        precioTotal.setText(Double.toString(precioTotalS - (precios[0] * cantidad)));
                    }
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
                    if (!cantidadCola.getText().toString().equals("")) {
                        precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString()) - (precios[1] * Double.parseDouble(cantidadLimon.getText().toString()))));
                    }
                    cantidadLimon.setText("");
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
                    if (!cantidadCola.getText().toString().equals("")) {
                        precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString()) - (precios[2] * Double.parseDouble(cantidadNaranja.getText().toString()))));
                    }
                    cantidadNaranja.setText("");
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
                    if (!cantidadCola.getText().toString().equals("")) {
                        precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString()) - (precios[3] * Double.parseDouble(cantidadNestea.getText().toString()))));
                    }
                    cantidadNestea.setText("");
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
                    if (!cantidadCola.getText().toString().equals("")) {
                        precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString()) - (precios[4] * Double.parseDouble(cantidadCerveza.getText().toString()))));
                    }
                    cantidadCerveza.setText("");
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
                    if (!cantidadCola.getText().toString().equals("")) {
                        precioTotal.setText(Double.toString(Double.parseDouble(precioTotal.getText().toString()) - (precios[5] * Double.parseDouble(cantidadAgua.getText().toString()))));
                    }
                    cantidadAgua.setText("");
                    cantidadAgua.setEnabled(false);
                }
            }
        });

        //Listeners para que cuando introduzca la cantidad de bebida deseada se actualice su precio Total

        cantidadCola.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadCola.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadCola.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[0] * cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[0] * cantidad)));
                    }
                }
                return false;
            }
        });

        cantidadLimon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadLimon.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadLimon.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[1]* cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[1] * cantidad)));
                    }
                }
                return false;
            }
        });

        cantidadNaranja.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadNaranja.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadNaranja.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[2]* cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[2] * cantidad)));
                    }
                }
                return false;
            }
        });

        cantidadNestea.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadNestea.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadNestea.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[3]* cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[3] * cantidad)));
                    }
                }
                return false;
            }
        });

        cantidadCerveza.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadCerveza.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadCerveza.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[4]* cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[4] * cantidad)));
                    }
                }
                return false;
            }
        });

        cantidadAgua.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadAgua.getText().toString().equals("")) {
                    double cantidad = Double.parseDouble(cantidadAgua.getText().toString());
                    double precioTotalS = Double.parseDouble(precioTotal.getText().toString());


                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        //Cuando borra tiene que recalcular los valores.
                        precioTotal.setText(Double.toString(precioTotalS - (precios[5]* cantidad)));
                    } else {
                        precioTotal.setText(Double.toString(precioTotalS + (precios[5] * cantidad)));
                    }
                }
                return false;
            }
        });
    }
}
