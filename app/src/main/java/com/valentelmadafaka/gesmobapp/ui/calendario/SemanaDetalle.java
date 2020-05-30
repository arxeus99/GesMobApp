package com.valentelmadafaka.gesmobapp.ui.calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment.saberFecha;

public class SemanaDetalle extends AppCompatActivity {

    Semana semana;
    Usuario alumno;
    TextView titulo, fechas, info;
    GesMobDB gesMobDB;
    ArrayList<Tarea> tareas = new ArrayList<>();
    TareaArray tareaArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semana_detalle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        semana = (Semana)getIntent().getSerializableExtra("semana");
        alumno = (Usuario)getIntent().getSerializableExtra("alumno");
        titulo = findViewById(R.id.semana);
        fechas = findViewById(R.id.fechas);
        info = findViewById(R.id.info);

        titulo.setText("Semana "+semana.getId());
        fechas.setText(semana.getInicio()+" - "+semana.getFin());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        gesMobDB = new GesMobDB(this);
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerTareas(Integer.parseInt(alumno.getId()));
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


        int horasEnTareas = 0;
        for(Tarea t : tareas){
            horasEnTareas += t.getHoras();
        }

        if(semana.getHoras()> horasEnTareas){
            info.setText("Aun no tienes suficientes horas en tareas");
        }else if(semana.getHoras() == horasEnTareas){
            info.setText("Tienes exactamente las horas necesarias en tareas de la semana");
        }else{
            info.setText("Tienes horas en tareas de más");
        }


        tareaArray = new TareaArray(this, R.layout.tarea_view, tareas);
        ListView listView = findViewById(R.id.tareas);
        listView.setAdapter(tareaArray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarea tarea = (Tarea)parent.getItemAtPosition(position);
                Intent intent = new Intent(SemanaDetalle.this, TareaDetail.class);
                intent.putExtra("tarea", tarea);
                startActivityForResult(intent, 10);

            }
        });

        final Button crearTarea = findViewById(R.id.crearTareaButton);
        crearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SemanaDetalle.this, TareaForm.class);
                intent.putExtra("destinatarios", false);
                intent.putExtra("fecha", semana.getInicio());
                startActivityForResult(intent, 11);
            }
        });

        gesMobDB.close();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 11) {
            if (data.hasExtra("tarea")) {
                Tarea t = (Tarea) data.getExtras().get("tarea");
                gesMobDB = new GesMobDB(this);
                gesMobDB.open();
                if (gesMobDB.insertaTarea(t) == -1) {
                    Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
