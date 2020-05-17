package com.valentelmadafaka.gesmobapp.ui.empresa;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;
import com.valentelmadafaka.gesmobapp.utils.shared_preferences.PreferencesHelper;


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
        final TextView email = root.findViewById(R.id.emailEmpresa);
        final TextView web = root.findViewById(R.id.webEmpresa);
        final TextView telefono = root.findViewById(R.id.telefonoEmpresa);
        final TextView direccion = root.findViewById(R.id.direccionEmpresa);
        nombre.setText(mViewModel.getNombreEmpresa());
        email.setText(mViewModel.getCorreoEmpresa());
        web.setText(mViewModel.getWebEmpresa());
        telefono.setText(mViewModel.getTelefonoEmpresa());
        direccion.setText(mViewModel.getDireccionEmpresa());

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW ,
                        Uri.parse("https://"+web.getText().toString()));
                startActivity(i);
            }
        });

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_DIAL,
                        Uri.parse("tel:"+telefono.getText().toString()));
                startActivity(i);
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+ mViewModel.getDireccionAlumno() +"&daddr="+direccion.getText().toString()+"&dirflg=r");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        // TODO: Use the ViewModel
    }
}
