package com.example.hibryd.pedidostortillas;

import java.io.Serializable;

/**
 * Created by adminportatil on 07/12/2016.
 */

public class Tortilla implements Serializable {

    private String tipoTortilla;
    private String tipoHuevos;
    private int cantidad;
    private String tamanio;



    private double precioUnitario;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



    public String getTipoTortilla() {
        return tipoTortilla;
    }

    public void setTipoTortilla(String tipoTortilla) {
        this.tipoTortilla = tipoTortilla;
    }

    public String getTipoHuevos() {
        return tipoHuevos;
    }

    public void setTipoHuevos(String tipoHuevos) {
        this.tipoHuevos = tipoHuevos;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


}
