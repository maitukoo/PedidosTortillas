package com.example.hibryd.pedidostortillas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button siguiente;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private Datos datos;
    private Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos los objetos a las vistas
        nombre = (EditText) findViewById(R.id.edtNombre);
        direccion = (EditText) findViewById(R.id.edtDireccion);
        telefono = (EditText) findViewById(R.id.edtTelefono);
        siguiente = (Button) findViewById(R.id.SiguienteDatos);

        //Ponemos el Listener de Click al boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LanzarActividad();
            }
        });
    }

    public void LanzarActividad(){
        Intent intent = new Intent(MainActivity.this,EligeTortilla.class);

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
        intent.putExtra("array",arrayParametros);

        //Lanzamos la siguiente actividad
        startActivity(intent);
    }
}
