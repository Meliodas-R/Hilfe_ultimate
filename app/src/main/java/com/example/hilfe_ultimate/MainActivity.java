package com.example.hilfe_ultimate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * Clase que realiza contactos de emergencia (llamadas, correos, sms)
 */
public class MainActivity extends AppCompatActivity {

    Button panicButton;
    String numero2 = "684313961";
    public static Integer var = 3;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermisos();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        final String correoDefectoString = preferences.getString("CorreoDefecto", "");
        final String asuntoDefectoString = preferences.getString("CorreoAsunto", "");
        final String cuerpoDefectoString = preferences.getString("CorreoCuerpo", "");

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(this, "bd_contactos", null, 1);

        panicButton = (Button) findViewById(R.id.boton_perso);
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (var == 1) {
                    enviarMensaje("684313961", "ELBOW DESTRUCTION");
                }
                if (var == 2) {
                    enviarCorreo(correoDefectoString, asuntoDefectoString, cuerpoDefectoString);
                }
                if (var == 3) {
                    realizarLlamada(numero2);
                }

            }
        });

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

    /**
     * Método que envia un correo electrónico.
     *
     * @param destinatario Correo electrónico al que enviar el mensaje.
     * @param asunto       Asunto del correo.
     * @param mensaje      Cuerpo del mensaje a enviar.
     */
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

    /**
     * Método que realiza la petición de todos los permisos necesarios para que la app funcione.
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pedirPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Snackbar.make(this.findViewById(android.R.id.content), "Por favor, conceda permisos para poder usar la aplicación.", Snackbar.LENGTH_INDEFINITE).setAction("Conceder", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE}, PERMISSIONS_MULTIPLE_REQUEST);
                    }
                }).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE}, PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // Código por si el permiso ya ha sido dado.
        }
    }
}




