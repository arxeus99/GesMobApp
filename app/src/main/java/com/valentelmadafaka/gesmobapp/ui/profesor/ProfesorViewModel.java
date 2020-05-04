package com.valentelmadafaka.gesmobapp.ui.profesor;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class ProfesorViewModel extends AndroidViewModel {

    GesMobDB db = new GesMobDB(getApplication().getApplicationContext());
    Usuario usuario = PreferencesHelper.recuperarUsuari("User", getApplication().getApplicationContext());

    public ProfesorViewModel(@NonNull Application application) {
        super(application);
    }

    public String getNombreProfesor(){
        db.open();
        Cursor c = db.obtenerAlumno(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        long idProfesor = c.getInt(3);
        c = db.obtenerUsuario(idProfesor);
        c.moveToFirst();
        db.close();
        return c.getString(1);
    }

    public String getCorreoProfesor(){
        db.open();
        Cursor c = db.obtenerAlumno(Integer.parseInt(usuario.getId()));
        c.moveToFirst();
        long idProfesor = c.getInt(3);
        c = db.obtenerUsuario(idProfesor);
        c.moveToFirst();
        db.close();
        return c.getString(2);
    }
}
