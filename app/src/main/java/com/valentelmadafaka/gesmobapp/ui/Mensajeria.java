package com.valentelmadafaka.gesmobapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Mensaje;
import com.valentelmadafaka.gesmobapp.model.MensajeArray;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class Mensajeria extends AppCompatActivity {

    GesMobDB bd;
    EditText mensajeText;
    ArrayList<Mensaje> mensajes = new ArrayList<>();
    MensajeArray mensajeArray;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mensajeText = findViewById(R.id.mensaje);

        bd = new GesMobDB(this);
        bd.open();
        Cursor c = bd.obtenerMensajes();
        c.moveToFirst();
        while(!c.isAfterLast()){
            Mensaje mensaje = new Mensaje();
            mensaje.setId(c.getString(0));
            mensaje.setContenido(c.getString(1));
            mensaje.setIdEmisor(c.getString(2));
            mensaje.setIdReceptor(c.getString(3));
            if(c.getInt(4) == 1){
                mensaje.setLeido(true);
            }else{
                mensaje.setLeido(false);
            }
            mensajes.add(mensaje);
            c.moveToNext();
        }
        bd.close();

        mensajeArray = new MensajeArray(this, R.layout.mensaje_enviado, mensajes);
        listView = findViewById(R.id.messages_view);
        listView.setAdapter(mensajeArray);

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
        Cursor c = bd.obtenerAlumno(Integer.parseInt(PreferencesHelper.recuperarUsuari("User", this).getId()));
        Mensaje mensaje = new Mensaje();
        mensaje.setId(bd.obtenerNumeroMensajes()+"");
        mensaje.setIdEmisor(PreferencesHelper.recuperarUsuari("User", this).getId());
        mensaje.setIdReceptor(c.getString(3));
        mensaje.setContenido(mensajeText.getText().toString());
        mensaje.setLeido(false);
        //Toast.makeText(this, mensaje.getContenido(), Toast.LENGTH_SHORT).show();
        bd.insertaMensaje(mensaje);
        bd.close();
        mensajeText.setText("");
        mensajes.add(mensaje);
        mensajeArray.notifyDataSetChanged();

    }
}
