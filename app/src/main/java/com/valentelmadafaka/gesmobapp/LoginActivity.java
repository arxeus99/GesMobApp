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
import com.valentelmadafaka.gesmobapp.model.Usuario;
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
            Cursor c = bd.obtenerUsuario(usuario.getText().toString());
            if(c.getCount() == 0){
                respuesta.setText("Usuario no encontrado");
            }else{
                c.moveToFirst();
                Usuario usuario = new Usuario();
                usuario.setId(c.getString(0));
                usuario.setNombre(c.getString(1));
                usuario.setEmail(c.getString(2));
                usuario.setTipo(c.getString(3));
                if(usuario.getTipo().equals("profesor")){
                    respuesta.setText("La aplicación de profesor está en proceso");
                }else{
                    PreferencesHelper.desaUsuari("User", usuario, this);
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
            bd.close();
        }else{
            respuesta.setText("Contraseña incorrecta");
        }
    }

    @Override
    public void onBackPressed(){
        this.finishAffinity();
    }
}
