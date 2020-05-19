package com.valentelmadafaka.gesmobapp.ui.tareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TareaForm extends AppCompatActivity {

    EditText titulo, descripcion, horas;
    LinearLayout linearLayout;
    MultiAutoCompleteTextView destinatarios;
    Tarea t;
    Usuario usuario;
    GesMobDB gesMobDB;
    Usuario alumno;
    ArrayList<Tarea> tareas = new ArrayList<>();
    final ArrayList<String> nombresAlumnos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usuario = PreferencesHelper.recuperarUsuari("User", this);
        titulo = findViewById(R.id.tituloTarea);
        descripcion = findViewById(R.id.descripcionTarea);
        horas = findViewById(R.id.duracionTarea);
        linearLayout = findViewById(R.id.destinatarios);
        alumno = (Usuario)getIntent().getSerializableExtra("alumno");
        if(!getIntent().getExtras().getBoolean("destinatarios")){
            linearLayout.setVisibility(View.INVISIBLE);
        }else{
            destinatarios = findViewById(R.id.destinatariosText);
            gesMobDB = new GesMobDB(this);
            gesMobDB.open();
            Cursor c = gesMobDB.obtenerAlumnosDeProfeosor(Integer.parseInt(usuario.getId()));
            c.moveToFirst();
            while(!c.isAfterLast()){
                Cursor cursor = gesMobDB.obtenerUsuario(c.getInt(0));
                cursor.moveToFirst();
                nombresAlumnos.add(cursor.getString(1));
                c.moveToNext();
            }
            gesMobDB.close();
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresAlumnos);
            destinatarios.setAdapter(adapter);
            destinatarios.setThreshold(0);
            destinatarios.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            destinatarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.remove(nombresAlumnos.get(position));
                    nombresAlumnos.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void crearVoid(View view){
        if(titulo.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty() || horas.getText().toString().isEmpty()){
            Snackbar.make(view, "No puede dejar ningun espacio en blanco", Snackbar.LENGTH_LONG).show();
        }else{
            if(usuario.getTipo().equals("alumno")){
                gesMobDB = new GesMobDB(this);
                gesMobDB.open();
                t = new Tarea();
                t.setId((gesMobDB.obtenerNumeroTareas()+1)+"");
                t.setNombre(titulo.getText().toString());
                t.setDescripcion(descripcion.getText().toString());
                t.setHoras(Integer.parseInt(horas.getText().toString()));
                Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(usuario.getId()));
                c.moveToFirst();
                t.setIdProfesor(c.getString(3));
                t.setIdAlumno(usuario.getId());
                t.setRealizada(false);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = simpleDateFormat.format(new Date());
                t.setFecha(currentDate);
                gesMobDB.close();
            }else{
                gesMobDB = new GesMobDB(this);
                gesMobDB.open();
                if(getIntent().getExtras().getBoolean("destinatarios")){
                    if(destinatarios.getText().toString().isEmpty()){
                        Snackbar.make(view, "No puede dejar ningun espacio en blanco", Snackbar.LENGTH_LONG).show();
                    }else{
                        boolean encontrado = false;
                        String input = destinatarios.getText().toString().trim();
                        String[] personas = input.split("\\s*,\\s*");
                        for(int i=1; i<personas.length+1; i++){
                            for(String s : nombresAlumnos){
                                if(personas[i-1].equals(s)){
                                    encontrado = true;
                                }
                            }

                            if(encontrado){
                                Tarea tarea = new Tarea();
                                tarea.setId((gesMobDB.obtenerNumeroTareas()+i)+"");
                                tarea.setNombre(titulo.getText().toString());
                                tarea.setDescripcion(descripcion.getText().toString());
                                tarea.setHoras(Integer.parseInt(horas.getText().toString()));
                                tarea.setIdProfesor(usuario.getId());
                                Cursor c = gesMobDB.obtenerUsuario(personas[i-1]);
                                c.moveToFirst();
                                tarea.setIdAlumno(c.getString(0));
                                tarea.setRealizada(false);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String currentDate = simpleDateFormat.format(new Date());
                                tarea.setFecha(currentDate);
                                tareas.add(tarea);
                            }else{
                                Snackbar.make(view, "Alumno no encontrado", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }else{
                    t = new Tarea();
                    t.setId((gesMobDB.obtenerNumeroTareas()+1)+"");
                    t.setNombre(titulo.getText().toString());
                    t.setDescripcion(descripcion.getText().toString());
                    t.setHoras(Integer.parseInt(horas.getText().toString()));
                    t.setIdProfesor(usuario.getId());
                    t.setIdAlumno(alumno.getId());
                    t.setRealizada(false);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String currentDate = simpleDateFormat.format(new Date());
                    t.setFecha(currentDate);
                }
                gesMobDB.close();
            }

            finish();
        }
    }

    public void finish(){
        if(t != null){
            Intent data = new Intent();
            data.putExtra("tarea", t);
            setResult(RESULT_OK, data);
        }
        if(getIntent().getExtras().getBoolean("destinatarios")){
            Intent data = new Intent();
            data.putExtra("tareas", tareas);
            setResult(11, data);
        }
        super.finish();
    }
}
