package com.valentelmadafaka.gesmobapp.ui.alumno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.SemanaArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.calendario.SemanaDetalle;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class PerfilAlumno extends AppCompatActivity {

    ArrayList<Semana> semanas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Usuario usuario = (Usuario)getIntent().getSerializableExtra("alumno");
        TextView nombre = findViewById(R.id.nombreAlumno);
        TextView correo = findViewById(R.id.correoAlumno);
        TextView empresa = findViewById(R.id.empresaAlumno);
        nombre.setText(usuario.getNombre());
        correo.setText(usuario.getEmail());
        GesMobDB gesMobDB = new GesMobDB(this);
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        String info = c.getString(4);
        String[] semanasInfo = info.split(",");
        for(String s : semanasInfo){
            c = gesMobDB.obtenerSemana(Integer.parseInt(s));
            c.moveToFirst();
            Semana semana = new Semana();
            semana.setId(c.getString(0));
            semana.setInicio(c.getString(1));
            semana.setFin(c.getString(2));
            semana.setHoras(c.getInt(3));
            semanas.add(semana);
        }
        SemanaArray semanaArray = new SemanaArray(this, R.layout.semana_view, semanas);
        ListView listView = findViewById(R.id.semanas);
        listView.setAdapter(semanaArray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Semana semana = (Semana)parent.getItemAtPosition(position);
                Intent i = new Intent(PerfilAlumno.this, SemanaDetalle.class);
                i.putExtra("semana", semana);
                startActivity(i);
            }
        });
        c = gesMobDB.obtenerAlumno(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        int idEmpresa = c.getInt(1);
        c = gesMobDB.obtenerEmpresa(idEmpresa);
        c.moveToFirst();
        empresa.setText(c.getString(1));

        gesMobDB.close();
    }


   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
