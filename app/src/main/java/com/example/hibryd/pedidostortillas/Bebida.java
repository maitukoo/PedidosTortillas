package com.example.hibryd.pedidostortillas;

import java.io.Serializable;

/**
 * Created by adminportatil on 14/12/2016.
 */

public class Bebida implements Serializable {
    private Double numeroBebidas;
    private String tipoBebida;
    private double precioUnitario;

    public Double getNumeroBebidas() {
        return numeroBebidas;
    }

    public void setNumeroBebidas(Double numeroBebidas) {
        this.numeroBebidas = numeroBebidas;
    }

    public String getTipoBebida() {
        return tipoBebida;
    }

    public void setTipoBebida(String tipoBebida) {
        this.tipoBebida = tipoBebida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


}
