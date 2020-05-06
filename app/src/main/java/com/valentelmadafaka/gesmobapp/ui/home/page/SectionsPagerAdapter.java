package com.valentelmadafaka.gesmobapp.ui.home.page;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.valentelmadafaka.gesmobapp.GesMobApp;
import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.util.ArrayList;
import java.util.Date;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private int[] TAB_TITLES = {1};
    private Context mContext;
    private static GesMobDB gesMobDB = new GesMobDB(GesMobApp.getAppContext());

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }

    public void getSemanas(){
        gesMobDB.open();
        Cursor c = gesMobDB.obtenerAlumno(Integer.parseInt(PreferencesHelper.recuperarUsuari("User", GesMobApp.getAppContext()).getId()));
        c.moveToFirst();
        int semanas = c.getInt(4);
        ArrayList<Integer> numeros = new ArrayList<>();
        for(int i = 1; i < semanas+1; i++){
            numeros.add(i);
        }
        int[] resultado = new int[numeros.size()];
        for(int i = 0; i<resultado.length; i++){
            resultado[i] = numeros.get(i).intValue();
        }
        this.TAB_TITLES = resultado;
        gesMobDB.close();
    }
}
