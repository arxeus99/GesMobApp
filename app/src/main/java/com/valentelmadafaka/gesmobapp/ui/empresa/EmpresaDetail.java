package com.valentelmadafaka.gesmobapp.ui.empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

public class EmpresaDetail extends AppCompatActivity {

    Usuario alumno;
    TextView nombre, email, web, telefono, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_fragment);

        alumno = (Usuario)getIntent().getSerializableExtra("alumno");

        nombre = findViewById(R.id.nombreEmpresa);
        email = findViewById(R.id.emailEmpresa);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
        web = findViewById(R.id.webEmpresa);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW ,
                        Uri.parse("https://"+web.getText().toString()));
                startActivity(i);
            }
        });
        telefono = findViewById(R.id.telefonoEmpresa);
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_DIAL,
                        Uri.parse("tel:"+telefono.getText().toString()));
                startActivity(i);
            }
        });
        direccion = findViewById(R.id.direccionEmpresa);
        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+direccion.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        GesMobDB gesMobDB = new GesMobDB(this);
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(alumno.getId()));
        c.moveToFirst();
        int idEmpresa = c.getInt(1);
        c = gesMobDB.obtenerEmpresa(idEmpresa);
        c.moveToFirst();

        nombre.setText(c.getString(1));
        email.setText(c.getString(2));
        direccion.setText(c.getString(3));
        web.setText(c.getString(4));
        telefono.setText(c.getString(5));

        gesMobDB.close();


    }
}
