package com.example.hilfe_ultimate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;


public class Ajustes extends AppCompatActivity {

    public final String SHA = "SHA-1";
    public BigInteger shaData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //boolean sync = preferences.getBoolean("Tema", false);
        String sync = preferences.getString("Metodo", "");

        switch (sync) {
            case "SMS":
                MainActivity.var = 1;
                break;

            case "Correo electrónico":
                MainActivity.var = 2;
                break;

            case "Llamada":
                MainActivity.var = 3;
                break;
        }

        String hash = preferences.getString("Password", "");

        byte[] inputData = hash.getBytes();
        byte[] outputData = new byte[0];
        try {
            outputData = sha.encryptSHA(inputData, SHA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shaData= new BigInteger(1, outputData);
        //Toast.makeText(this, shaData.toString(), Toast.LENGTH_SHORT).show();

        SharedPreferences prefs =
                getSharedPreferences("com.example.hilfe_ultimate_preferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Password", shaData.toString());
        editor.commit();


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference preference = (Preference) findPreference("Consentimiento");

            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(final Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Implementar esta función puede ralentizar su acceso a la aplicación, téngalo en cuenta antes de usar esta función.")
                            .setPositiveButton("Lo entiendo", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.show();
                    return false;
                }
            });

            Preference preferenciaCopiaSeguridad = (Preference) findPreference("Respaldo");
            preferenciaCopiaSeguridad.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(final Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("¿Desea realizar una copia de seguridad?.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getActivity(), "Se ha generado un respaldo de la base de datos.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getActivity(), "La operacion se ha cancelado.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.show();
                    return false;
                }
            });

            ListPreference listPreference = (ListPreference) findPreference("Metodo");
            Preference preferencePass = (Preference) findPreference("Password");
            preference = (Preference) findPreference("Consentimiento");

            if (preference.isEnabled()) {
                preferencePass.setEnabled(true);
            } else {
                preferencePass.setEnabled(false);
            }






        }

    }

}