package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Agrega un contacto a la base de datos.
 *
 */
public class AgregarContacto extends AppCompatActivity {

    private EditText vetNombre;
    private EditText vetTelefono;

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        getSupportActionBar().hide();

        vetNombre = (EditText) findViewById(R.id.etNombre);
        vetTelefono = (EditText) findViewById(R.id.etTelefono);
    }

    /**
     * Agrega un contacto a la base de datos.
     *
     * @param view Representación de los elementos en pantalla.
     */
    public void agregar(View view) {
        ContactoBD cbd = new ContactoBD(this);
        Contacto contacto = new Contacto();
        contacto.setNombreContacto(vetNombre.getText().toString());
        contacto.setTelefonoContacto(vetTelefono.getText().toString());

        if (!vetNombre.getText().toString().isEmpty() && !vetTelefono.getText().toString().isEmpty()) {
            long registro = cbd.agregarContacto(contacto);
            if (registro != 0) {
                Toast.makeText(this, "Registro insertado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Los campos nombre y telefono son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Elimina el contenido de los campos.
     *
     * @param view Representación de los elementos en pantalla.
     */
    public void limpiar(View view) {
        vetNombre.setText("");
        vetTelefono.setText("");
    }

    /**
     * Regresa a la pantalla anterior.
     *
     * @param view Representación de los elementos en pantalla.
     */
    public void volver(View view){
        finish();
        Intent i = new Intent(this, Contactos.class);
        startActivity(i);
    }
}
