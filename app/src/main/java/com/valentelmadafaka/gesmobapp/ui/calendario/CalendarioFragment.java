package com.valentelmadafaka.gesmobapp.ui.calendario;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.SemanaArray;

public class CalendarioFragment extends Fragment {

    private CalendarioViewModel mViewModel;
    View root;

    public static CalendarioFragment newInstance() {
        return new CalendarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.calendario_fragment, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CalendarioViewModel.class);


        SemanaArray semanaArray = new SemanaArray(getActivity(), R.layout.semana_view, mViewModel.getSemanas());
        ListView listView = root.findViewById(R.id.semanas);
        listView.setAdapter(semanaArray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Semana semana = (Semana)parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), SemanaDetalle.class);
                i.putExtra("semana", semana);
                startActivity(i);
            }
        });
        // TODO: Use the ViewModel
    }

}
