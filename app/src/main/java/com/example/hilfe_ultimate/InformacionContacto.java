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
     * Elimina de la base de datos el contacto seleccionado.
     *
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
     * Modifica el contacto seleccionado.
     *
     * @param view Representación en pantalla de los elementos.
     */
    public void modificar(View view) {
        Contacto contactoM = new Contacto();
        contactoM.setNombreContacto(etNombreContacto.getText().toString());
        contactoM.setTelefonoContacto(etTelefonoContacto.getText().toString());
        ContactoBD cbd = new ContactoBD(this);
        long registro = cbd.modificarContacto(contactoM, contacto.getIdContacto());
        if (registro != 0) {
            Toast.makeText(this, "Registro actualizado.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El registro no se ha modificado.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Termina la actividad y regresa a la anterior.
     *
     * @param view Representación en pantalla de los elementos.
     */
    public void volver(View view){
        finish();
        Intent i=new Intent(this, Contactos.class);
        startActivity(i);
    }

    public void marcarComoPredeterminado(View view){
        String p= contacto.getTelefonoContacto();
        Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
        MainActivity.numeroTelefono=p;
    }
}
