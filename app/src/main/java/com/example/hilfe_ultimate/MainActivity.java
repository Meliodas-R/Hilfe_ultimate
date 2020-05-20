package com.example.hilfe_ultimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button panicButton;
    String numero2 = "684313961";
    public static Integer var = 2;
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gestion de permisos
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para realizar llamadas telefónicas.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso!");
        }

        permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para emviar mensajes de texto.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1000);
        } else {
            Log.i("Mensaje", "Se tiene permiso!");
        }

        permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.INTERNET);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para emviar mensajes de texto.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1001);
        } else {
            Log.i("Mensaje", "Se tiene permiso!");
        }

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(this, "bd_contactos", null, 1);

        panicButton = (Button) findViewById(R.id.boton_perso);
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (var == 1) {
                    enviarMensaje("684313961", "ELBOW DESTRUCTION");
                }
                if (var == 2) {
                    enviarCorreo("aserlaredo@gmail.com","Elbow destruction","Hola amigos");
                }
                if (var == 3) {
                    realizarLlamada(numero2);
                }

            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        boolean sync = preferences.getBoolean("Tema",false);
//        Toast.makeText(getApplicationContext(), sync + "", Toast.LENGTH_SHORT).show();
//
//        String nombreFirma = preferences.getString("Nombre","");
//        Toast.makeText(this, nombreFirma, Toast.LENGTH_SHORT).show();

    }


    /**
     * Método que permite al usuario desplazarse a la ventana de opciones.
     *
     * @param view Representación en pantalla de los elementos pertenecientes a la vista de
     *             opciones generales.
     */
    public void IrOpciones(View view) {
        Intent i = new Intent(this, OpcionesGenerales.class);
        startActivity(i);
    }

    /**
     * Método que cierra la aplicación.
     *
     * @param view Representación en pantalla de los elementos.
     */
    public void salir(View view) {
        finish();
        System.exit(0);
    }

    /**
     * Método que realiza el envío de un mensaje SMS.
     *
     * @param numero  Número de teléfono al que se envía el SMS.
     * @param mensaje Texto que es enviado.
     */
    private void enviarMensaje(String numero, String mensaje) {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero, null, mensaje, null, null);
            Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "El mensaje no se pudo enviar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void enviarCorreo(String destinatario, String asunto, String mensaje) {

        Intent i = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto:" + destinatario));
        i.putExtra(Intent.EXTRA_SUBJECT, asunto);
        i.putExtra(Intent.EXTRA_TEXT, mensaje);
        startActivity(i);
    }

    /**
     * Método que realiza una llamada telefónica.
     *
     * @param numero Número de teléfono al que contactar.
     */
    private void realizarLlamada(String numero) {
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(i);
    }

}



