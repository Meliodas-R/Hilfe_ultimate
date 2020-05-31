package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Muestra una serie de botones que llevan a otras pantallas.
 *
 */
public class OpcionesGenerales extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_generales);
        getSupportActionBar().hide();
    }

    /**
     * Lanza la actividad contactos.
     *
     * @param view Representación de la actividad contactos en pantalla.
     */
    public void irContactos(View view) {
        Intent i = new Intent(this, Contactos.class);
        startActivity(i);
    }

    /**
     * Lanza la actividad ubicación.
     *
     * @param view Representación de la actividad ubicación en pantalla.
     */
    public void irUbicacion(View view) {
        Intent i = new Intent(this, Ubicacion.class);
        startActivity(i);
    }

    /**
     * Lanza la actividad ajustes.
     *
     * @param view Representación de la actividad ajustes en pantalla.
     */
    public void irAjustes(View view) {
        Intent i = new Intent(this, Ajustes.class);
        startActivity(i);
    }

    /**
     * Termina la actividad actual y regresa a la anterior.
     *
     * @param view Representación de la actividad ajustes generales en pantalla.
     */
    public void irAtras(View view) {
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Lanza la actividad FAQ.
     *
     * @param view Representación de la actividad FAQ en pantalla.
     */
    public void irFaq(View view) {
        Intent i = new Intent(this, FAQ.class);
        startActivity(i);
    }
}
