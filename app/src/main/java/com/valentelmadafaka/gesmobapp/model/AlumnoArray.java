package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class AlumnoArray extends ArrayAdapter<Usuario> {

    private Context context;
    private ArrayList<Usuario> alumnos;

    public AlumnoArray(Context context, int resource, ArrayList<Usuario> alumnos){
        super(context, resource, alumnos);
        this.context = context;
        this.alumnos = alumnos;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        Usuario usuario = alumnos.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alumno_view, null);
        TextView nombre = view.findViewById(R.id.nombreAlumno);
        nombre.setText(usuario.getNombre());
        return view;
    }
}
