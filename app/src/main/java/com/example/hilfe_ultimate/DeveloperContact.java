package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Permite contactar con el desarrollador de la aplicación.
 *
 */
public class DeveloperContact extends AppCompatActivity {

    EditText etDestinatario, etAsunto, etMensaje;
    Button btnEnviar;

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contactos");

        etDestinatario = findViewById(R.id.etDestinatario);
        etAsunto = findViewById(R.id.etAsunto);
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);

        etDestinatario.setText("ehj.development@gmail.com");

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:" + etDestinatario.getText().toString()));
                i.putExtra(Intent.EXTRA_SUBJECT, etAsunto.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT, etMensaje.getText().toString());
                startActivity(i);
            }
        });
    }

}
