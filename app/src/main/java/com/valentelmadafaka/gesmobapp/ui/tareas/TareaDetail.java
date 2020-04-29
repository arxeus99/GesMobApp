package com.valentelmadafaka.gesmobapp.ui.tareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Observacion;
import com.valentelmadafaka.gesmobapp.model.ObservacionArray;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class TareaDetail extends AppCompatActivity {

    Tarea tarea;
    TextView titulo, descripcion, duracion;
    Button observacion;
    GesMobDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_detalle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tarea = (Tarea)getIntent().getSerializableExtra("tarea");
        titulo = findViewById(R.id.tituloTarea);
        descripcion = findViewById(R.id.descripcionTarea);
        duracion = findViewById(R.id.duracionTarea);
        observacion = findViewById(R.id.añadirObservacion);

        titulo.setText(tarea.getNombre());
        descripcion.setText(tarea.getDescripcion());
        duracion.setText(tarea.getHoras()+"h");
        db = new GesMobDB(this);


        ArrayList<Observacion> observaciones = new ArrayList<>();

        db.open();
        Cursor c = db.obetenerObservacionesDeTareas(Integer.parseInt(tarea.getId()));
        c.moveToFirst();
        while(!c.isAfterLast()){
            Observacion observacion = new Observacion();
            observacion.setId(c.getString(0));
            observacion.setAutorId(c.getString(1));
            observacion.setTareaId(c.getString(2));
            observacion.setContenido(c.getString(3));
            observaciones.add(observacion);
            c.moveToNext();
        }
        db.close();

        ObservacionArray observacionArray = new ObservacionArray(this, R.layout.observacion_view, observaciones);
        ListView listView = findViewById(R.id.observaciones);
        listView.setAdapter(observacionArray);

        observacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(TareaDetail.this);
                View promptView = layoutInflater.inflate(R.layout.prompt_observacion, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TareaDetail.this);

                alertDialogBuilder.setView(promptView);

                final EditText userInput = (EditText)promptView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Observacion observacion = new Observacion();
                                        db.open();
                                        observacion.setId((db.obetenerNumeroObservaciones()+1)+"");
                                        observacion.setAutorId("1 P");
                                        observacion.setTareaId(tarea.getId());
                                        observacion.setContenido(userInput.getText().toString());
                                        db.insertaObservacion(observacion);
                                        db.close();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
