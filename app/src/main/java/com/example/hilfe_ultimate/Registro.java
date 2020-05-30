package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class Registro extends AppCompatActivity {

    EditText pass1;
    EditText pass2;
    private final String SHA = "SHA-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        pass1 = (EditText) findViewById(R.id.etRegistroPass1);
        pass2 = (EditText) findViewById(R.id.etRegistroPass2);

    }

    public void registrarse(View view){
        BigInteger shaData;

        if(pass1.getText().toString().isEmpty() || pass2.getText().toString().isEmpty()){
            Toast.makeText(this, "Deben completarse todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if(pass1.getText().toString().equals(pass2.getText().toString())){
                byte[] inputData = pass1.getText().toString().getBytes();
                byte[] outputData = new byte[0];
                try {
                    outputData = sha.encryptSHA(inputData, SHA);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                shaData = new BigInteger(1, outputData);
                SharedPreferences prefs =
                        getSharedPreferences("com.example.hilfe_ultimate_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Password", shaData.toString(16));
                editor.putBoolean("Registro", false);
                editor.commit();
                Toast.makeText(this, "Se establecido la contraseña", Toast.LENGTH_SHORT).show();

                finish();
                Intent i=new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
