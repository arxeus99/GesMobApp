package com.valentelmadafaka.gesmobapp.ui.profesor;

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

public class ProfesorFragment extends Fragment {

    private ProfesorViewModel mViewModel;

    public static ProfesorFragment newInstance() {
        return new ProfesorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profesor, container, false);
        mViewModel = ViewModelProviders.of(this).get(ProfesorViewModel.class);
        TextView nombre = root.findViewById(R.id.nombreProfesor);
        final TextView email = root.findViewById(R.id.correoProfesor);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
        nombre.setText(mViewModel.getNombreProfesor());
        email.setText(mViewModel.getCorreoProfesor());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfesorViewModel.class);
        // TODO: Use the ViewModel
    }

}
