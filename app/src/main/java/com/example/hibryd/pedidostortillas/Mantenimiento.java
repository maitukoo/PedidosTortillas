package com.example.hibryd.pedidostortillas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Hibryd on 12/02/2017.
 */

public class Mantenimiento extends AppCompatActivity {
    private EditText articuloID;
    private EditText nombreArticulo;
    private EditText precioArticulo;
    private Button insertar;
    private Spinner spinnerTipo;
    private SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimiento_articulos);

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getReadableDatabase();

        articuloID = (EditText) findViewById(R.id.edtIdArticulo);
        nombreArticulo = (EditText) findViewById(R.id.NombreNuevo);
        precioArticulo = (EditText) findViewById(R.id.edtPrecioNuevo);
        insertar = (Button) findViewById(R.id.btnInsertar);
        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        articuloID.setEnabled(false);

        Cursor cr = db.rawQuery("SELECT max(productoID) FROM producto",null);
        if (cr.moveToNext()){
            articuloID.setText(String.valueOf(cr.getInt(0)));
        }


        ArrayAdapter<CharSequence> adaptadorTamanio = ArrayAdapter.createFromResource(this,R.array.Tipos,android.R.layout.simple_spinner_item);
        adaptadorTamanio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adaptadorTamanio);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarArticulo();
            }
        });
    }


    public void insertarArticulo(){
        if (nombreArticulo.getText().toString().equals("")){
            Toast.makeText(this,"Introduce el nombre del producto",Toast.LENGTH_LONG).show();
        } else if (precioArticulo.getText().toString().equals("")){
            Toast.makeText(this,"Introduce el precio del producto",Toast.LENGTH_LONG).show();
        } else {
            comprobarInsertar();
        }
    }

    public void comprobarInsertar(){

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getWritableDatabase();

        int siguienteArticulo = Integer.valueOf(articuloID.getText().toString()) + 1;

        if (spinnerTipo.getSelectedItem().toString().equals("Bebida")){

            if (comprobarProducto()) {

                db.execSQL("INSERT INTO producto (productoID,nombre,precioUnitario) VALUES(" + siguienteArticulo + ",'" + nombreArticulo.getText().toString() + "','" + precioArticulo.getText().toString() + "')");
                Toast.makeText(this, "Bebida insertada con exito", Toast.LENGTH_LONG).show();
                articuloID.setText(String.valueOf(siguienteArticulo));
            }


        } else if (spinnerTipo.getSelectedItem().toString().equals("Tortilla")){

            if (comprobarProducto()) {
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + siguienteArticulo + ",'" + nombreArticulo.getText().toString() + "','Granja','Individual'," + Float.valueOf(precioArticulo.getText().toString()) + ",'tpatata')");
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + (siguienteArticulo + 1) + ",'" + nombreArticulo.getText().toString() + "','Campero','Individual'," + (Float.valueOf(precioArticulo.getText().toString()) + 2) + ",'tpatata')");
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + (siguienteArticulo + 2) + ",'" + nombreArticulo.getText().toString() + "','Ecologico','Individual'," + (Float.valueOf(precioArticulo.getText().toString()) + 3) + ",'tpatata')");
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + (siguienteArticulo + 3) + ",'" + nombreArticulo.getText().toString() + "','Granja','Familiar'," + (Float.valueOf(precioArticulo.getText().toString()) + 5) + ",'tpatata')");
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + (siguienteArticulo + 4) + ",'" + nombreArticulo.getText().toString() + "','Campero','Familiar'," + (Float.valueOf(precioArticulo.getText().toString()) + 7) + ",'tpatata')");
                db.execSQL("INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES(" + (siguienteArticulo + 5) + ",'" + nombreArticulo.getText().toString() + "','Ecologico','Familiar'," + (Float.valueOf(precioArticulo.getText().toString()) + 8) + ",'tpatata')");

                Toast.makeText(this, "Tortilla insertada con exito", Toast.LENGTH_LONG).show();
                siguienteArticulo += 5;
                articuloID.setText(String.valueOf(siguienteArticulo));
            }
        }
    }

    public boolean comprobarProducto(){
        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getReadableDatabase();

        Cursor cr = db.rawQuery("Select * FROM producto where nombre ='" + nombreArticulo.getText().toString() + "'",null);

        if (cr.moveToNext()){
            Toast.makeText(this, "Este porducto ya existe", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}