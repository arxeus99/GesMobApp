package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;

import java.util.ArrayList;

public class SemanaArray extends ArrayAdapter<Semana> {

    private Context context;
    private ArrayList<Semana> semanas;

    public SemanaArray(Context context, int resource, ArrayList<Semana> semanas){
        super(context, resource, semanas);
        this.context = context;
        this.semanas = semanas;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Semana semana = semanas.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.semana_view, null);
        TextView titulo = view.findViewById(R.id.tituloSemana);
        TextView fechas = view.findViewById(R.id.fechasSemana);

        titulo.setText("Semana "+semana.getId());
        fechas.setText(semana.getInicio()+" - "+semana.getFin());

        return view;
    }
}
