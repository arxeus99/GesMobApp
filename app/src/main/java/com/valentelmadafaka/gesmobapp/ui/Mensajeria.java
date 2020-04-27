package com.valentelmadafaka.gesmobapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.valentelmadafaka.gesmobapp.R;

public class Mensajeria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
