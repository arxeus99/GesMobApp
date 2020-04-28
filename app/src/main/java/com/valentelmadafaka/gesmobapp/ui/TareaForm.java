package com.valentelmadafaka.gesmobapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TareaForm extends AppCompatActivity {

    EditText titulo, descripcion, horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titulo = findViewById(R.id.tituloTarea);
        descripcion = findViewById(R.id.descripcionTarea);
        horas = findViewById(R.id.duracionTarea);
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
            GesMobDB gesMobDB = new GesMobDB(this);
            gesMobDB.open();
            Tarea t = new Tarea();
            t.setId((gesMobDB.obtenerNumeroTareas()+1)+"");
            t.setNombre(titulo.getText().toString());
            t.setDescripcion(descripcion.getText().toString());
            t.setHoras(Integer.parseInt(horas.getText().toString()));
            t.setIdProfesor("1");
            t.setIdAlumno("1");
            t.setRealizada(false);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = simpleDateFormat.format(new Date());
            t.setFecha(currentDate);
            if(gesMobDB.insertaTarea(t) == -1){
                Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();
            }
            gesMobDB.close();
            finish();
        }
    }
}
