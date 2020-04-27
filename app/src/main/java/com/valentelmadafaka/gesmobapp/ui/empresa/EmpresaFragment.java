package com.valentelmadafaka.gesmobapp.ui.empresa;

import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valentelmadafaka.gesmobapp.R;

public class EmpresaFragment extends Fragment {

    private EmpresaViewModel mViewModel;

    public static EmpresaFragment newInstance() {
        return new EmpresaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empresa_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        // TODO: Use the ViewModel
    }

}
