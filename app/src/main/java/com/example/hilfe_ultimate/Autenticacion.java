package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Autenticacion extends AppCompatActivity {

    EditText etCampoPass;
    String passString;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);
        SharedPreferences preferenciasAutenticacion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        passString = preferenciasAutenticacion.getString("Password", "");
        etCampoPass = (EditText) findViewById(R.id.etPass);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCampoPass.toString().equals(passString)){
                    Toast.makeText(Autenticacion.this, "Correcto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Autenticacion.this, "Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    public void aaa(View view){
//        if(etCampoPass.toString()==passString){
//            Toast.makeText(this, "Entraste!", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, passString, Toast.LENGTH_SHORT).show();
//        }
//    }

    public void salir(View view){
        finish();
        System.exit(0);
    }
}
