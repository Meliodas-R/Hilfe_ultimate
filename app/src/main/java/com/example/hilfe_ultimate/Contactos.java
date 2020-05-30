package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class Contactos extends AppCompatActivity {

    ListView lvContactos;
    ArrayList<Contacto> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        getSupportActionBar().hide();
        consultarListaContactos();
        lvContactos = findViewById(R.id.listViewContactos);
        ContactosListAdapter cla = new ContactosListAdapter(this, listaContactos);
        lvContactos.setAdapter(cla);
        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto conta = (Contacto) lvContactos.getItemAtPosition(position);
                Intent i = new Intent(Contactos.this, InformacionContacto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contacto", conta);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Muestra todos los contactos de la base de datos.
     */
    private void consultarListaContactos() {
        ContactoBD cdb = new ContactoBD(this);
        listaContactos = cdb.listarContactos();

    }

    /**
     * Lanza la actividad AgregarContacto.
     *
     * @param view Representación de la actividad AgregarContacto en pantalla .
     */
    public void irAgregar(View view) {
        Intent i = new Intent(this, AgregarContacto.class);
        startActivity(i);
    }

    /**
     * Refresca/actualiza la actividad actual.
     *
     * @param view Representación actualizada de la actividad Contactos.
     */
    public void refrescar(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


}
