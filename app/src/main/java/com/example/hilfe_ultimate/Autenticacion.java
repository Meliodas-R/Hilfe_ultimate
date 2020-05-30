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

import java.math.BigInteger;

public class Autenticacion extends AppCompatActivity {

    EditText etCampoPass;
    String passString;
    Button boton;
    private final String SHA = "SHA-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);
        getSupportActionBar().hide();
        SharedPreferences preferenciasAutenticacion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        passString = preferenciasAutenticacion.getString("Password", " ");
        etCampoPass = (EditText) findViewById(R.id.etPass);
        boton = (Button) findViewById(R.id.btnAcceder);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String hash = preferences.getString("Password", "");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCampoPass.getText().toString().isEmpty()) {
                    Toast.makeText(Autenticacion.this, "Se deben rellenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] inputData = etCampoPass.getText().toString().getBytes();
                    byte[] outputData = new byte[0];
                    try {
                        outputData = sha.encryptSHA(inputData, SHA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    BigInteger shaData = new BigInteger(1, outputData);


                    if (hash.equals(shaData.toString(16))) {
                        MainActivity.autenticacion = false;
                        Intent i = new Intent(Autenticacion.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Autenticacion.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void salir(View view) {
        finish();
        System.exit(0);
    }

}
