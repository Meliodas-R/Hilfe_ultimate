package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InformacionContacto extends AppCompatActivity {

    TextView tvNombreNfo;
    TextView tvTelefonoNfo;
    Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_contacto);
        tvNombreNfo = (TextView) findViewById(R.id.tvInfoNombre);
        tvTelefonoNfo = (TextView) findViewById(R.id.tvInfoTelefono);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contacto = (Contacto) bundle.getSerializable("contacto");
            tvNombreNfo.setText(contacto.getNombreContacto());
            tvTelefonoNfo.setText(contacto.getTelefonoContacto());
        }
    }

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
}
