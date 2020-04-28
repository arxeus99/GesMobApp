package com.valentelmadafaka.gesmobapp.ui.empresa;

import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;


public class EmpresaFragment extends Fragment {

    private EmpresaViewModel mViewModel;

    public static EmpresaFragment newInstance() {
        return new EmpresaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.empresa_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        TextView nombre = root.findViewById(R.id.nombreEmpresa);
        TextView email = root.findViewById(R.id.correoEmpresa);
        TextView web = root.findViewById(R.id.webEmpresa);
        TextView telefono = root.findViewById(R.id.telefonoEmpresa);
        TextView direccion = root.findViewById(R.id.direccionEmpresa);
        nombre.setText(mViewModel.getNombreEmpresa());
        email.setText(mViewModel.getCorreoEmpresa());
        web.setText(mViewModel.getWebEmpresa());
        telefono.setText(mViewModel.getTelefonoEmpresa());
        direccion.setText(mViewModel.getDireccionEmpresa());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        // TODO: Use the ViewModel
    }
}
