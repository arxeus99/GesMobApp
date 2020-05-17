package com.valentelmadafaka.gesmobapp.ui.alumno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.SemanaArray;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.calendario.SemanaDetalle;
import com.valentelmadafaka.gesmobapp.ui.empresa.EmpresaDetail;
import com.valentelmadafaka.gesmobapp.ui.mensajeria.Mensajeria;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class PerfilAlumno extends AppCompatActivity {

    ArrayList<Semana> semanas = new ArrayList<>();
    Usuario alumno;
    TextView correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alumno = (Usuario)getIntent().getSerializableExtra("alumno");
        correo = findViewById(R.id.correoAlumno);
        TextView nombre = findViewById(R.id.nombreAlumno);
        TextView correo = findViewById(R.id.correoAlumno);
        TextView empresa = findViewById(R.id.empresaAlumno);
        nombre.setText(alumno.getNombre());
        correo.setText(alumno.getEmail());
        GesMobDB gesMobDB = new GesMobDB(this);
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(alumno.getId()));
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
                i.putExtra("alumno", alumno);
                startActivity(i);
            }
        });
        c = gesMobDB.obtenerAlumno(Integer.parseInt(alumno.getId()));
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

    public void tarea(View view) {
        Intent intent = new Intent(this, TareaForm.class);
        intent.putExtra("destinatarios", false);
        intent.putExtra("alumno", alumno);
        startActivityForResult(intent, 11);
    }

    public void mensaje(View view) {
        Intent intent = new Intent(this, Mensajeria.class);
        intent.putExtra("Usuario", alumno);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 11){
            Tarea t = (Tarea) data.getExtras().get("tarea");
            GesMobDB gesMobDB = new GesMobDB(this);
            gesMobDB.open();
            if (gesMobDB.insertaTarea(t) == -1) {
                Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();
            }
            gesMobDB.close();
        }
    }

    public void correo(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", correo.getText().toString(), null));
        startActivity(Intent.createChooser(intent, "Send email..."));

    }

    public void empresa(View view) {
        Intent intent = new Intent(this, EmpresaDetail.class);
        intent.putExtra("alumno", alumno);
        startActivity(intent);
    }
}
