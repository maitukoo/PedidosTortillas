package com.example.hibryd.pedidostortillas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button siguiente;
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

        nombre = (EditText) findViewById(R.id.edtNombre);
        direccion = (EditText) findViewById(R.id.edtDireccion);
        telefono = (EditText) findViewById(R.id.edtTelefono);



        siguiente = (Button) findViewById(R.id.Siguiente1);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LanzarActividad();
            }
        });
    }
    public void LanzarActividad(){
        Intent intent = new Intent(MainActivity.this,EligeTortilla.class);
        String a = nombre.getText().toString();
        String b = direccion.getText().toString();
        String c = telefono.getText().toString();
        cliente = new Cliente();
        objeto = new Datos();
        cliente.setNombre(a);
        cliente.setDireccion(b);
        cliente.setTelefono(c);
        objeto.setIdentificador("cliente");
        objeto.setX(cliente);
        arrayParametros.add(objeto);
        intent.putExtra("array",arrayParametros);
        startActivity(intent);
    }
}
