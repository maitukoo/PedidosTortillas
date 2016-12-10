package com.example.hibryd.pedidostortillas;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by adminportatil on 09/12/2016.
 */

public class Datos implements Serializable {
    private String identificador;

    public Object getX() {
        return x;
    }

    public void setX(Object x) {
        this.x = x;
    }

    private Object x;


    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }






}
