package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Autenticacion extends AppCompatActivity {

    EditText etCampoPass;
    String passString;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);
        getSupportActionBar().hide();
        SharedPreferences preferenciasAutenticacion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        passString = preferenciasAutenticacion.getString("Password", " ");
        etCampoPass = (EditText) findViewById(R.id.etPass);
        boton = (Button) findViewById(R.id.btnAcceder);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCampoPass.getText().toString().equals(passString)){
                    MainActivity.autenticacion=false;
                    Intent i=new Intent(Autenticacion.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(Autenticacion.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void salir(View view){
        finish();
        System.exit(0);
    }

}
