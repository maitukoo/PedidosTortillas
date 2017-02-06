package com.example.hibryd.pedidostortillas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CreacionTablas extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    String tablaCliente = "CREATE TABLE cliente (clienteID INTEGER, nombre TEXT, direccion TEXT, telefono INTEGER)";
    String ventaCabecera = "CREATE TABLE ventaCabecera (cabeceraID INTEGER, clienteID INTEGER, fechaPedido DATE)";
    String ventaLinea = "CREATE TABLE ventaLinea (ventaLineaID INTEGER, productoID INTEGER, cantidad INTEGER, precioUnitario REAL)";
    String producto = "CREATE TABLE producto (productoID INTEGER, nombre TEXT, tipoHuevo TEXT, tamanio TEXT, precioUnitario REAL)";


    public CreacionTablas(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(tablaCliente);
        db.execSQL(ventaCabecera);
        db.execSQL(ventaLinea);
        db.execSQL(producto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla


        //Se crea la nueva versión de la tabla
    }
}
