package com.valentelmadafaka.gesmobapp.ui.calendario;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class CalendarioViewModel extends AndroidViewModel {

    public CalendarioViewModel(Application application){
        super(application);
    }


    public ArrayList<Semana> getSemanas(){
        ArrayList<Semana> semanas = new ArrayList<>();
        GesMobDB gesMobDB = new GesMobDB(getApplication().getApplicationContext());
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerSemanas();
        c.moveToFirst();
        while(!c.isAfterLast()){
            Semana semana = new Semana();
            semana.setId(c.getString(0));
            semana.setInicio(c.getString(1));
            semana.setFin(c.getString(2));
            semana.setHoras(c.getInt(3));
            c.moveToNext();
            semanas.add(semana);
        }

        return semanas;
    }
    // TODO: Implement the ViewModel
}
