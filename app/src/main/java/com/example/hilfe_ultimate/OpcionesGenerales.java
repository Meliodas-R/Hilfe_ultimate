package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
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

    /**
     * Método que lanza la actividad teléfonos.
     *
     * @param view Representación de la actividad teléfonos en pantalla.
     */
    public void irTelefonos(View view) {
        Intent i = new Intent(this, Telefonos.class);
        startActivity(i);
    }

    /**
     * Método que lanza la actividad contactos.
     *
     * @param view Representación de la actividad contactos en pantalla.
     */
    public void irContactos(View view) {
        Intent i = new Intent(this, Contactos.class);
        startActivity(i);
    }

    /**
     * Método que lanza la actividad ubicación.
     *
     * @param view Representación de la actividad ubicación en pantalla.
     */
    public void irUbicacion(View view) {
        Intent i = new Intent(this, Ubicacion.class);
        startActivity(i);
    }

    /**
     * Método que lanza la actividad ajustes.
     *
     * @param view Representación de la actividad ajustes en pantalla.
     */
    public void irAjustes(View view) {
        Intent i = new Intent(this, Ajustes.class);
        startActivity(i);
    }

    /**
     * Método que termina la actividad actual y regresa a la anterior.
     *
     * @param view Representación de la actividad ajustes generales en pantalla.
     */
    public void irAtras(View view) {
        finish();
    }

    public void irMensajes(View view) {
        Intent i = new Intent(this, DeveloperContact.class);
        startActivity(i);
    }
}
