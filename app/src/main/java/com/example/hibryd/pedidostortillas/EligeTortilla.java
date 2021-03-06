package com.example.hibryd.pedidostortillas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class EligeTortilla extends AppCompatActivity {

    ArrayList<String> nombre = new ArrayList<String>();//{"Patata 6€","Verduras 6€","Bacalao 8€","Jamon Iberico 10€","Queso Idiazabal 10€", "Hongos 9€"};
    ArrayList<String> imageId =new ArrayList<String>(); //{R.drawable.tpatata,R.drawable.tverdura,R.drawable.tbacalao,R.drawable.tjamon,R.drawable.tpatata,R.drawable.thongos};
    private ArrayList<Datos> arrayParametros;
    private Cliente cliente;
    private Datos datos;
    private ImageButton carrito;
    private TextView precioTotal;
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
    private Float precioLinea;
    private SQLiteDatabase db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligetortilla);

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getReadableDatabase();


        //Carga los nombres y las imagenes en los arraylist.
        cargarArrays();


        carrito = (ImageButton) findViewById(R.id.btnCarrito);
        precioTotal=(TextView) findViewById(R.id.precioTotalTortillas);
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

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ventana.dismiss();
                return false;
            }
        });


        comboTamanio = (Spinner) findViewById(R.id.cmbTamanio);
        ArrayAdapter<CharSequence> adaptadorTamanio = ArrayAdapter.createFromResource(this,R.array.Tamanios,android.R.layout.simple_spinner_item);
        adaptadorTamanio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboTamanio.setAdapter(adaptadorTamanio);

        comboTipoHuevo = (Spinner) findViewById(R.id.cmbTipoHuevo);
        ArrayAdapter<CharSequence> adaptadorTipoHuevo = ArrayAdapter.createFromResource(this,R.array.TipoHuevos,android.R.layout.simple_spinner_item);
        adaptadorTipoHuevo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboTipoHuevo.setAdapter(adaptadorTipoHuevo);

        spinnerTortillas = (Spinner) findViewById(R.id.cmbTipoTortilla);

        Integer [] imagenes=new Integer[imageId.size()];
        String [] nombreS=new String[nombre.size()];
        for(int i = 0; i < imageId.size(); i++){
            imagenes [i] = getResources().getIdentifier(imageId.get(i),"drawable",getPackageName());
            nombreS [i]= nombre.get(i);
        }
        AdaptadorTortillas adapter = new AdaptadorTortillas(this,nombreS,imagenes);
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

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cargarCarrito();
            }
        });

        comboTamanio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularPrecio(db);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        comboTipoHuevo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularPrecio(db);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTortillas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularPrecio(db);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                   /* //Recogemos los datos de los Text Box
                    tortillaElegida = spinnerTortillas.getSelectedItemPosition();
                    String tipoTortilla = "";
                    switch (tortillaElegida){
                        case 0:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.Patata";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.Patata +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Patata +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Patata +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Patata +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Patata +fam +eco";
                            }
                            break;

                        case 1:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.Verduras";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.Verduras +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Verduras +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Verduras +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Verduras +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Verduras +fam +eco";
                            }
                            break;

                        case 2:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.Bacalao";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.Bacalao +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Bacalao +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Bacalao +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Bacalao +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Bacalao +fam +eco";
                            }
                            break;
                        case 3:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.Jamon";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.Jamon +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Jamon +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Jamon +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Jamon +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Jamon +fam +eco";
                            }
                            break;
                        case 4:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.QuesoIdi";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.QuesoIdi +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.QuesoIdi +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.QuesoIdi +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.QuesoIdi +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.QuesoIdi +fam +eco";
                            }
                            break;
                        case 5:
                            if (comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() == 0) {
                                tipoTortilla = "T.Hongos";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==0 ) {
                                tipoTortilla = "T.Hongos +fam";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Hongos +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==0 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Hongos +eco";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==1 ) {
                                tipoTortilla = "T.Hongos +fam +camp";
                            }
                            else if(comboTamanio.getSelectedItemPosition()==1 && comboTipoHuevo.getSelectedItemPosition() ==2 ) {
                                tipoTortilla = "T.Hongos +fam +eco";
                            }
                            break;
                    }*/

                    String tipoTortilla = spinnerTortillas.getSelectedItem().toString();
                    String tamanioS = comboTamanio.getSelectedItem().toString();
                    String huevoS = comboTipoHuevo.getSelectedItem().toString();
                    int cantidadS = Integer.parseInt(cantidadTortilla.getText().toString());
                    Log.e("info ", String.valueOf(spinnerTortillas.getSelectedItemId()));

                    //Instaciamos los objetos que vamos a meter en el arrayList para pasarlo por parametro
                    tortilla = new Tortilla();
                    datos = new Datos();

                    //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
                    tortilla.setPrecioUnitario(precioLinea);
                    tortilla.setTamanio(tamanioS);
                    tortilla.setTipoHuevos(huevoS);
                    tortilla.setCantidad(cantidadS);
                    tortilla.setTipoTortilla(tipoTortilla);
                    datos.setIdentificador("tortilla");
                    datos.setX(tortilla);
                    arrayParametros.add(datos);
                    cont++;
                    alertaAniadidoExito.show();

                    //Ocultar Teclado
                    InputMethodManager ocultarTeclado = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    ocultarTeclado.hideSoftInputFromWindow(cantidadTortilla.getWindowToken(), 0);

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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //Ocultar Teclado
        InputMethodManager ocultarTeclado = (InputMethodManager)getSystemService(EligeTortilla.this.INPUT_METHOD_SERVICE);
        ocultarTeclado.hideSoftInputFromWindow(cantidadTortilla.getWindowToken(), 0);


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

    public void calcularPrecio(SQLiteDatabase db){
        String [] tamaniotortilla = comboTamanio.getSelectedItem().toString().split(" ");
        String [] tipoHuevotortilla = comboTipoHuevo.getSelectedItem().toString().split(" ");
        Cursor c=db.rawQuery("SELECT precioUnitario from producto where UPPER(nombre)='" + spinnerTortillas.getSelectedItem().toString().toUpperCase() +
                "' and UPPER(tipoHuevo)='" + tipoHuevotortilla[0].toUpperCase() + "' and UPPER(tamanio)='" +
                tamaniotortilla[0].toUpperCase() + "'",null);
        while(c.moveToNext()){
            precioTotal.setText("Precio total: "+c.getFloat(0)+ "€");
            precioLinea = c.getFloat(0);
        }

    }

    public void cargarArrays(){
       Cursor c= db.rawQuery("SELECT distinct(nombre),imagen from producto where tipoHuevo is not null",null);
        while(c.moveToNext()){
            nombre.add(c.getString(0));
            imageId.add(c.getString(1));
        }
    }

    public void cargarCarrito(){
        if (arrayParametros.size()>1) {
            Intent in = new Intent(EligeTortilla.this, Resumen.class);

            Bundle extras = new Bundle();
            extras.putSerializable("array",arrayParametros);
            extras.putString("ventanaTortilla","ventanaTortilla");
            in.putExtras(extras);

            startActivity(in);
            finish();
        }else{
            Toast.makeText(this, "Antes de ir al carrito tienes que añadir una tortilla.", Toast.LENGTH_SHORT).show();
        }
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
