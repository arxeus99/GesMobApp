package com.valentelmadafaka.gesmobapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.TareaArray;
import com.valentelmadafaka.gesmobapp.model.Usuario;
import com.valentelmadafaka.gesmobapp.ui.home.page.PlaceholderFragment;
import com.valentelmadafaka.gesmobapp.ui.home.page.SectionsPagerAdapter;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaDetail;
import com.valentelmadafaka.gesmobapp.ui.tareas.TareaForm;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    SectionsPagerAdapter sectionsPagerAdapter;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Usuario usuario = PreferencesHelper.recuperarUsuari("User", getActivity());



        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        Date fechaDeHoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");



        if(usuario != null){
            sectionsPagerAdapter.getSemanas();
            sectionsPagerAdapter.notifyDataSetChanged();
        }


        for(Semana s : obtenerSemanas()){
            try {
                if(PlaceholderFragment.saberFecha(dateFormat.parse(s.getInicio()), dateFormat.parse(s.getFin()), fechaDeHoy)){
                    viewPager.setCurrentItem(Integer.parseInt(s.getId())-1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    private ArrayList<Semana> obtenerSemanas(){
        ArrayList<Semana> semanas = new ArrayList<>();
        GesMobDB gesMobDB = new GesMobDB(getActivity());
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
