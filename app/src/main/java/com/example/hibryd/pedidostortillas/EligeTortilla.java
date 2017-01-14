package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    private PopupWindow ventana;
    private LayoutInflater inflater;
    private LinearLayout layoutVentana;
    private Button aceptar;
    private Button cancelar;
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

        //Todo lo necesario para el popup
        layoutVentana = (LinearLayout) findViewById(R.id.layoutTortillas);
        inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) inflater.inflate(R.layout.cancelarpedido,null);
        ventana = new PopupWindow(container,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

        //A los botones del poup tenemos que ponerles apuntadores pero diciendole que es del CONTAINER!!
        aceptar=(Button) container.findViewById(R.id.btnSiCancelarPedido);
        cancelar=(Button) container.findViewById(R.id.btnNoCancelarPedido);

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

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ventana.dismiss();
                return false;
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayParametros.clear();
                ventana.dismiss();
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventana.dismiss();
            }
        });

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
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "TPatata";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "TPatata +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TPatata +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TPatata +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TPatata +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TPatata +fam +eco";
                            }
                            break;

                        case 1:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "TVerduras";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "TVerduras +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TVerduras +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TVerduras +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TVerduras +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TVerduras +fam +eco";
                            }
                            break;

                        case 2:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "TBacalao";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "TBacalao +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TBacalao +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TBacalao +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TBacalao +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TBacalao +fam +eco";
                            }
                            break;
                        case 3:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "TJamon";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "TJamon +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TJamon +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TJamon +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TJamon +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TJamon +fam +eco";
                            }
                            break;
                        case 4:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "TQuesoIdi";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "TQuesoIdi +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TQuesoIdi +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TQuesoIdi +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "TQuesoIdi +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "TQuesoIdi +fam +eco";
                            }
                            break;
                        case 5:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "THongos";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "THongos +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "THongos +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "THongos +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "THongos +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "THongos +fam +eco";
                            }
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
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventana.showAtLocation(layoutVentana, Gravity.CENTER, 0, 0);
            }
        });

        //Listener del boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayParametros.size()>1) {

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
        saludo.setText("Buenas " + nombre + ". ¿Que deseas para tu pedido?");
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


    //En caso de que presione el boton de atras del movil tenemos que borrar el arraylist.
    //Si muestro en la pantalla la alerta directamente la cierra y no da tiempo alegir la opcion
    //porque directamente se va a la actividad anterior, entonces borramos el arraylist y yasta.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        arrayParametros.clear();

    }

}
