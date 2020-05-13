package com.valentelmadafaka.gesmobapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.empresa.EmpresaViewModel;
import com.valentelmadafaka.gesmobapp.ui.home.page.PageViewModel;
import com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment;
import com.valentelmadafaka.gesmobapp.ui.home.page.SectionsPagerAdapter;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment.saberFecha;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    GesMobDB gesMobDB;
    ArrayList<Tarea> tareas = new ArrayList<>();
    TareaArray tareaArray;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        final TextView textView = root.findViewById(R.id.semana);
        final TextView fechas = root.findViewById(R.id.fechas);
        final TextView info = root.findViewById(R.id.info);
        GesMobDB gesMobDB;



        Date fechaDeHoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        for(Semana s : homeViewModel.getSemanas()){
            try {
                if(saberFecha(dateFormat.parse(s.getInicio()), dateFormat.parse(s.getFin()), fechaDeHoy)){
                    textView.setText("Semana "+s.getId());
                    fechas.setText(s.getInicio()+" - "+s.getFin());

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
                            if(saberFecha(dateFormat.parse(s.getInicio()), dateFormat.parse(s.getFin()), dateFormat.parse(t.getFecha())))
                                tareas.add(t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.moveToNext();
                    }

                    gesMobDB.close();


                    int horasEnTareas = 0;
                    for(Tarea t : tareas){
                        horasEnTareas += t.getHoras();
                    }

                    if(s.getHoras()> horasEnTareas){
                        info.setText("Aun no tienes suficientes horas en tareas");
                    }else if(s.getHoras() == horasEnTareas){
                        info.setText("Tienes exactamente las horas necesarias en tareas de la semana");
                    }else{
                        info.setText("Tienes horas en tareas de más");
                    }


                    tareaArray = new TareaArray(getActivity(), R.layout.tarea_view, tareas);
                    ListView listView = root.findViewById(R.id.tareas);
                    listView.setAdapter(tareaArray);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Tarea tarea = (Tarea)parent.getItemAtPosition(position);
                            Intent intent = new Intent(getActivity(), TareaDetail.class);
                            intent.putExtra("tarea", tarea);
                            startActivityForResult(intent, 10);

                        }
                    });

                    final Button crearTarea = root.findViewById(R.id.crearTareaButton);
                    crearTarea.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getActivity(), TareaForm.class), 11);
                        }
                    });
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }




        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 11) {
            if (data.hasExtra("tarea")) {
                Tarea t = (Tarea) data.getExtras().get("tarea");
                gesMobDB = new GesMobDB(getActivity());
                gesMobDB.open();
                if (gesMobDB.insertaTarea(t) == -1) {
                    Toast.makeText(getActivity(), "Error a l'afegir", Toast.LENGTH_SHORT).show();
                }
                gesMobDB.close();
                tareas.add(t);
                tareaArray.notifyDataSetChanged();

            }
        } else if (resultCode == RESULT_OK && requestCode == 10) {
            if (data.hasExtra("tarea")) {
                Tarea t = (Tarea) data.getExtras().get("tarea");
                for (Tarea tarea : tareas) {
                    if (tarea.getId().equals(t.getId())) {
                        tareas.remove(tarea);
                        tareas.add(t);
                    }
                }
                tareaArray.notifyDataSetChanged();
            }
        }
    }

}
