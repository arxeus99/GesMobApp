package com.valentelmadafaka.gesmobapp.ui.home.page;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.ui.home.HomeViewModel;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    GesMobDB gesMobDB;
    ArrayList<Tarea> tareas = new ArrayList<>();
    TareaArray tareaArray;
    ArrayList<Semana> semanas = new ArrayList<>();
    int semanaActual;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        semanas = pageViewModel.getSemanas();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home_page, container, false);

        final TextView textView = root.findViewById(R.id.semana);
        final TextView fechas = root.findViewById(R.id.fechas);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                String[] splited = textView.getText().toString().split(" ");
                semanaActual = Integer.parseInt(splited[1])-1;
                Semana semana = semanas.get(semanaActual);
                fechas.setText(semana.getInicio()+" - "+semana.getFin());


                gesMobDB = new GesMobDB(getActivity());
                gesMobDB.open();
                Cursor c = gesMobDB.obtenerTareas();
                c.moveToFirst();
                tareas.clear();
                while(!c.isAfterLast()){
                    Tarea t = new Tarea();
                    t.setId(c.getString(0));
                    t.setNombre(c.getString(1));
                    t.setDescripcion(c.getString(2));
                    t.setFecha(c.getString(3));
                    t.setHoras(c.getInt(4));
                    if(c.getInt(5) == 1){
                        t.setRealizada(true);
                    }else{
                        t.setRealizada(false);
                    }
                    t.setIdAlumno(c.getString(6));
                    t.setIdProfesor(c.getString(7));
                    try {
                        if(saberFecha(dateFormat.parse(semana.getInicio()), dateFormat.parse(semana.getFin()), dateFormat.parse(t.getFecha())))
                            tareas.add(t);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c.moveToNext();
                }

                gesMobDB.close();
                tareaArray = new TareaArray(getActivity(), R.layout.tarea_view, tareas);
                ListView listView = root.findViewById(R.id.tareas);
                listView.setAdapter(tareaArray);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Tarea tarea = (Tarea)parent.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), TareaDetail.class);
                        intent.putExtra("tarea", tarea);
                        startActivity(intent);

                    }
                });
            }
        });

        final Button crearTarea = root.findViewById(R.id.crearTareaButton);
        crearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), TareaForm.class), 11);
            }
        });

        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 11){
            if(data.hasExtra("tarea")){
                Tarea t = (Tarea)data.getExtras().get("tarea");
                gesMobDB = new GesMobDB(getActivity());
                gesMobDB.open();
                if(gesMobDB.insertaTarea(t) == -1){
                    Toast.makeText(getActivity(), "Error a l'afegir", Toast.LENGTH_SHORT).show();
                }
                gesMobDB.close();
                tareas.add(t);
                tareaArray.notifyDataSetChanged();

            }
        }
    }

    public static boolean saberFecha(Date inicio, Date fin, Date actual){
        return inicio.compareTo(actual) * actual.compareTo(fin) >=0;
    }
}
