package com.valentelmadafaka.gesmobapp.ui.ajustes;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valentelmadafaka.gesmobapp.R;

public class AjustesFragment extends Fragment {

    private AjustesViewModel mViewModel;

    public static AjustesFragment newInstance() {
        return new AjustesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ajustes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AjustesViewModel.class);
        // TODO: Use the ViewModel
    }

}
