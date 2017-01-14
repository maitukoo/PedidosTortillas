package com.example.hibryd.pedidostortillas;

import android.content.Intent;
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
    private double cantidades[]={0,0,0,0,0,0};
    private String nombreBebidas[] = {"Cola","Limon","Naranja","Nestea","Cerveza","Agua"};
    private Bebida bebida;
    private Datos datos;
    private Button botonSiguinte;
    private double precioTotalBebidas = 0;
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
        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");

        //Enlazamos los objetos a las vistas
        botonSiguinte = (Button) findViewById(R.id.btnSiguiente3);
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

        botonSiguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarActividad();
            }
        });



        checkCola.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCola.isChecked()) {
                    cantidadCola.setEnabled(true);
                } else {
                    cantidades[0] = 0;
                    cantidadCola.setText("");
                    cantidadCola.setEnabled(false);
                    RecalcularPrecioTotal();
                }
            }
        });
        checkLimon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkLimon.isChecked()) {
                    cantidadLimon.setEnabled(true);
                } else {
                    cantidades[1] = 0;
                    cantidadLimon.setText("");
                    cantidadLimon.setEnabled(false);
                    RecalcularPrecioTotal();
                }
            }
        });

        checkNaranja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkNaranja.isChecked()) {
                    cantidadNaranja.setEnabled(true);
                } else {
                    cantidades[2] = 0;
                    cantidadNaranja.setText("");
                    cantidadNaranja.setEnabled(false);
                    RecalcularPrecioTotal();
                }
            }
        });

        checkNestea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkNestea.isChecked()) {
                    cantidadNestea.setEnabled(true);
                } else {
                    cantidades[3] = 0;
                    cantidadNestea.setText("");
                    cantidadNestea.setEnabled(false);
                    RecalcularPrecioTotal();
                }
            }
        });

        checkCerveza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCerveza.isChecked()) {
                    cantidadCerveza.setEnabled(true);
                } else {
                    cantidades[4] = 0;
                    cantidadCerveza.setText("");
                    cantidadCerveza.setEnabled(false);
                    RecalcularPrecioTotal();
                }
            }
        });
        checkAgua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkAgua.isChecked()) {
                        cantidadAgua.setEnabled(true);
                    } else {
                        cantidades[5] = 0;
                    cantidadAgua.setText("");
                    cantidadAgua.setEnabled(false);
                        RecalcularPrecioTotal();
                    }
            }
        });

        //Listeners para que cuando introduzca la cantidad de bebida deseada se actualice su precio Total

        cantidadCola.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadCola.getText().toString().equals("")) {
                    cantidades[0] = Double.parseDouble(cantidadCola.getText().toString());
                    RecalcularPrecioTotal();
                }else{
                       cantidades[0] = 0;
                    RecalcularPrecioTotal();
                    }
                return false;
            }
        });

        cantidadLimon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (!cantidadLimon.getText().toString().equals("")) {
                        cantidades[1] = Double.parseDouble(cantidadLimon.getText().toString());
                        RecalcularPrecioTotal();
                    } else {
                        cantidades[1] = 0;
                        RecalcularPrecioTotal();
                    }
                    return false;
                }
        });

        cantidadNaranja.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadNaranja.getText().toString().equals("")) {
                        cantidades[2] = Double.parseDouble(cantidadNaranja.getText().toString());
                        RecalcularPrecioTotal();
                    }else{
                        cantidades[2] = 0;
                        RecalcularPrecioTotal();
                    }
                    return false;
            }
        });


        cantidadNestea.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadNestea.getText().toString().equals("")) {
                        cantidades[3] = Double.parseDouble(cantidadNestea.getText().toString());
                        RecalcularPrecioTotal();
                    }else{
                        cantidades[3] = 0;
                        RecalcularPrecioTotal();
                    }
                    return false;

            }
        });

        cantidadCerveza.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadCerveza.getText().toString().equals("")) {
                        cantidades[4] = Double.parseDouble(cantidadCerveza.getText().toString());
                        RecalcularPrecioTotal();
                    }else{
                        cantidades[4] = 0;
                        RecalcularPrecioTotal();
                    }
                    return false;

            }
        });

        cantidadAgua.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!cantidadAgua.getText().toString().equals("")) {
                        cantidades[5] = Double.parseDouble(cantidadAgua.getText().toString());
                        RecalcularPrecioTotal();
                    }else{
                        cantidades[5] = 0;
                        RecalcularPrecioTotal();
                    }
                    return false;
                }

        });
    }

    private void RecalcularPrecioTotal(){
        precioTotalBebidas=0;
        for (int i=0;i<=5;i++){
            precioTotalBebidas+= cantidades[i] * precios[i];
        }
        precioTotal.setText(Double.toString(precioTotalBebidas));
    }

    public void LanzarActividad(){
        Intent intent = new Intent(EligeBebida.this,Resumen.class);


        //Instaciamos los objetos que vamos a meter en el arrayList para pasarlo por parametro
        for (int i=0;i<=5; i++) {
            if (cantidades[i] != 0){
                datos = new Datos();
                bebida = new Bebida();
                //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
                bebida.setNumeroBebidas(cantidades[i]);
                bebida.setPrecioUnitario(precios[i]);
                bebida.setTipoBebida(nombreBebidas[i]);
                datos.setIdentificador("bebida");
                datos.setX(bebida);
                arrayParametros.add(datos);
            }
        }


        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        intent.putExtra("array",arrayParametros);

        //Lanzamos la siguiente actividad
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(EligeBebida.this,EligeTortilla.class);

        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        intent.putExtra("array",arrayParametros);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //Lanzamos la siguiente actividad
        startActivity(intent);
    }

}
