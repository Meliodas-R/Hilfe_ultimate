package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
