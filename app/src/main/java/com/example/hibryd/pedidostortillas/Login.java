package com.example.hibryd.pedidostortillas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hibryd on 12/02/2017.
 */

public class Login extends AppCompatActivity {
    private EditText usuario;
    private EditText contrasenia;
    private Button entrar;
    private SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        CreacionTablas creartablas =
                new CreacionTablas(this, "DBUsuarios", null, 22);
        db = creartablas.getReadableDatabase();

        usuario = (EditText) findViewById(R.id.edtUsuario);
        contrasenia = (EditText) findViewById(R.id.edtContrasenia);
        entrar = (Button) findViewById(R.id.btnEntrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarUsuarioContrasenia();
            }
        });


    }

    public void comprobarUsuarioContrasenia(){
        if (usuario.getText().toString().equals("")){
            Toast.makeText(this,"Introduce un Usuario",Toast.LENGTH_LONG).show();
        } else if (contrasenia.getText().toString().equals("")){
            Toast.makeText(this,"Introduce la contraseña",Toast.LENGTH_LONG).show();
        } else {
            String usuariostr = usuario.getText().toString();
            String contrastr = contrasenia.getText().toString();
            comprobarDatosDB(usuariostr,contrastr);
        }
    }

    

    public void comprobarDatosDB (String usuario, String contrasenia){

        Cursor cr = db.rawQuery("Select * FROM usuario where usuario ='" + usuario + "' AND contrasenia ='" + contrasenia + "'",null);

        if (cr.moveToNext()){
            LanzarActividad();
        } else {
            Toast.makeText(this,"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
        }
    }

    public void LanzarActividad(){
        Intent intent = new Intent(Login.this,Mantenimiento.class);

        //Lanzamos la siguiente actividad
        startActivity(intent);
        finish();

    }


}
