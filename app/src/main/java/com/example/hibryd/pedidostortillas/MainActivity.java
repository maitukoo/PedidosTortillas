package com.example.hibryd.pedidostortillas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button siguiente;
    private Button atras;
    private Button buscar;
    private Button limpiar;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private Datos datos;
    private Cliente cliente;
    private Toast alertaNombre;
    private Toast alertaDireccion;
    private Toast alertaTelefono;
    SQLiteDatabase db;
    private boolean buscado=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);


        db = creartablas.getWritableDatabase();

        //Enlazamos los objetos a las vistas
        nombre = (EditText) findViewById(R.id.edtNombre);
        direccion = (EditText) findViewById(R.id.edtDireccion);
        telefono = (EditText) findViewById(R.id.edtTelefono);
        siguiente = (Button) findViewById(R.id.SiguienteDatos);
        atras = (Button) findViewById(R.id.btnAtras1);
        buscar = (Button) findViewById(R.id.botonBuscar);
        limpiar= (Button) findViewById(R.id.btnLimpiar);

        //Toast para mostrar los mensajes en caso de que no introduzca algun valor.
        alertaNombre = Toast.makeText(this, "Introduce el nombre", Toast.LENGTH_SHORT);
        alertaDireccion = Toast.makeText(this, "Introduce la direccion", Toast.LENGTH_SHORT);
        alertaTelefono = Toast.makeText(this, "Introduce el numero de telefono", Toast.LENGTH_SHORT);

        //programamos el boton de buscar cliente

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCliente();
            }
        });


        //Programamos el boton salir
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setText("");
                direccion.setText("");
                telefono.setText("");
                nombre.setEnabled(true);
                direccion.setEnabled(true);
                telefono.setEnabled(true);

                buscado = false;
            }
        });


        //Ponemos el Listener de Click al boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.getText().toString().equals("")) {
                    alertaNombre.show();
                } else if (direccion.getText().toString().equals("")) {
                    alertaDireccion.show();
                } else if (telefono.getText().toString().equals("")) {
                    alertaTelefono.show();
                } else {
                    //Ocultar Teclado
                    InputMethodManager ocultarTeclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    ocultarTeclado.hideSoftInputFromWindow(telefono.getWindowToken(), 0);

                    if (comprobarCliente(db) || buscado==true){
                        LanzarActividad();
                    }


                }
            }
        });

    }

    public void LanzarActividad() {
        Intent intent = new Intent(MainActivity.this, EligeTortilla.class);

        //Recogemos los datos de los Text Box
        String nombreS = nombre.getText().toString();
        String direccionS = direccion.getText().toString();
        String TelefonoS = telefono.getText().toString();

        //Instaciamos los objetos que vamos a meter en el arrayList para pasarlo por parametro
        cliente = new Cliente();
        datos = new Datos();

        //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
        cliente.setNombre(nombreS);
        cliente.setDireccion(direccionS);
        cliente.setTelefono(TelefonoS);
        datos.setIdentificador("cliente");
        datos.setX(cliente);
        arrayParametros.add(datos);

        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        intent.putExtra("array", arrayParametros);


        //Lanzamos la siguiente actividad
        startActivity(intent);
    }



    public void buscarCliente() {
        if (nombre.getText().toString().equals("")) {
            alertaNombre.show();
        } else {
            String nombreS = nombre.getText().toString();
            String sql = "SELECT * from cliente where UPPER(nombre) ='" + nombreS.toUpperCase() + "'";
            Cursor c = db.rawQuery(sql, null);

            if (c.moveToNext()) {
                direccion.setText(c.getString(2));
                telefono.setText(c.getString(3));
                buscado=true;
                nombre.setEnabled(false);
                direccion.setEnabled(false);
                telefono.setEnabled(false);
            }
            else{
                nombre.setEnabled(true);
                direccion.setEnabled(true);
                telefono.setEnabled(true);
                buscado=false;
            }
        }

    }

    public boolean comprobarCliente(SQLiteDatabase db){
        Cursor cnombre = db.rawQuery("Select nombre from cliente where UPPER(nombre)='" + nombre.getText().toString().toUpperCase() + "'", null);
        if (cnombre.moveToNext()) {
            if (buscado != true) {
                Toast.makeText(MainActivity.this, "Ese nombre ya existe en la base de datos", Toast.LENGTH_LONG).show();
            }
        } else {
            Cursor cr = db.rawQuery("Select max(clienteID) from cliente", null);
            if (cr.moveToNext()) {
                db.execSQL("INSERT INTO cliente (ClienteID,nombre,direccion,telefono) VALUES(" + (cr.getInt(0) + 1) + ",'" + nombre.getText().toString() + "','" + direccion.getText().toString() + "','" + telefono.getText().toString()+"')");
                return true;
            }
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        arrayParametros.clear();
        nombre.setText("");
        direccion.setText("");
        telefono.setText("");
        nombre.setEnabled(true);
        direccion.setEnabled(true);
        telefono.setEnabled(true);

    }
}
