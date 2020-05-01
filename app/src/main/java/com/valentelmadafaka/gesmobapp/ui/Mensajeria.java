package com.valentelmadafaka.gesmobapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Mensaje;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class Mensajeria extends AppCompatActivity {

    GesMobDB bd;
    EditText mensajeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mensajeText = findViewById(R.id.mensaje);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void enviarVoid(View view) {
        bd = new GesMobDB(this);
        bd.open();
        Mensaje mensaje = new Mensaje();
        mensaje.setId(bd.obtenerNumeroMensajes()+"");
        mensaje.setIdEmisor(PreferencesHelper.recuperarUsuari("User", this).getId());
        mensaje.setIdReceptor(PreferencesHelper.recuperarUsuari("User", this).getIdProfesor());
        mensaje.setContenido(mensajeText.getText().toString());
        mensaje.setLeido(false);
        Toast.makeText(this, mensaje.getContenido(), Toast.LENGTH_SHORT).show();
        mensajeText.setText("");
    }
}
