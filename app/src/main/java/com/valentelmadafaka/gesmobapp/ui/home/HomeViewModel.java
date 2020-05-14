package com.valentelmadafaka.gesmobapp.ui.home;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<Semana> getSemanas(){
        ArrayList<Semana> semanas = new ArrayList<>();
        GesMobDB gesMobDB = new GesMobDB(getApplication().getApplicationContext());
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(PreferencesHelper.recuperarUsuari("User", getApplication().getApplicationContext()).getId()));
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
        gesMobDB.close();
        return semanas;
    }

}