package com.valentelmadafaka.gesmobapp.ui.home.page;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import java.util.ArrayList;

public class PageViewModel extends AndroidViewModel {

        public PageViewModel(@NonNull Application application) {
            super(application);}

        private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
        private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return "Semana " + (input);
            }
        });

        public void setIndex(int index) {
            mIndex.setValue(index);
        }

        public LiveData<String> getText() {
            return mText;
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


}
