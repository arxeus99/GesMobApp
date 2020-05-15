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
import com.valentelmadafaka.gesmobapp.model.AlumnoArray;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.alumno.PerfilAlumno;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment.saberFecha;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    GesMobDB gesMobDB;
    ArrayList<Tarea> tareas = new ArrayList<>();
    TareaArray tareaArray;
    AlumnoArray alumnoArray;
    Usuario usuario;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usuario = PreferencesHelper.recuperarUsuari("User", getActivity());
        if(usuario != null){
            if(usuario.getTipo().equals("alumno")){
                root = inflater.inflate(R.layout.fragment_home_alumno, container, false);
                homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

                final TextView textView = root.findViewById(R.id.semana);
                final TextView fechas = root.findViewById(R.id.fechas);
                final TextView info = root.findViewById(R.id.info);
                GesMobDB gesMobDB;

                boolean fueraDeFechas = true;



                Date fechaDeHoy = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


                for(Semana s : homeViewModel.getSemanas()){
                    try {
                        if(saberFecha(dateFormat.parse(s.getInicio()), dateFormat.parse(s.getFin()), fechaDeHoy)){
                            fueraDeFechas = false;
                            textView.setText("Semana "+s.getId());
                            fechas.setText(s.getInicio()+" - "+s.getFin());

                            gesMobDB = new GesMobDB(getActivity());
                            gesMobDB.open();
                            Cursor c = gesMobDB.obtenerTareas(Integer.parseInt(usuario.getId()));
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
                                    Intent intent = new Intent(getActivity(), TareaForm.class);
                                    intent.putExtra("destinatarios", false);
                                    startActivityForResult(intent, 11);
                                }
                            });
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(fueraDeFechas){
                    root = inflater.inflate(R.layout.fragment_home_fuera_de_fechas, container, false);
                }
            }else{
                root = inflater.inflate(R.layout.fragment_home_profesor, container, false);

                final ArrayList<Usuario> alumnos = new ArrayList<>();
                gesMobDB = new GesMobDB(getActivity());
                gesMobDB.open();
                Cursor c = gesMobDB.obtenerAlumnosDeProfeosor(Integer.parseInt(usuario.getId()));
                c.moveToFirst();
                while(!c.isAfterLast()){
                    Cursor cursor = gesMobDB.obtenerUsuario(c.getInt(0));
                    cursor.moveToFirst();
                    Usuario u = new Usuario();
                    u.setId(cursor.getString(0));
                    u.setNombre(cursor.getString(1));
                    u.setEmail(cursor.getString(2));
                    u.setTipo(cursor.getString(3));
                    alumnos.add(u);
                    c.moveToNext();
                }

                gesMobDB.close();

                ListView listView = root.findViewById(R.id.alumnos);
                alumnoArray = new AlumnoArray(getActivity(), R.layout.alumno_view, alumnos);
                listView.setAdapter(alumnoArray);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Usuario alumno = (Usuario)parent.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), PerfilAlumno.class);
                        intent.putExtra("alumno", alumno);
                        startActivity(intent);
                    }
                });

                final Button crearTarea = root.findViewById(R.id.crearTareaButton);
                crearTarea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TareaForm.class);
                        intent.putExtra("destinatarios", true);
                        startActivityForResult(intent, 11);
                    }
                });

            }






        }

        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if(resultCode == RESULT_OK) {
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
            }else if(resultCode == 11){
                ArrayList<Tarea> tareasRecibidas = (ArrayList<Tarea>)data.getExtras().get("tareas");
                gesMobDB = new GesMobDB(getActivity());
                gesMobDB.open();
                for(Tarea t : tareasRecibidas){
                    if(gesMobDB.insertaTarea(t) == -1){
                        Toast.makeText(getActivity(), "Error a l'afegir", Toast.LENGTH_SHORT).show();
                    }
                }
                gesMobDB.close();

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
