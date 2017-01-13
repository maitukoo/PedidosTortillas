package com.example.hibryd.pedidostortillas;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    private LinearLayout contenedorVentana;
    private ViewGroup.LayoutParams paramsVentana;
    private Button aceptar;
    private Button cancelar;
    private TextView textoVentana;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        ventana = new PopupWindow(this);
        contenedorVentana = new LinearLayout(this);
        aceptar = new Button(this);
        cancelar = new Button(this);
        textoVentana=new TextView(this);

        textoVentana.setText("Desea borrar esta linea");
        paramsVentana = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contenedorVentana.setOrientation(LinearLayout.VERTICAL);
        contenedorVentana.addView(textoVentana,paramsVentana);
        ventana.setContentView(contenedorVentana);

        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");


        nombres = (ListView) findViewById(R.id.lstNombres);
        cantidades = (ListView) findViewById(R.id.lstCantidades);
        preciosTotales = (ListView) findViewById(R.id.lstPreciosTotales);

        nombres.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OpcionBorrar(position);
                return true;
            }
        });
        cantidades.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OpcionBorrar(position);
                return false;
            }
        });
        preciosTotales.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OpcionBorrar(position);
                return false;
            }
        });
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

        Double precioTotal;
        for (int i = 0; i< arrayParametros.size();i++){
            datos = arrayParametros.get(i);
            switch (datos.getIdentificador()){
                case "tortilla":
                    tortilla = (Tortilla) datos.getX();
                    nombresA.add(tortilla.getTipoTortilla());
                    cantidadesA.add(String.valueOf(tortilla.getCantidad()));
                    precioTotal = tortilla.getPrecioUnitario() * tortilla.getCantidad();
                    preciosTotalesA.add(String.valueOf(precioTotal));
                    break;
                case "bebida":
                    bebida = (Bebida) datos.getX();
                    nombresA.add(bebida.getTipoBebida());
                    cantidadesA.add(String.valueOf(bebida.getNumeroBebidas()));
                    precioTotal = bebida.getPrecioUnitario() * bebida.getNumeroBebidas();
                    preciosTotalesA.add(String.valueOf(precioTotal));
                    break;
                }
            }

        }
    public void OpcionBorrar(int pos){
        ventana.showAtLocation(contenedorVentana, Gravity.BOTTOM,10,10);
        ventana.update();
    }
}
