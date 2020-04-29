package com.valentelmadafaka.gesmobapp.ui.home;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button crearTarea = root.findViewById(R.id.crearTareaButton);
        crearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TareaForm.class));
            }
        });

        ArrayList<Tarea> tareas = new ArrayList<>();

        GesMobDB gesMobDB = new GesMobDB(getActivity());
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerTareas();
        c.moveToFirst();

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
            tareas.add(t);
            c.moveToNext();
        }

        gesMobDB.close();
        TareaArray tareaArray = new TareaArray(getActivity(), R.layout.tarea_view, tareas);
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

        return root;
    }
}
