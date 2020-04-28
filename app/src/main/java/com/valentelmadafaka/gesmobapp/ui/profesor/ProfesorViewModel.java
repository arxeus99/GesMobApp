package com.valentelmadafaka.gesmobapp.ui.profesor;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

public class ProfesorViewModel extends AndroidViewModel {

    GesMobDB db = new GesMobDB(getApplication().getApplicationContext());

    public ProfesorViewModel(@NonNull Application application) {
        super(application);
    }

    public String getNombreProfesor(){
        db.open();
        Cursor c = db.obtenerProfesor(1);
        c.moveToFirst();
        db.close();
        return c.getString(1);
    }

    public String getCorreoProfesor(){
        db.open();
        Cursor c = db.obtenerProfesor(1);
        c.moveToFirst();
        db.close();
        return c.getString(2);
    }
}
