package com.valentelmadafaka.gesmobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Profesor;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usuario, contraseña;
    TextView respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.nombreUsuario);
        contraseña = findViewById(R.id.passwordUser);
        respuesta = findViewById(R.id.respuesta);
    }

    public void loginVoid(View view) {
        if(contraseña.getText().toString().equals("12345")){
            GesMobDB bd = new GesMobDB(this);
            bd.open();
            Cursor c = bd.obtenerProfesor(usuario.getText().toString());
            if(c.getCount() == 0){
                c = bd.obtenerAlumno(usuario.getText().toString());
                if(c.getCount() == 0){
                    respuesta.setText("Usuario no encontrado");
                }else{
                    c.moveToFirst();
                    Alumno alumno = new Alumno();
                    alumno.setId(c.getString(0));
                    alumno.setNombre(c.getString(1));
                    alumno.setEmail(c.getString(2));
                    alumno.setIdEmpresa(c.getString(3));
                    alumno.setDireccion(c.getString(4));
                    alumno.setIdProfesor(c.getString(5));
                    PreferencesHelper.desaUsuari("User", alumno, this);
                    startActivity(new Intent(this, MainActivity.class));
                }
            }else{
                c.moveToFirst();
                Profesor profesor = new Profesor();
                profesor.setId(c.getString(0));
                profesor.setNombre(c.getString(1));
                profesor.setEmail(c.getString(2));
                PreferencesHelper.desaUsuari("User", profesor, this);
                startActivity(new Intent(this, MainActivity.class));
            }
            bd.close();
        }else{
            respuesta.setText("Contraseña incorrecta");
        }
    }
}
