package com.valentelmadafaka.gesmobapp.ui.tareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class TareaDetail extends AppCompatActivity {

    Tarea tarea;
    TextView titulo, descripcion, duracion;
    Button observacion;
    GesMobDB db;
    ListView listView;
    Boolean cambiada = false;

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

        if(tarea.isRealizada()){
            Button finalizar = findViewById(R.id.finalizada);
            finalizar.setVisibility(View.INVISIBLE);
        }

        final ArrayList<Observacion> observaciones = new ArrayList<>();

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

        final ObservacionArray observacionArray = new ObservacionArray(this, R.layout.observacion_view, observaciones);
        listView = findViewById(R.id.observaciones);
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
                                        if(!userInput.getText().toString().isEmpty()){
                                            Observacion observacion = new Observacion();
                                            db.open();
                                            observacion.setId((db.obetenerNumeroObservaciones()+1)+"");
                                            observacion.setAutorId(PreferencesHelper.recuperarUsuari("User", TareaDetail.this).getId());
                                            observacion.setTareaId(tarea.getId());
                                            observacion.setContenido(userInput.getText().toString());
                                            db.insertaObservacion(observacion);
                                            db.close();
                                            observaciones.add(observacion);
                                            observacionArray.notifyDataSetChanged();
                                        }

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

    public void finalizarVoid(View view){
        new android.app.AlertDialog.Builder(TareaDetail.this)
                .setTitle("Atención")
                .setMessage("¿Seguro que quiere finalizar esta tarea?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tarea.setRealizada(true);
                        db.open();
                        db.finalizarTarea(Integer.parseInt(tarea.getId()));
                        db.close();
                        cambiada = true;
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(android.R.string.no, null)
                .show();

    }


    public void finish(){

        if(cambiada){
            Intent data = new Intent();
            data.putExtra("tarea", tarea);
            setResult(RESULT_OK, data);
        }

        super.finish();
    }


}
