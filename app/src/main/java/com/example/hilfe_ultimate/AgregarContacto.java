package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarContacto extends AppCompatActivity {

    private EditText vetNombre;
    private EditText vetTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        vetNombre = (EditText) findViewById(R.id.etNombre);
        vetTelefono = (EditText) findViewById(R.id.etTelefono);
    }

    /**
     * Método que agrega un contacto a la base de datos.
     *
     * @param view Representación de los elementos en pantalla.
     */
    public void agregar(View view){
        ContactoBD cbd=new ContactoBD(this);
        Contacto contacto=new Contacto();
        contacto.setNombreContacto(vetNombre.getText().toString());
        contacto.setTelefonoContacto(vetTelefono.getText().toString());

        if (!vetNombre.getText().toString().isEmpty() && !vetTelefono.getText().toString().isEmpty()){
            long registro=cbd.agregarContacto(contacto);
            if (registro!=0){
                Toast.makeText(this, "Registro insertado", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Los campos nombre y telefono son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}
