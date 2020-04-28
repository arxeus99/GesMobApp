package com.valentelmadafaka.gesmobapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Empresa;
import com.valentelmadafaka.gesmobapp.model.Profesor;
import com.valentelmadafaka.gesmobapp.ui.Mensajeria;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.json.JSONHelper;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int correo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
//            Alumno alumno = JSONHelper.obtenerAlumno("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"nombre\": \"Dickie\",\n" +
//                    "  \"email\": \"dbartosinski0@mac.com\",\n" +
//                    "  \"direccion\": \"12025 Stoughton Hill\",\n" +
//                    "  \"idProfesor\": 1,\n" +
//                    "  \"idEmpresa\": 1\n" +
//                    "}");
//            Empresa empresa = JSONHelper.obtenerEmpresa("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"nombre\": \"Shields, Wisozk and Doyle\",\n" +
//                    "  \"email\": \"ggott0@washington.edu\",\n" +
//                    "  \"direccion\": \"06981 Michigan Park\",\n" +
//                    "  \"web\": \"Kurdish.com\",\n" +
//                    "  \"telefono\": \"686-901-2503\"\n" +
//                    "}");
//            Profesor profesor = JSONHelper.obtenerProfesor("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"nombre\": \"Tailor\",\n" +
//                    "  \"email\": \"tjeune0@rediff.com\"\n" +
//                    "}");
//
//            GesMobDB db = new GesMobDB(this);
//            db.open();
//            if(db.insertaEmpresa(empresa) == -1){
//                Toast.makeText(this, "Error a l’afegir empresa",
//                        Toast.LENGTH_SHORT).show();
//            }
//            if(db.insertaAlumno(alumno) == -1 ){
//                Toast.makeText(this, "Error a l’afegir alumne",
//                        Toast.LENGTH_SHORT).show();
//            }
//            if(db.insertaProfesor(profesor) == -1){
//                Toast.makeText(this, "Error a l’afegir professor",
//                        Toast.LENGTH_SHORT).show();
//            }
//            db.close();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = findViewById(R.id.nav_mensajeria);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                if(correo == 0){
//                    correo = 1;
//                    fab.setImageResource(R.drawable.correonotificacion);
//                }else if(correo == 1){
//                    correo = 0;
//                    fab.setImageResource(R.drawable.correo);
//                }

                startActivity(new Intent(MainActivity.this, Mensajeria.class));


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profesor, R.id.nav_empresa, R.id.nav_ajustes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
