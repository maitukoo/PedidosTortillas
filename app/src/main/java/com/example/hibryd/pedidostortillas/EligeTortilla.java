package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeTortilla extends AppCompatActivity {

    String[] nombre = {"Coca-Cola","Kas"};
    Integer[] imageId = {R.drawable.cola,R.drawable.kas};
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private Cliente cliente;
    private TextView saludo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);
        saludo = (TextView) findViewById(R.id.Saludo);
        Bundle bnd = getIntent().getExtras();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");
        cliente = (Cliente)arrayParametros.get(0).getX();
        Saludar();
        String s = arrayParametros.get(0).getIdentificador();
        Log.e("tu madre",s);
        Log.e("joder", cliente.getNombre());


        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        AdaptadorTortillas adapter = new AdaptadorTortillas(this,nombre,imageId);
        mySpinner.setAdapter(adapter);
    }
    public void Saludar(){
        String nombre;
        nombre = cliente.getNombre();
        saludo.setText("Buenas " + nombre + ", que deseas para tu pedido");
    }
}
