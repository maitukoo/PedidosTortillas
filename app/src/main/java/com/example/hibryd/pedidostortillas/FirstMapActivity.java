package com.example.hibryd.pedidostortillas;

/**
 * Created by adminportatil on 13/01/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;


        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class FirstMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirstMapFragment mFirstMapFragment;
    private Button inicio;
    private ImageButton llamar;
    private Button salir;
    private ArrayList<Datos> arrayParametros = new ArrayList<Datos>();
    private Datos datos;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 1);

        db = creartablas.getWritableDatabase();

        setContentView(R.layout.mapa);

        //Para Finalizar el programa desde la llamada del Resumen
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        inicio = (Button) findViewById(R.id.btnInicio);
        llamar = (ImageButton) findViewById(R.id.btnLlamar);
        salir = (Button) findViewById(R.id.btnSalirInicio);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mFirstMapFragment)
                .commit();

        // Registrar escucha onMapReadyCallback
        mFirstMapFragment.getMapAsync(this);

        //Lanzar a la 2 actividad
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarInicio(null);
            }
        });

        //Realizar la llamada
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLlamada(null);
            }
        });
    }


    //El codigo para generar y especificar el lugar del mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng cebanc = new LatLng(43.30469411639206, -2.0168709754943848);
        googleMap.addMarker(new MarkerOptions()
                .position(cebanc)
                .title("Tortillas Cebanc"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cebanc)
                .zoom(13)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void lanzarInicio(View view) {
        Intent inicio = new Intent(this, MainActivity.class);

        //Les asignamos los valores correspondientes a los objetos y añadimos al ArrayList
        datos = new Datos();
        datos.setIdentificador("baseDatos");
        datos.setX(db);
        arrayParametros.add(datos);

        //Pasamos por parametro el array List, esto lo podemos hacer porque todas las clases que componen el ArrayList Implementan Serializable
        inicio.putExtra("array",arrayParametros);
        startActivity(inicio);
    }

    //La llamada
    public void realizarLlamada(View view) {
        Intent llamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+619889687"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startActivity(llamada);
    }
}
