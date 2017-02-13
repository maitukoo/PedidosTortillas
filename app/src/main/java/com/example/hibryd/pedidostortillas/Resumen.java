package com.example.hibryd.pedidostortillas;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by adminportatil on 11/01/2017.
 */

public class Resumen extends AppCompatActivity {
    private ListView nombres;
    private ListView cantidades;
    private ListView preciosTotales;
    private Datos datos;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private ArrayList<String> nombresA = new ArrayList<>();
    private ArrayList<String> cantidadesA= new ArrayList<>();
    private ArrayList<String> preciosTotalesA= new ArrayList<>();
    private Tortilla tortilla;
    private Bebida bebida;
    private PopupWindow ventana;
    private LayoutInflater inflater;
    private LinearLayout layoutVentana;
    private Button aceptar;
    private Button cancelar;
    private Button atrasResumen;
    private Button finalizarPedido;
    private int posicionSeleccionada;
    private double precioTotalPedido;
    private TextView regalo;
    private String vengode="";
    private SQLiteDatabase db;
    private Cliente cliente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getReadableDatabase();

        Toast avisoborrar =
                Toast.makeText(getApplicationContext(),
                        "Recuerda que puedes borrar tus productos con una pulsacion larga sobre el CONCEPTO!", Toast.LENGTH_LONG);

        avisoborrar.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);

        avisoborrar.show();


        //Todo lo necesario para el popup
        layoutVentana = (LinearLayout) findViewById(R.id.linearLayout);
        inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup contenedor = (ViewGroup) inflater.inflate(R.layout.avisoborrar,null);
        ventana = new PopupWindow(contenedor,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

        //A los botones del poup tenemos que ponerles apuntadores pero diciendole que es del CONTAINER!!
        aceptar=(Button) contenedor.findViewById(R.id.btnAceptarBorrar);
        cancelar=(Button) contenedor.findViewById(R.id.btnCancelarBorrar);

        atrasResumen = (Button) findViewById(R.id.btnAtrasResumen);

        //Recogida de datos del arraylist
        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");
        if (bnd.size() == 2) {
            vengode = bnd.getString("ventanaTortilla");
        }


        //Apuntadores a los listview y precio total
        nombres = (ListView) findViewById(R.id.lstNombres);
        cantidades = (ListView) findViewById(R.id.lstCantidades);
        preciosTotales = (ListView) findViewById(R.id.lstPreciosTotales);
        regalo = (TextView) findViewById(R.id.txtRegalo);
        finalizarPedido = (Button) findViewById(R.id.btnfinalizarPedido);

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Gracias por realizar su pedido con nosotros! Lo recibira en su domicilio lo antes posible!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Cerrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //System.exit(0);
                        Intent intent = new Intent(getApplicationContext(), FirstMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                });




        finalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayParametros.size()>1) {
                    cliente = (Cliente)arrayParametros.get(0).getX();
                    Cursor c = db.rawQuery("SELECT MAX(cabeceraID) from ventaCabecera",null);
                    Cursor cur = db.rawQuery("SELECT clienteID from cliente where UPPER(nombre)='" + cliente.getNombre().toString().toUpperCase() + "'",null);
                    int linea= 1;
                    c.moveToNext();
                    cur.moveToNext();
                    db.execSQL("INSERT INTO ventaCabecera (cabeceraID,clienteID,fechaPedido) VALUES(" + (c.getInt(0)+1) + "," + cur.getInt(0)+ ",datetime())");
                    for(int i=0;i<arrayParametros.size();i++){
                        datos = arrayParametros.get(i);
                        Log.e("info",datos.getIdentificador());
                        switch (datos.getIdentificador()){
                            case "tortilla":
                                tortilla =(Tortilla) datos.getX();
                                Cursor curs = db.rawQuery("SELECT * from producto WHERE UPPER(nombre)='"+ tortilla.getTipoTortilla().toUpperCase() +"'",null);
                                curs.moveToNext();
                                db.execSQL("INSERT INTO ventaLinea (ventaLineaID,cabeceraID,productoID,cantidad,precioUnitario) VALUES("+ linea + "," + (c.getInt(0)+1) + "," +
                                        curs.getString(0) + "," + tortilla.getCantidad() + "," + tortilla.getPrecioUnitario()+")");
                                linea++;
                                break;
                            case "bebida":
                                bebida =(Bebida) datos.getX();
                                Cursor curss = db.rawQuery("SELECT * from producto WHERE UPPER(nombre)='"+ bebida.getTipoBebida().toUpperCase() + "'",null);
                                curss.moveToNext();
                                db.execSQL("INSERT INTO ventaLinea (ventaLineaID,cabeceraID,productoID,cantidad,precioUnitario) VALUES("+ linea + "," + (c.getInt(0)+1) + "," +
                                        curss.getString(0) + "," + bebida.getNumeroBebidas() + "," + bebida.getPrecioUnitario()+")");
                                linea++;
                                break;
                        }
                    }
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                    }

                else{
                    Toast.makeText(Resumen.this, "No puedes realizar un pedido vacio!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //En caso de que pulse con el dedo fuera del popup se cerrara el popup.
        contenedor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ventana.dismiss();
                return false;
            }
        });

        //En el caso de que pulse aceptar tendremos que borrar ese pedido.
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventana.dismiss();
                borrarDatosArray(posicionSeleccionada);
            }
        });

        //En el caso de cancelar no tendremos que hacer nada mas que cerrar la ventana.
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventana.dismiss();
            }
        });

        //Al darle atras podemos cerrar esta actividad ya que cuando le de a siguiente en bebidas hara de nuevo un onCreate
        atrasResumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vengode.equals("ventanaTortilla")) {
                    Intent intent = new Intent(Resumen.this, EligeTortilla.class);

                    //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
                    intent.putExtra("array", arrayParametros);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //Lanzamos la siguiente actividad
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Resumen.this, EligeBebida.class);

                    //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
                    intent.putExtra("array", arrayParametros);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //Lanzamos la siguiente actividad
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Listener para cuando selecionan un pedido del carrito poder sacar el popup y pedirle si lo quiere borrar o no.
        nombres.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ventana.showAtLocation(layoutVentana,Gravity.CENTER, 0, 0);
                posicionSeleccionada=position;
                return true;
            }
        });

        //Desglosamos todo el pedido en los listviews correspondientes.
        DesglosarPedido();
        ArrayAdapter<String> adaptadornombres;
        adaptadornombres = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombresA);


        ArrayAdapter<String> adaptadorcantidad;
        adaptadorcantidad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cantidadesA);

        ArrayAdapter<String> adaptadorprecio;
        adaptadorprecio = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,preciosTotalesA);

        nombres.setAdapter(adaptadornombres);
        cantidades.setAdapter(adaptadorcantidad);
        preciosTotales.setAdapter(adaptadorprecio);
    }

    public void DesglosarPedido(){
        nombresA.clear();
        cantidadesA.clear();
        preciosTotalesA.clear();
        precioTotalPedido = 0;

        Double precioTotal;
        for (int i = 0; i< arrayParametros.size();i++){
            datos = arrayParametros.get(i);
            switch (datos.getIdentificador()){
                case "tortilla":
                    tortilla = (Tortilla) datos.getX();
                    String nombreTortillaLinea=tortilla.getTipoTortilla();
                    String tamaniotort = tortilla.getTamanio();
                    switch (tortilla.getTamanio()){
                        case "Familiar +5€":
                            nombreTortillaLinea +="+Fam";
                            break;
                        case "Individual":
                            nombreTortillaLinea += "+Ind";
                            break;
                    }
                    switch (tortilla.getTipoHuevos()){
                        case "Granja":
                            nombreTortillaLinea += "+Gra";
                            break;
                        case "Campero +2€":
                            nombreTortillaLinea += "+Camp";
                            break;
                        case "Ecologico +3€":
                            nombreTortillaLinea += "+Eco";
                    }

                    nombresA.add(nombreTortillaLinea);
                    cantidadesA.add(String.valueOf(tortilla.getCantidad()));
                    precioTotal = tortilla.getPrecioUnitario() * tortilla.getCantidad();
                    preciosTotalesA.add(String.valueOf(precioTotal)+" €");
                    precioTotalPedido+=precioTotal;
                    break;
                case "bebida":
                    bebida = (Bebida) datos.getX();
                    nombresA.add(bebida.getTipoBebida());
                    cantidadesA.add(String.valueOf(bebida.getNumeroBebidas()));
                    precioTotal = bebida.getPrecioUnitario() * bebida.getNumeroBebidas();
                    preciosTotalesA.add(String.valueOf(precioTotal)+" €");
                    precioTotalPedido+=precioTotal;
                    break;
                }
            }
        cantidadesA.add("Total: ");
        preciosTotalesA.add(String.valueOf(precioTotalPedido)+" €");

        comprobarRegalo();

        }
    //Metodo para borrar los datos;
    public void borrarDatosArray(int pos){
        arrayParametros.remove(pos+1);
        DesglosarPedido();
        ((BaseAdapter) nombres.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) cantidades.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) preciosTotales.getAdapter()).notifyDataSetChanged();

    }

    public void onBackPressed(){
        super.onBackPressed();
        if(vengode.equals("ventanaTortilla")) {
            Intent intent = new Intent(Resumen.this, EligeTortilla.class);

            //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
            intent.putExtra("array", arrayParametros);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //Lanzamos la siguiente actividad
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(Resumen.this, EligeBebida.class);

            //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
            intent.putExtra("array", arrayParametros);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //Lanzamos la siguiente actividad
            startActivity(intent);
            finish();
        }
    }

    public void comprobarRegalo(){
        regalo.setText("");
        if (precioTotalPedido >= 28){
            regalo.setText("Por superar los 28€ en tu pedido recibiras un peluche del muñeco de android y un vale para comer en Cebanc!");
        } else if (precioTotalPedido >=18){
            regalo.setText("Por superar los 18€ en tu pedido recibiras un peluche del muñeco de Android!");
        }


    }

}
