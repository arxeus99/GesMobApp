package com.valentelmadafaka.gesmobapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Chat;
import com.valentelmadafaka.gesmobapp.model.Empresa;
import com.valentelmadafaka.gesmobapp.model.Mensaje;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.mensajeria.Chats;
import com.valentelmadafaka.gesmobapp.ui.mensajeria.Mensajeria;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.json.JSONHelper;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
//            Mensaje mensaje = JSONHelper.obtenerMensaje("{\"id\": 1, \"emisor\":2, \"receptor\":1, \"contenido\": \"Hola buenas probando\", \"leido\":1}");
//            Mensaje mensaje2 = JSONHelper.obtenerMensaje("{\"id\": 2, \"emisor\":1, \"receptor\":2, \"contenido\": \"Hola buenas probando\", \"leido\":1}");
//            Mensaje mensaje3 = JSONHelper.obtenerMensaje("{\"id\": 3, \"emisor\":2, \"receptor\":1, \"contenido\": \"Hola buenas probando\", \"leido\":1}");
//            Mensaje mensaje4 = JSONHelper.obtenerMensaje("{\"id\": 4, \"emisor\":1, \"receptor\":2, \"contenido\": \"Hola buenas probando\", \"leido\":0}");
//            Usuario usuario = JSONHelper.obtenerUsuario("{\"id\":1,\"nombre\":\"Yoshi\",\"email\":\"ydensham0@artisteer.com\",\"tipo\":\"alumno\"}");
//            Usuario usuario2 = JSONHelper.obtenerUsuario("{\"id\":3,\"nombre\":\"Valentin\",\"email\":\"arxeus99@gmail.com\",\"tipo\":\"alumno\"}");
//            Alumno alumno = JSONHelper.obtenerAlumno("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"direccion\": \"12025 Stoughton Hill\",\n" +
//                    "  \"idProfesor\": 2,\n" +
//                    "  \"idEmpresa\": 1,\n" +
//                    "  \"semanas\": \"1,2,3,4,5\"\n" +
//                    "}");
//            Alumno alumno2 = JSONHelper.obtenerAlumno("{\n" +
//                    "  \"id\": 3,\n" +
//                    "  \"direccion\": \"c/Ramon Desbrull, 50, Inca\",\n" +
//                    "  \"idProfesor\": 2,\n" +
//                    "  \"idEmpresa\": 1,\n" +
//                    "  \"semanas\": \"3,4,5,6\"\n" +
//                    "}");
//
//            Empresa empresa = JSONHelper.obtenerEmpresa("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"nombre\": \"Shields, Wisozk and Doyle\",\n" +
//                    "  \"email\": \"ggott0@washington.edu\",\n" +
//                    "  \"direccion\": \"06981 Michigan Park\",\n" +
//                    "  \"web\": \"Kurdish.com\",\n" +
//                    "  \"telefono\": \"686-901-2503\"\n" +
//                    "}");
//            Usuario profesor = JSONHelper.obtenerUsuario("{\"id\":2,\"nombre\":\"Benedict\",\"email\":\"bspeek1@odnoklassniki.ru\",\"tipo\":\"profesor\"}");
//
//            Tarea t1 = JSONHelper.obtenerTarea("{\n" +
//                    "  \"id\": 1,\n" +
//                    "  \"nombre\": \"Holdlamis\",\n" +
//                    "  \"descripcion\": \"Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum.\",\n" +
//                    "  \"fecha\": \"09/05/2020\",\n" +
//                    "  \"horas\": 53,\n" +
//                    "  \"realizada\": 0,\n" +
//                    "  \"idAlumno\": 1,\n" +
//                    "  \"idProfesor\": 2\n" +
//                    "}");
//            Tarea t2 = JSONHelper.obtenerTarea("{\n" +
//                    "  \"id\": 2,\n" +
//                    "  \"nombre\": \"Opela\",\n" +
//                    "  \"descripcion\": \"Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo.\",\n" +
//                    "  \"fecha\": \"22/05/2020\",\n" +
//                    "  \"horas\": 27,\n" +
//                    "  \"realizada\": 1,\n" +
//                    "  \"idAlumno\": 1,\n" +
//                    "  \"idProfesor\": 2\n" +
//                    "}");
//            Tarea t3 = JSONHelper.obtenerTarea("{\n" +
//                    "  \"id\": 3,\n" +
//                    "  \"nombre\": \"Sub-Ex\",\n" +
//                    "  \"descripcion\": \"Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl.\",\n" +
//                    "  \"fecha\": \"15/05/2020\",\n" +
//                    "  \"horas\": 65,\n" +
//                    "  \"realizada\": 1,\n" +
//                    "  \"idAlumno\": 1,\n" +
//                    "  \"idProfesor\": 2\n" +
//                    "}");
//            Tarea t4 = JSONHelper.obtenerTarea("{\n" +
//                    "  \"id\": 4,\n" +
//                    "  \"nombre\": \"Vagram\",\n" +
//                    "  \"descripcion\": \"Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.\",\n" +
//                    "  \"fecha\": \"29/05/2020\",\n" +
//                    "  \"horas\": 78,\n" +
//                    "  \"realizada\": 0,\n" +
//                    "  \"idAlumno\": 1,\n" +
//                    "  \"idProfesor\": 2\n" +
//                    "}");
//
//            Semana s1 = JSONHelper.obtenerSemana("{\"id\":1,\"inicio\":\"04/05/2020\",\"fin\":\"10/05/2020\",\"horas\":17}");
//            Semana s2 = JSONHelper.obtenerSemana("{\"id\":2,\"inicio\":\"11/05/2020\",\"fin\":\"17/05/2020\",\"horas\":22}");
//            Semana s3 = JSONHelper.obtenerSemana("{\"id\":3,\"inicio\":\"18/05/2020\",\"fin\":\"24/05/2020\",\"horas\":15}");
//            Semana s4 = JSONHelper.obtenerSemana("{\"id\":4,\"inicio\":\"25/05/2020\",\"fin\":\"31/05/2020\",\"horas\":21}");
//            Semana s5 = JSONHelper.obtenerSemana("{\"id\":5,\"inicio\":\"01/06/2020\",\"fin\":\"07/06/2020\",\"horas\":23}");
//            Semana s6 = JSONHelper.obtenerSemana("{\"id\":6,\"inicio\":\"08/06/2020\",\"fin\":\"14/06/2020\",\"horas\":35}");
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
//            }if(db.insertaAlumno(alumno2) == -1){
//                Toast.makeText(this, "Error a l’afegir alumne 2",
//                        Toast.LENGTH_SHORT).show();
//            }
//            if(db.insertaUsuario(profesor) == -1){
//                Toast.makeText(this, "Error a l’afegir professor",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaUsuario(usuario) == -1){
//                Toast.makeText(this, "Error a l’afegir usuari",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaUsuario(usuario2) == -1){
//                Toast.makeText(this, "Error a l’afegir usuari 2",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaTarea(t1) == -1){
//                Toast.makeText(this, "Error a l’afegir t1",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaTarea(t2) == -1){
//                Toast.makeText(this, "Error a l’afegir t2",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaTarea(t3) == -1){
//                Toast.makeText(this, "Error a l’afegir t3",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaTarea(t4) == -1){
//                Toast.makeText(this, "Error a l’afegir t4",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaMensaje(mensaje) == -1){
//                Toast.makeText(this, "Error al añadir mensaje 1",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaMensaje(mensaje2) == -1){
//                Toast.makeText(this, "Error al añadir mensaje 2",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaMensaje(mensaje3) == -1){
//                Toast.makeText(this, "Error al añadir mensaje 3",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaMensaje(mensaje4) == -1){
//                Toast.makeText(this, "Error al añadir mensaje 4",
//                        Toast.LENGTH_SHORT).show();
//            }
//            if(db.insertaSemana(s1) == -1){
//                Toast.makeText(this, "Error al añadir setmana 1",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaSemana(s2) == -1){
//                Toast.makeText(this, "Error al añadir setmana 2",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaSemana(s3) == -1){
//                Toast.makeText(this, "Error al añadir setmana 3",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaSemana(s4) == -1){
//                Toast.makeText(this, "Error al añadir setmana 4",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaSemana(s5) == -1){
//                Toast.makeText(this, "Error al añadir setmana 5",
//                        Toast.LENGTH_SHORT).show();
//            }if(db.insertaSemana(s6) == -1){
//                Toast.makeText(this, "Error al añadir setmana 5",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            db.close();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        super.onCreate(savedInstanceState);
        if(PreferencesHelper.recuperarUsuari("User", this) == null){
            startActivityForResult(new Intent(this, LoginActivity.class), 11);
        }else{
            iniciar();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.contenedor);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11)
            iniciar();
        if(requestCode == 10){
            fab.setImageResource(R.drawable.correo);
            ArrayList<Chat> chats = (ArrayList<Chat>)data.getExtras().get("Chats");
            for(Chat c:chats){
                if(c.isNotificacion())
                    fab.setImageResource(R.drawable.correonotificacion);
            }
        }
    }

    void iniciar(){
        final Usuario usuario = PreferencesHelper.recuperarUsuari("User", this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.nav_mensajeria);
        if(mensajesSinLeer(usuario)){
            fab.setImageResource(R.drawable.correonotificacion);
        }else{
            fab.setImageResource(R.drawable.correo);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(usuario.getTipo().equals("alumno")) {
                    Intent intent = new Intent(MainActivity.this, Mensajeria.class);
                    Usuario profesor = new Usuario();
                    GesMobDB gesMobDB = new GesMobDB(MainActivity.this);
                    gesMobDB.open();
                    Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(usuario.getId()));
                    c.moveToFirst();
                    int idProfesor = c.getInt(3);
                    c = gesMobDB.obtenerUsuario(idProfesor);
                    c.moveToFirst();
                    profesor.setId(c.getString(0));
                    profesor.setNombre(c.getString(1));
                    profesor.setEmail(c.getString(2));
                    profesor.setTipo(c.getString(3));
                    intent.putExtra("Usuario", profesor);
                    gesMobDB.close();
                    startActivityForResult(intent, 10);
                }else
                    startActivityForResult(new Intent(MainActivity.this, Chats.class), 10);


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(usuario.getTipo().equals("profesor")) {
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_calendario).setVisible(false);
            nav_menu.findItem(R.id.nav_empresa).setVisible(false);
            nav_menu.findItem(R.id.nav_profesor).setVisible(false);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profesor, R.id.nav_empresa, R.id.nav_ajustes, R.id.nav_calendario)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.contenedor);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    private boolean mensajesSinLeer(Usuario usuario){
        GesMobDB gesMobDB = new GesMobDB(this);
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerMensajesRecibidos(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getInt(4) == 0){
                gesMobDB.close();
                return true;
            }
            c.moveToNext();
        }
        gesMobDB.close();
        return false;
    }

}
