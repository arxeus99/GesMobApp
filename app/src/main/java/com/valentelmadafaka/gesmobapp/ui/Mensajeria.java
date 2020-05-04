package com.valentelmadafaka.gesmobapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        for(Mensaje m : mensajes){
            if(!m.getIdEmisor().equals(PreferencesHelper.recuperarUsuari("User", this).getId())){
               m.setLeido(true);
               bd.leerMensaje(Integer.parseInt(m.getId()));
            }
        }
        bd.close();

        mensajeArray = new MensajeArray(this, R.layout.mensaje_enviado, mensajes);
        listView = findViewById(R.id.messages_view);
        listView.setAdapter(mensajeArray);
        listView.setSelection(mensajeArray.getCount()-1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void enviarVoid(View view) {
        if(!mensajeText.getText().toString().isEmpty()){
            bd = new GesMobDB(this);
            bd.open();
            Cursor c = bd.obtenerAlumno(Integer.parseInt(PreferencesHelper.recuperarUsuari("User", this).getId()));
            c.moveToFirst();
            Mensaje mensaje = new Mensaje();
            mensaje.setId((bd.obtenerNumeroMensajes()+1)+"");
            mensaje.setIdEmisor(PreferencesHelper.recuperarUsuari("User", this).getId());
            mensaje.setIdReceptor(c.getString(3));
            mensaje.setContenido(mensajeText.getText().toString());
            mensaje.setLeido(false);
            //Toast.makeText(this, mensaje.getContenido(), Toast.LENGTH_SHORT).show();
            if(bd.insertaMensaje(mensaje) == -1){
                Toast.makeText(this, "Error al afegir missatges", Toast.LENGTH_SHORT).show();
            }
            bd.close();
            mensajeText.setText("");
            mensajes.add(mensaje);
            mensajeArray.notifyDataSetChanged();
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view1 = this.getCurrentFocus();

            if(view1 == null){
                view1 = new View(this);
            }

            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);

            listView.setSelection(mensajeArray.getCount()-1);
        }
    }
}
