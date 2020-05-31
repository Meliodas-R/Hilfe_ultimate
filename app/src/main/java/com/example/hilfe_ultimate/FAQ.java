package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Muestra una serie de preguntas y respuestas.
 *
 */
public class FAQ extends AppCompatActivity {

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FAQ");

    }
}
