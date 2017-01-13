package com.example.hibryd.pedidostortillas;

/**
 * Created by adminportatil on 13/01/2017.
 */

import com.google.android.gms.maps.SupportMapFragment;


        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.google.android.gms.maps.SupportMapFragment;

//Mostrar mapa

public class FirstMapFragment extends SupportMapFragment {

    public FirstMapFragment() {
    }

    public static FirstMapFragment newInstance() {
        return new FirstMapFragment();
    }

    //szD
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }

}
