package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;

import java.util.ArrayList;

public class TareaArray extends ArrayAdapter<Tarea> {

    private Context context;
    private ArrayList<Tarea> tareaArrayList;

    public TareaArray(Context context, int resource, ArrayList<Tarea> tareas){
        super(context, resource, tareas);
        this.context = context;
        this.tareaArrayList = tareas;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        Tarea tarea = tareaArrayList.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tarea_view, null);
        TextView titulo = view.findViewById(R.id.tituloTarea);
        TextView horas = view.findViewById(R.id.horasTarea);
        CheckBox hecho = view.findViewById(R.id.hecho);

        titulo.setText(tarea.getNombre());
        horas.setText(tarea.getHoras()+"h");
        if(tarea.isRealizada()){
            hecho.setChecked(true);
        }else{
            hecho.setChecked(false);
        }

        return view;
    }
}
