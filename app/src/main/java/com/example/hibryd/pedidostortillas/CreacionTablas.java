package com.example.hibryd.pedidostortillas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class CreacionTablas extends SQLiteOpenHelper implements Serializable {

    //Sentencia SQL para crear la tabla de Usuarios
    String tablaCliente = "CREATE TABLE cliente (clienteID INTEGER PRIMARY KEY, nombre TEXT, direccion TEXT, telefono INTEGER)";
    String ventaCabecera = "CREATE TABLE ventaCabecera (cabeceraID INTEGER PRIMARY KEY, clienteID INTEGER, fechaPedido DATE) FOREIGN KEY (clienteID) REFERENCES cliente (clienteID)";
    String ventaLinea = "CREATE TABLE ventaLinea (ventaLineaID INTEGER PRIMARY KEY,cabeceraID INTEGER,productoID INTEGER " +
            "productoID INTEGER, cantidad INTEGER, precioUnitario REAL FOREING KEY (cabeceraID) REFERENCES ventaCabecera (cabeceraID))" +
            "FOREIGN KEY (productoID) REFERENCES producto (productoID)";
    String producto = "CREATE TABLE producto (productoID INTEGER PRIMARY KEY, nombre TEXT, tipoHuevo TEXT, tamanio TEXT, precioUnitario REAL)";

    String insertarCola = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (1,'Coca-cola',1.5)";
    String insertarLimon = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (2,'Kas-limon',1.5)";
    String insertarNaranja = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (3,'Kas-naranja',1.5)";
    String insertarNestea = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (2,'Nestea',2)";
    String insertarCerveza = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (2,'Cerveza',2)";
    String insertarAgua = "INSERT INTO producto (productoID,nombre,precioUniterio) VALUES (2,'Kas-limon',1)";
    String insertarCliente = "INSERT INTO cliente (clienteID,nombre,direccion,telefono) VALUES (1,'Mikel','Latxumbe-berri 25 4-A',650560079)";
    String insertarCliente2 = "INSERT INTO cliente (clienteID,nombre,direccion,telefono) VALUES (2,'Oscar','Donostia xdddmemes',666666666)";


    public CreacionTablas(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    public void ejecutarSentencia(String sql){

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
        db.execSQL("DROP TABLE cliente");
        db.execSQL("DROP TABLE ventaCabecera");
        db.execSQL("DROP TABLE ventaLinea");
        db.execSQL("DROP TABLE producto");
        db.execSQL(tablaCliente);
        db.execSQL(ventaCabecera);
        db.execSQL(ventaLinea);
        db.execSQL(producto);
        db.execSQL(insertarCola);
        db.execSQL(insertarLimon);
        db.execSQL(insertarNaranja);
        db.execSQL(insertarNestea);
        db.execSQL(insertarCerveza);
        db.execSQL(insertarAgua);
        db.execSQL(insertarCliente);
        db.execSQL(insertarCliente2);


        //Se crea la nueva versión de la tabla
    }
}
