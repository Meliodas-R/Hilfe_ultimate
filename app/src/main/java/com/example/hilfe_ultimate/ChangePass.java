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

/**
 * Permite cambiar la contraseña.
 *
 */
public class ChangePass extends AppCompatActivity {

    private final String SHA = "SHA-1";
    private EditText oldPass, newPass, newPassConfirm;

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().hide();
        oldPass = (EditText) findViewById(R.id.etOldPass);
        newPass = (EditText) findViewById(R.id.etNewPass);
        newPassConfirm = (EditText) findViewById(R.id.etNewPassConf);
    }

    /**
     * Permite cambiar la contraseña antigua por una nueva.
     *
     * @param view Representacion en pantalla de los elementos.
     */
    public void cambiarContra(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String hash = preferences.getString("Password", "");
        BigInteger shaData;


        byte[] inputData = oldPass.getText().toString().getBytes();
        byte[] outputData = new byte[0];
        try {
            outputData = sha.encryptSHA(inputData, SHA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shaData = new BigInteger(1, outputData);

        if (oldPass.getText().toString().isEmpty() || newPass.getText().toString().isEmpty() || newPassConfirm.getText().toString().isEmpty()) {
            Toast.makeText(this, "Deben completarse todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if (hash.equals(shaData.toString(16))){
                Toast.makeText(this, "HA FUNCIONADO", Toast.LENGTH_SHORT).show();
                if(newPass.getText().toString().equals(newPassConfirm.getText().toString())){
                    inputData = newPass.getText().toString().getBytes();
                    outputData = new byte[0];
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
                    editor.commit();
                    Toast.makeText(this, "Se ha cambiado la contraseña", Toast.LENGTH_SHORT).show();

                    finish();
                    Intent i=new Intent(this, Ajustes.class);
                    startActivity(i);

                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "La contraseña es incorrecta.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Regresa a la actividad anterior.
     *
     * @param view Representación en pantalla de los elementos.
     */
    public void volver(View view){
        finish();
    }
}
