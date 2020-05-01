package com.valentelmadafaka.gesmobapp.ui.ajustes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class AjustesViewModel extends AndroidViewModel {

    public AjustesViewModel(@NonNull Application application) {
        super(application);
    }

    Alumno alumno = PreferencesHelper.recuperarUsuari("User", getApplication().getApplicationContext());

    public String getUserName(){
        return alumno.getNombre();
    }
}
