package com.example.hibryd.pedidostortillas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreacionTablas extends SQLiteOpenHelper implements Serializable {

    //Sentencia SQL para crear la tabla de Usuarios
    String tablaCliente = "CREATE TABLE cliente (clienteID INTEGER PRIMARY KEY, nombre TEXT, direccion TEXT, telefono TEXT)";
    String ventaCabecera = "CREATE TABLE ventaCabecera (cabeceraID INTEGER PRIMARY KEY, clienteID INTEGER, fechaPedido DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (clienteID) REFERENCES cliente (clienteID) ON DELETE CASCADE)";
    String ventaLinea = "CREATE TABLE ventaLinea (ventaLineaID INTEGER,cabeceraID INTEGER, " +
            "productoID INTEGER, cantidad INTEGER, precioUnitario REAL, FOREIGN KEY (cabeceraID) REFERENCES ventaCabecera (cabeceraID) ON DELETE CASCADE," +
            "FOREIGN KEY (productoID) REFERENCES producto (productoID) ON DELETE CASCADE, PRIMARY KEY (ventaLineaID, cabeceraID))";
    String producto = "CREATE TABLE producto (productoID INTEGER PRIMARY KEY, nombre TEXT, tipoHuevo TEXT, tamanio TEXT, precioUnitario REAL, imagen TEXT)";
    String usuario = "CREATE TABLE usuario (usuario TEXT PRIMARY KEY, contrasenia TEXT)";

    String insertaradmin = "INSERT INTO usuario VALUES('admin','admin')";

    String insertarCola = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (1,'Coca-cola',1.5)";
    String insertarLimon = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (2,'Kas-limon',1.5)";
    String insertarNaranja = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (3,'Kas-naranja',1.5)";
    String insertarNestea = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (4,'Nestea',2)";
    String insertarCerveza = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (5,'Cerveza',2)";
    String insertarAgua = "INSERT INTO producto (productoID,nombre,precioUnitario) VALUES (6,'Agua',1)";

    String insertarTortilla1 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (7,'Tpatata','Granja','Individual',6, 'tpatata')";
    String insertarTortilla2 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (8,'Tpatata','Campero','Individual',8, 'tpatata')";
    String insertarTortilla3 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (9,'Tpatata','Ecologico','Individual',9, 'tpatata')";
    String insertarTortilla4 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (10,'Tpatata','Granja','Familiar',11, 'tpatata')";
    String insertarTortilla5 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (11,'Tpatata','Campero','Familiar',13, 'tpatata')";
    String insertarTortilla6 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (12,'Tpatata','Ecologico','Familiar',14, 'tpatata')";

    String insertarTortilla7 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (13,'Tverduras','Granja','Individual',6, 'tverdura')";
    String insertarTortilla8 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (14,'Tverduras','Campero','Individual',8, 'tverdura')";
    String insertarTortilla9 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (15,'Tverduras','Ecologico','Individual',9, 'tverdura')";
    String insertarTortilla10 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (16,'Tverduras','Granja','Familiar',11,'tverdura')";
    String insertarTortilla11 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (17,'Tverduras','Campero','Familiar',13,'tverdura')";
    String insertarTortilla12 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (18,'Tverduras','Ecologico','Familiar',14,'tverdura')";

    String insertarTortilla13 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (19,'Tbacalao','Granja','Individual',8,'tbacalao')";
    String insertarTortilla14 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (20,'Tbacalao','Campero','Individual',10,'tbacalao')";
    String insertarTortilla15 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (21,'Tbacalao','Ecologico','Individual',11,'tbacalao')";
    String insertarTortilla16 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (22,'Tbacalao','Granja','Familiar',13,'tbacalao')";
    String insertarTortilla17 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (23,'Tbacalao','Campero','Familiar',15,'tbacalao')";
    String insertarTortilla18 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (24,'Tbacalao','Ecologico','Familiar',16,'tbacalao')";

    String insertarTortilla19 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (25,'TJIberico','Granja','Individual',10,'tjamon')";
    String insertarTortilla20 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (26,'TJIberico','Campero','Individual',12,'tjamon')";
    String insertarTortilla21 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (27,'TJIberico','Ecologico','Individual',13,'tjamon')";
    String insertarTortilla22 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (28,'TJIberico','Granja','Familiar',15,'tjamon')";
    String insertarTortilla23 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (29,'TJIberico','Campero','Familiar',17,'tjamon')";
    String insertarTortilla24 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (30,'TJIberico','Ecologico','Familiar',18,'tjamon')";

    String insertarTortilla25 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (31,'TQIdiazabal','Granja','Individual',10,'tpatata')";
    String insertarTortilla26 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (32,'TQIdiazabal','Campero','Individual',12,'tpatata')";
    String insertarTortilla27 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (33,'TQIdiazabal','Ecologico','Individual',13,'tpatata')";
    String insertarTortilla28 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (34,'TQIdiazabal','Granja','Familiar',15,'tpatata')";
    String insertarTortilla29 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (35,'TQIdiazabal','Campero','Familiar',17,'tpatata')";
    String insertarTortilla30 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (36,'TQIdiazabal','Ecologico','Familiar',18,'tpatata')";

    String insertarTortilla31 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (37,'Thongos','Granja','Individual',9,'thongos')";
    String insertarTortilla32 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (38,'Thongos','Campero','Individual',11,'thongos')";
    String insertarTortilla33 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (39,'Thongos','Ecologico','Individual',12,'thongos')";
    String insertarTortilla34 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (40,'Thongos','Granja','Familiar',14,'thongos')";
    String insertarTortilla35 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (41,'Thongos','Campero','Familiar',16,'thongos')";
    String insertarTortilla36 = "INSERT INTO producto (productoID,nombre,tipoHuevo,tamanio,precioUnitario,imagen) VALUES (42,'Thongos','Ecologico','Familiar',17,'thongos')";

    String insertarCabecera= "INSERT INTO ventaCabecera (cabeceraID,clienteID,fechaPedido) VALUES(1,1," + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + ")";
    String insertarLinea= "INSERT INTO ventaLinea (ventaLineaID,productoID,cantidad,precioUnitario) VALUES(1,1,3,6)";

    String insertarCliente = "INSERT INTO cliente (clienteID,nombre,direccion,telefono) VALUES (1,'Mikel','Latxumbe-berri 25 4-A',650560079)";
    String insertarCliente2 = "INSERT INTO cliente (clienteID,nombre,direccion,telefono) VALUES (2,'Oscar','Donostia xdddmemes',666666666)";


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
        db.execSQL(usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        ArrayList<String> listaproductos = new ArrayList<String>();
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE ventaLinea");
        db.execSQL("DROP TABLE ventaCabecera");
        db.execSQL("DROP TABLE cliente");
        db.execSQL("DROP TABLE producto");
        db.execSQL("DROP TABLE usuario");

        db.execSQL(tablaCliente);
        db.execSQL(ventaCabecera);
        db.execSQL(ventaLinea);
        db.execSQL(producto);
        db.execSQL(usuario);
        db.execSQL(insertaradmin);

        db.execSQL(insertarCola);
        db.execSQL(insertarLimon);
        db.execSQL(insertarNaranja);
        db.execSQL(insertarNestea);
        db.execSQL(insertarCerveza);
        db.execSQL(insertarAgua);
        db.execSQL(insertarCliente);
        db.execSQL(insertarCliente2);
        db.execSQL(insertarTortilla1);
        db.execSQL(insertarTortilla2);
        db.execSQL(insertarTortilla3);
        db.execSQL(insertarTortilla4);
        db.execSQL(insertarTortilla5);
        db.execSQL(insertarTortilla6);
        db.execSQL(insertarTortilla7);
        db.execSQL(insertarTortilla8);
        db.execSQL(insertarTortilla9);
        db.execSQL(insertarTortilla10);
        db.execSQL(insertarTortilla11);
        db.execSQL(insertarTortilla12);
        db.execSQL(insertarTortilla13);
        db.execSQL(insertarTortilla14);
        db.execSQL(insertarTortilla15);
        db.execSQL(insertarTortilla16);
        db.execSQL(insertarTortilla17);
        db.execSQL(insertarTortilla18);
        db.execSQL(insertarTortilla19);
        db.execSQL(insertarTortilla20);
        db.execSQL(insertarTortilla21);
        db.execSQL(insertarTortilla22);
        db.execSQL(insertarTortilla23);
        db.execSQL(insertarTortilla24);
        db.execSQL(insertarTortilla25);
        db.execSQL(insertarTortilla26);
        db.execSQL(insertarTortilla27);
        db.execSQL(insertarTortilla28);
        db.execSQL(insertarTortilla29);
        db.execSQL(insertarTortilla30);
        db.execSQL(insertarTortilla31);
        db.execSQL(insertarTortilla32);
        db.execSQL(insertarTortilla33);
        db.execSQL(insertarTortilla34);
        db.execSQL(insertarTortilla35);
        db.execSQL(insertarTortilla36);
        db.execSQL(insertarCabecera);
        db.execSQL(insertarLinea);


        //Se crea la nueva versión de la tabla
    }
}
