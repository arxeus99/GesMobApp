package com.valentelmadafaka.gesmobapp.ui.mensajeria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Chat;
import com.valentelmadafaka.gesmobapp.model.ChatArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class Chats extends AppCompatActivity {

    Usuario usuario;
    GesMobDB gesMobDB = new GesMobDB(this);
    ArrayList<Chat> chats = new ArrayList<>();
    ChatArray chatArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = PreferencesHelper.recuperarUsuari("User", this);
        for(Alumno a: getAlumnos()){
            Chat chat = new Chat();
            gesMobDB.open();
            Cursor c = gesMobDB.obtenerUsuario(Integer.parseInt(a.getId()));
            c.moveToFirst();
            Usuario alumno = new Usuario();
            alumno.setId(c.getString(0));
            alumno.setNombre(c.getString(1));
            alumno.setEmail(c.getString(2));
            alumno.setTipo(c.getString(3));
            chat.setUsuario(alumno);
            c = gesMobDB.obtenerUltimoMensaje(Integer.parseInt(alumno.getId()), Integer.parseInt(usuario.getId()));
            c.moveToFirst();
            if(c.getCount() == 0) {
                chat.setUltimoMensaje("");
                chat.setNotificacion(false);
            }else {
                chat.setUltimoMensaje(c.getString(0));
                if(c.getString(2).equals(usuario.getId()))
                    chat.setNotificacion(false);
                else {
                    if (c.getInt(1) == 0)
                        chat.setNotificacion(true);
                    else
                        chat.setNotificacion(false);
                }
            }
            chats.add(chat);
            gesMobDB.close();
        }

        ListView listView = findViewById(R.id.chats);
        chatArray = new ChatArray(this, R.layout.chat_view, chats);
        listView.setAdapter(chatArray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = (Chat)parent.getItemAtPosition(position);
                Intent intent = new Intent(Chats.this, Mensajeria.class);
                intent.putExtra("Usuario", chat.getUsuario());
                intent.putExtra("Chat", chat);
                startActivityForResult(intent, 11);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11 && resultCode == RESULT_OK){
            Chat chat = (Chat)data.getExtras().get("Chat");
            int chat2 = 0;
            for(Chat c:chats){
                if(c.getUsuario().getId().equals(chat.getUsuario().getId())){
                    chat2 = chats.indexOf(c);
                }
            }
            chats.set(chat2,chat);
            chatArray.notifyDataSetChanged();
        }

    }

    ArrayList<Alumno> getAlumnos(){
        ArrayList<Alumno> alumnos = new ArrayList<>();
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumnosDeProfeosor(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        while(!c.isAfterLast()){
            Alumno alumno = new Alumno();
            alumno.setId(c.getString(0));
            alumnos.add(alumno);
            c.moveToNext();
        }
        gesMobDB.close();
        return alumnos;
    }

    public void finish(){
        Intent data = new Intent();
        data.putExtra("Chats", chats);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
