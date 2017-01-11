package com.example.hibryd.pedidostortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

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
    private String nombresArray[];
    private String cantidadesArray[];
    private String preciosArray[];





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");


        nombres = (ListView) findViewById(R.id.lstNombres);
        cantidades = (ListView) findViewById(R.id.lstCantidades);
        preciosTotales = (ListView) findViewById(R.id.lstPreciosTotales);

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
}
