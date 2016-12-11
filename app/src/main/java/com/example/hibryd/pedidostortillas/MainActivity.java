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
    private Cliente cliente;
    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private Datos objeto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos los objetos a las vistas
        nombre = (EditText) findViewById(R.id.edtNombre);
        direccion = (EditText) findViewById(R.id.edtDireccion);
        telefono = (EditText) findViewById(R.id.edtTelefono);
        siguiente = (Button) findViewById(R.id.Siguiente1);

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

        //Inicializamos los objetos que vamos a meter en el arrayList para pasarlo por parametro
        cliente = new Cliente();
        objeto = new Datos();

        //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
        cliente.setNombre(nombreS);
        cliente.setDireccion(direccionS);
        cliente.setTelefono(TelefonoS);
        objeto.setIdentificador("cliente");
        objeto.setX(cliente);
        arrayParametros.add(objeto);

        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        intent.putExtra("array",arrayParametros);

        //Lanzamos la siguiente actividad
        startActivity(intent);
    }
}
