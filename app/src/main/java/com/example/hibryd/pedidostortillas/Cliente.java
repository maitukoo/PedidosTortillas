package com.example.hibryd.pedidostortillas;

import java.io.Serializable;

/**
 * Created by Hibryd on 06/12/2016.
 */

public class Cliente implements Serializable {
    private String nombre;
    private String direccion;
    private String telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
