package com.example.hibryd.pedidostortillas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeBebida extends AppCompatActivity{
    private ArrayList<String> precios = new ArrayList<String>();
    private double cantidades[]={0,0,0,0,0,0};
    private String nombreBebidas[] = {"Cola","Limon","Naranja","Nestea","Cerveza","Agua"};
    private Bebida bebida;
    private Datos datos;
    private Button botonSiguinte;
    private Button botonAtras;
    private double precioTotalBebidas = 0;
    private TextView precioTotal;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private LinearLayout contenedorcheck;
    private LinearLayout contenedoredit;
    SQLiteDatabase db;
    int cont =0;
    int numeroBebidas=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligebebida);
        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 14);
        db = creartablas.getReadableDatabase();

        Cursor cr = db.rawQuery("Select * FROM producto where tipoHuevo is null", null);


        //Enlazamos los objetos a las vistas
        precioTotal = (TextView) findViewById(R.id.lblPrecioTotal);
        botonAtras = (Button) findViewById(R.id.btnAtras3);
        botonSiguinte = (Button) findViewById(R.id.btnSiguiente3);
        contenedorcheck = (LinearLayout) findViewById(R.id.contenedorcheck);
        contenedoredit = (LinearLayout) findViewById(R.id.contenedoredit);


        ArrayList<CrearVistasDinamicas> vistas = new ArrayList<CrearVistasDinamicas>();
        while (cr.moveToNext()) {
            vistas.add(new CrearVistasDinamicas(cr.getInt(0), cr.getString(1)));
            precios.add(Float.toString(cr.getFloat(4)));
            numeroBebidas++;

        }


        for (CrearVistasDinamicas c : vistas) {
            final CheckBox cb = new CheckBox(getApplicationContext());
            final EditText edt = new EditText(getApplicationContext());
            cb.setText(c.getNombre());
            cb.setId(c.getId());
            cb.setTextColor(Color.BLACK);
            edt.setId(c.getId());
            edt.setTextColor(Color.BLACK);
            edt.setHint("Cantidad");
            edt.setHintTextColor(Color.BLACK);
            edt.setTextSize(8);
            edt.setEnabled(false);
            contenedorcheck.addView(cb);
            contenedoredit.addView(edt);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (cb.isChecked()) {
                        edt.setEnabled(true);
                        //Ponemos el foco en la caja de texto para escribir la cantidad
                        edt.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    } else {
                        cantidades[buttonView.getId() - 1] = 0;
                        edt.setText("");
                        edt.setEnabled(false);
                        RecalcularPrecioTotal(cb);
                    }
                }
            });
            edt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (!edt.getText().toString().equals("")) {
                        cantidades[v.getId() - 1] = Double.parseDouble(edt.getText().toString());
                        RecalcularPrecioTotal(v);
                    }
                    return false;
                }
            });


            //camasjdjasjhdhjasdhjasd

        /*

        //Los ponemos en disabled para que no pueda meter ningun valor hasta que selecione alguna bebida.
        cantidadCola.setEnabled(false);
        cantidadLimon.setEnabled(false);
        cantidadNaranja.setEnabled(false);
        cantidadNestea.setEnabled(false);
        cantidadCerveza.setEnabled(false);
        cantidadAgua.setEnabled(false);
           */

            botonSiguinte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LanzarActividad();
                }
            });

            botonAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EligeBebida.this, EligeTortilla.class);

                    //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
                    intent.putExtra("array", arrayParametros);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //Lanzamos la siguiente actividad
                    startActivity(intent);
                    finish();
                }
            });


        /*
        //Ponemos los listeners para todos los checkBox para que cuando esten checkeados sea posible
        //cambiar el valor de la canitdad.
        checkCola.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkCola.isChecked()) {
                    cantidadCola.setEnabled(true);
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadCola.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadLimon.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadNaranja.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadNestea.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadCerveza.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                    //Ponemos el foco en la caja de texto para escribir la cantidad
                    cantidadAgua.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(EligeBebida.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/
        }
    }

    private void RecalcularPrecioTotal(View v){
        precioTotalBebidas=0;

        for (int i=0;i<=numeroBebidas -1;i++){
            precioTotalBebidas+= cantidades[i] * Double.parseDouble(precios.get(i));
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
                bebida.setPrecioUnitario(Double.parseDouble(precios.get(i)));
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

    class CrearVistasDinamicas{
        private int id;
        private String nombre;

        public CrearVistasDinamicas(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }


    }

}
