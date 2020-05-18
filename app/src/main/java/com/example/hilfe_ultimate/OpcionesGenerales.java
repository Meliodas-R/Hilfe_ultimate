package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpcionesGenerales extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_generales);
    }

    //Métodos de desplazamiento entre pantallas.
    public void irTelefonos(View view) {
        Intent i = new Intent(this, Telefonos.class);
        startActivity(i);
    }

    public void irContactos(View view) {
        Intent i = new Intent(this, Contactos.class);
        startActivity(i);
    }

    public void irUbicacion(View view) {
        Intent i = new Intent(this, Ubicacion.class);
        startActivity(i);
    }

    public void irNosedonde(View view) {
        Intent i = new Intent(this, Ajustes.class);
        startActivity(i);
    }

    public void irAtras(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void irMensajes(View view) {
        Intent i = new Intent(this, DeveloperContact.class);
        startActivity(i);
    }
}
