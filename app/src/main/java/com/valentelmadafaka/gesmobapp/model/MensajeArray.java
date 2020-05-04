package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class MensajeArray extends ArrayAdapter<Mensaje> {

    private Context context;
    private ArrayList<Mensaje> mensajes;
    private GesMobDB gesMobDB;

    public MensajeArray(Context context, int resource, ArrayList<Mensaje> mensajes){
        super(context, resource, mensajes);
        this.context = context;
        this.mensajes = mensajes;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        Mensaje mensaje = mensajes.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view;
        if(mensaje.getIdEmisor().equals(PreferencesHelper.recuperarUsuari("User", context).getId())){
            if(mensaje.isLeido()){
                view = inflater.inflate(R.layout.mensaje_abierto, null);
            }else{
                view = inflater.inflate(R.layout.mensaje_enviado, null);
            }
        }else{
            view = inflater.inflate(R.layout.mensaje_recibido, null);
            TextView name = view.findViewById(R.id.name);
            gesMobDB = new GesMobDB(context);
            gesMobDB.open();
            Usuario alumno = PreferencesHelper.recuperarUsuari("User", context);
            Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(alumno.getId()));
            c.moveToFirst();
            Cursor profesor = gesMobDB.obtenerUsuario(c.getInt(3));
            profesor.moveToFirst();
            name.setText(profesor.getString(1));
            gesMobDB.close();
        }
        TextView messageBody = view.findViewById(R.id.message_body);
        messageBody.setText(mensaje.getContenido());
        return view;
    }
}
