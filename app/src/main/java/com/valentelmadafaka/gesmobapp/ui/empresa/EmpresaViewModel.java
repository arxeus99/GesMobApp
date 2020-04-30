package com.valentelmadafaka.gesmobapp.ui.empresa;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class EmpresaViewModel extends AndroidViewModel {

    GesMobDB db = new GesMobDB(getApplication().getApplicationContext());
    Alumno alumno = PreferencesHelper.recuperarUsuari("User", getApplication().getApplicationContext());
    int empresa = Integer.parseInt(alumno.getIdEmpresa());

    public EmpresaViewModel(@NonNull Application application) {
        super(application);
    }

    public String getNombreEmpresa(){
        db.open();
        Cursor c = db.obtenerEmpresa(empresa);
        c.moveToFirst();
        db.close();
        return c.getString(1);
    }

    public String getCorreoEmpresa(){
        db.open();
        Cursor c = db.obtenerEmpresa(empresa);
        c.moveToFirst();
        db.close();
        return c.getString(2);
    }

    public String getTelefonoEmpresa(){
        db.open();
        Cursor c = db.obtenerEmpresa(empresa);
        c.moveToFirst();
        db.close();
        return c.getString(5);
    }

    public String getWebEmpresa(){
        db.open();
        Cursor c = db.obtenerEmpresa(empresa);
        c.moveToFirst();
        db.close();
        return c.getString(4);
    }

    public String getDireccionEmpresa(){
        db.open();
        Cursor c = db.obtenerEmpresa(empresa);
        c.moveToFirst();
        db.close();
        return c.getString(3);
    }
}
