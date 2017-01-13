package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeTortilla extends AppCompatActivity {

    private double precios[]={6,6,8,10,10,9};
    String[] nombre = {"Patata 6€","Verduras 6€","Bacalao 8€","Jamon Iberico 10€","Queso Idiazabal 10€", "Hongos 9€"};
    Integer[] imageId = {R.drawable.tpatata,R.drawable.tverdura,R.drawable.tbacalao,R.drawable.tjamon,R.drawable.tpatata,R.drawable.thongos};
    private ArrayList<Datos> arrayParametros;
    private Cliente cliente;
    private Datos datos;
    private double precioTotalTortillas;
    private Tortilla tortilla;
    private Button aniadir;
    private TextView saludo;
    private Spinner comboTamanio;
    private Spinner comboTipoHuevo;
    private Button siguiente;
    private Button atras;
    private EditText cantidadTortilla;
    Spinner spinnerTortillas;
    private Toast alertaCantidad;
    private Toast alertaSiguiente;
    private Toast alertaAniadidoExito;
    private int cont;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);

        atras=(Button) findViewById(R.id.btnAtrasTortilla);
        aniadir=(Button) findViewById(R.id.btnAniadirTortilla);
        cantidadTortilla = (EditText) findViewById(R.id.txtcantidadTortilla);
        alertaCantidad = Toast.makeText(this,"Especifica la cantidad",Toast.LENGTH_SHORT);
        alertaSiguiente = Toast.makeText(this,"Tienes que añadir alguna tortilla al carrito",Toast.LENGTH_SHORT);
        alertaAniadidoExito=Toast.makeText(this,"Tortilla añadida con exito", Toast.LENGTH_SHORT);
        saludo = (TextView) findViewById(R.id.Saludo);

        //Recogemos los valores de la primera actividad para poder saludar al usuario
        Bundle bnd = getIntent().getExtras();
        arrayParametros = new ArrayList<Datos>();
        arrayParametros = (ArrayList<Datos>) bnd.getSerializable("array");
        cliente = (Cliente)arrayParametros.get(0).getX();
        Saludar();
        String s = arrayParametros.get(0).getIdentificador();

        siguiente=(Button) findViewById(R.id.btnSiguienteTortillas);

        comboTamanio = (Spinner) findViewById(R.id.cmbTamanio);
        ArrayAdapter<CharSequence> adaptadorTamanio = ArrayAdapter.createFromResource(this,R.array.Tamanios,android.R.layout.simple_spinner_item);
        adaptadorTamanio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboTamanio.setAdapter(adaptadorTamanio);

        comboTipoHuevo = (Spinner) findViewById(R.id.cmbTipoHuevo);
        ArrayAdapter<CharSequence> adaptadorTipoHuevo = ArrayAdapter.createFromResource(this,R.array.TipoHuevos,android.R.layout.simple_spinner_item);
        adaptadorTipoHuevo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboTipoHuevo.setAdapter(adaptadorTipoHuevo);

        spinnerTortillas = (Spinner) findViewById(R.id.cmbTipoTortilla);
        AdaptadorTortillas adapter = new AdaptadorTortillas(this,nombre,imageId);
        spinnerTortillas.setAdapter(adapter);

        //Listener del boton añadir
        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tortillaElegida;
                if(cantidadTortilla.getText().toString().equals("")){
                    alertaCantidad.show();
                }
                else {
                    //Recogemos los datos de los Text Box
                    tortillaElegida = spinnerTortillas.getSelectedItemPosition();
                    String tipoTortilla = "";
                    switch (tortillaElegida){
                        case 0:
                            tipoTortilla = "Patata";
                            break;
                        case 1:
                            tipoTortilla = "Verduras";
                            break;
                        case 2:
                            tipoTortilla = "Bacalao";
                            break;
                        case 3:
                            tipoTortilla = "Jamon";
                            break;
                        case 4:
                            tipoTortilla = "Queso Idiazabal";
                            break;
                        case 5:
                            tipoTortilla = "Hongos";
                            break;
                    }

                    String tamanioS = comboTamanio.getSelectedItem().toString();
                    String huevoS = comboTipoHuevo.getSelectedItem().toString();
                    int cantidadS = Integer.parseInt(cantidadTortilla.getText().toString());
                    Log.e("info ", String.valueOf(spinnerTortillas.getSelectedItemId()));

                    //Instaciamos los objetos que vamos a meter en el arrayList para pasarlo por parametro
                    tortilla = new Tortilla();
                    datos = new Datos();

                    //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
                    tortilla.setPrecioUnitario(CalculaPrecioTortilla());
                    tortilla.setTamanio(tamanioS);
                    tortilla.setTipoHuevos(huevoS);
                    tortilla.setCantidad(cantidadS);
                    tortilla.setTipoTortilla(tipoTortilla);
                    datos.setIdentificador("tortilla");
                    datos.setX(tortilla);
                    arrayParametros.add(datos);
                    cont++;
                    alertaAniadidoExito.show();

                }

            }
        });

        //Listener del boton atras
        //En caso de que haya añadido alguna tortilla al arraylist, tenemos que borrarla
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont > 0){
                    arrayParametros.clear();
                    //Ponemos el contador de las tortillas añadidas a cero otra vez porque hemos borrado las tortillas
                    cont=0;
                }
                finish();
            }
        });

        //Listener del boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cont != 0) {

                    LanzarActividad();
                }
                else{
                    alertaSiguiente.show();
                }

            }
        });
    }
    public void Saludar(){
        String nombre;
        nombre = cliente.getNombre();
        saludo.setText("Buenas " + nombre + ", que deseas para tu pedido?");
    }



    public void LanzarActividad(){
        Intent intent = new Intent(EligeTortilla.this,EligeBebida.class);

        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        intent.putExtra("array",arrayParametros);

        //Lanzamos la siguiente actividad
        startActivity(intent);
        finish();
    }

    public double CalculaPrecioTortilla(){
        double precioTortilla=0;
        switch (comboTipoHuevo.getSelectedItemPosition()) {
            case 1:
                precioTortilla += 2;
                break;
            case 2:
                precioTortilla += 3;
        }

        switch (comboTamanio.getSelectedItemPosition()){
            case 1:
                precioTortilla+=5;
                break;
        }
        switch (spinnerTortillas.getSelectedItemPosition()){
            case 0:
                precioTortilla+=6;
                break;
            case 1:
                precioTortilla+=6;
                break;
            case 2:
                precioTortilla+=8;
                break;
            case 3:
                precioTortilla+=10;
                break;
            case 4:
                precioTortilla+=10;
                break;
            case 5:
                precioTortilla+=9;
                break;

        }
        return precioTortilla;


    }

}
