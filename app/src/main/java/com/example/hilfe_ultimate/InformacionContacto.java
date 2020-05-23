package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InformacionContacto extends AppCompatActivity {

    EditText etNombreContacto, etTelefonoContacto;
    Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_contacto);
        etNombreContacto = (EditText) findViewById(R.id.etInfoNombre);
        etTelefonoContacto = (EditText) findViewById(R.id.etInfoTelefono);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contacto = (Contacto) bundle.getSerializable("contacto");
            etNombreContacto.setText(contacto.getNombreContacto());
            etTelefonoContacto.setText(contacto.getTelefonoContacto());
        }
    }

    /**
     * Método que elimina de la base de datos el contacto seleccionado.
     * @param view Representación en pantalla de los elementos.
     */
    public void eliminar(View view) {
        ContactoBD cbd = new ContactoBD(this);
        long registro = cbd.eliminarContacto(contacto.getIdContacto());
        if (registro != 0) {
            Toast.makeText(this, "Registro eliminado.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(InformacionContacto.this, Contactos.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "El registro no se pudo eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que termina la actividad y regresa a la anterior.
     *
     * @param view Representación en pantalla de los elementos.
     */
    public void volver(View view){
        finish();
    }
}
