package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Telefonos extends AppCompatActivity {

    ListView lista;
    private static final String TAG = "Telefonos";

    public static ArrayList<Telefono> arrayTelefonos = new ArrayList<Telefono>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefonos);
        Log.d(TAG, "onCreate: Started.");
        lista = (ListView) findViewById(R.id.listaVista);

        Telefono tfno=new Telefono(1,"SOS","112");
        arrayTelefonos.add(tfno);
        Telefono tfno1=new Telefono(2,"Policia","091");
        arrayTelefonos.add(tfno1);
        Telefono tfno2=new Telefono(3,"Guardia civil","062");
        arrayTelefonos.add(tfno2);
        Telefono tfno3=new Telefono(4,"Bomberos","085");
        arrayTelefonos.add(tfno3);
        Telefono tfno4=new Telefono(5,"Emergencia medica","061");
        arrayTelefonos.add(tfno4);
        Telefono tfno5=new Telefono(6,"Cruz roja","901222222");
        arrayTelefonos.add(tfno5);
        Telefono tfno6=new Telefono(7,"Proteccion civil","1006");
        arrayTelefonos.add(tfno6);

//        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayTelefonos);
//        lista.setAdapter(adaptador);

        TelefonosListAdapter adapter=new TelefonosListAdapter(this, R.layout.adapter_view_layout, arrayTelefonos);
        lista.setAdapter(adapter);
    }
}