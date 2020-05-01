package com.valentelmadafaka.gesmobapp.ui.ajustes;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.LoginActivity;
import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;

public class AjustesFragment extends Fragment {

    private AjustesViewModel mViewModel;

    public static AjustesFragment newInstance() {
        return new AjustesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ajustes_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(AjustesViewModel.class);
        Button borrar = root.findViewById(R.id.borrarUser);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesHelper.borrarUsuario(getActivity());
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        TextView nombre = root.findViewById(R.id.nombreUsuario);
        nombre.setText("Hola "+mViewModel.getUserName());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AjustesViewModel.class);
        // TODO: Use the ViewModel
    }

}
