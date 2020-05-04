package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class ObservacionArray extends ArrayAdapter<Observacion> {

    private Context context;
    private ArrayList<Observacion> observacionArrayList;
    private GesMobDB gesMobDB;

    public ObservacionArray(Context context, int resource, ArrayList<Observacion> observacionArrayList){
        super(context, resource, observacionArrayList);
        this.context = context;
        this.observacionArrayList = observacionArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        Observacion observacion = observacionArrayList.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.observacion_view, null);
        TextView autor = view.findViewById(R.id.nombre);
        TextView contenido = view.findViewById(R.id.contenido);
        gesMobDB = new GesMobDB(context);
        gesMobDB.open();
        Cursor cursor = gesMobDB.obtenerUsuario(Integer.parseInt(observacion.getAutorId()));
        autor.setText(cursor.getString(1));
        contenido.setText(observacion.getContenido());
        gesMobDB.close();
        return view;
    }
}
