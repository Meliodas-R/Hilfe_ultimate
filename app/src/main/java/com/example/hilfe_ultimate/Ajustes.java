package com.example.hilfe_ultimate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.hilfe_ultimate.DriveServiceHelper.getGoogleDriveService;

/**
 * Muestra todos los ajustes de la aplicación.
 */
public class Ajustes extends AppCompatActivity {

    /**
     * Inicia la actividad, llama al constructor de la clase padre y se establece el xml.
     *
     * @param savedInstanceState Contiene los datos mas recientes de la actividad.
     */
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

    }

    /**
     * Garantiza el correcto funcionamiento de los elementos en pantalla.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {
        private static final int REQUEST_CODE_SIGN_IN = 100;
        private GoogleSignInClient mGoogleSignInClient;
        private DriveServiceHelper mDriveServiceHelper;
        private static final String TAG = "CopiaSeguridad";
        static String idFile;

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
                    iniciarSesion();
                    mostrarDialogoCopia();
                    return false;
                }


            });

            Preference preferenciaRespaldo = (Preference) findPreference("Recuperacion");
            preferenciaRespaldo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(final Preference preference) {
                    iniciarSesion();
                    mostrarDialogoRestaurar();
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

        /**
         * Pide al usuario autenticarse mediante su cuenta de Google, en caso de que no haya
         * iniciado sesión.
         *
         */
        private void iniciarSesion() {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (account == null) {
                registrarse();
            } else {
                mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getActivity(), account, "appName"));
            }
        }

        /**
         * Inicia sesión con la cuenta de Google seleccionada.
         *
         */
        private void registrarse() {

            mGoogleSignInClient = buildGoogleSignInClient();
            startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        }

        /**
         * Muestra la ventana de inicio de sesion con los correos.
         *
         * @return
         */
        private GoogleSignInClient buildGoogleSignInClient() {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(Drive.SCOPE_FILE)
                            .requestEmail()
                            .build();
            return GoogleSignIn.getClient(getActivity(), signInOptions);
        }

        /**
         * Lee la cuenta con la que el usuario quiere hacer la copia de seguridad.
         *
         * @param requestCode
         * @param resultCode
         * @param resultData
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
            switch (requestCode) {
                case REQUEST_CODE_SIGN_IN:
                    if (resultCode == Activity.RESULT_OK && resultData != null) {
                        handleSignInResult(resultData);
                    }
                    break;
            }

            super.onActivityResult(requestCode, resultCode, resultData);
        }

        /**
         * Maneja la cuenta con la que se inicia sesión.
         *
         * @param result Llama a otra actividad.
         */
        private void handleSignInResult(Intent result) {
            GoogleSignIn.getSignedInAccountFromIntent(result)
                    .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                        @Override
                        public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                            Log.d(TAG, "Signed in as " + googleSignInAccount.getEmail());
                            mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getActivity(), googleSignInAccount, "appName"));

                            Log.d(TAG, "handleSignInResult: " + mDriveServiceHelper);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Unable to sign in.", e);
                        }
                    });
        }

        /**
         * Muestra el diálogo de la copia de seguridad. (Similar a JOPtionPane)
         *
         */
        private void mostrarDialogoCopia(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Desea realizar una copia de seguridad?.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface dialog, int id) {
                            exportarBDD();
                            Toast.makeText(getActivity(), "Los datos han sido respaldados.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "La operacion se ha cancelado.", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        /**
         * Muestra el diálogo restaurar la copia de seguridad. (Similar a JOPtionPane)
         *
         */
        private void mostrarDialogoRestaurar(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Desea restaurar la copia de seguridad?.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface dialog, int id) {
                            importarBDD();
                            Toast.makeText(getActivity(), "Los datos han sido restaurados.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "La operacion se ha cancelado.", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        /**
         * Sube el archivo de base de datos a Google Drive.
         *
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void exportarBDD() {
            if (mDriveServiceHelper == null) {
                return;
            }
            //Se comprueba si hay un fichero de copia de seguridad en la nube Google Drive
            mDriveServiceHelper.searchFile("bd", "application/octet-stream")
                    .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                            if (googleDriveFileHolders == null) {
                                GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                                idFile = googleDriveFileHolder.getId();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            });

            mDriveServiceHelper.deleteFolderFile(idFile).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccesDelete");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            });


            mDriveServiceHelper.uploadFile(new File("/data/data/com.example.hilfe_ultimate/databases/", "bd"), "application/octet-stream", null)
                    .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                        @Override
                        public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                            idFile = googleDriveFileHolder.getId();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "No se ha podido subir el archivo.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        /**
         * Descarga e importa el fichero de base de datos subido a Google Drive.
         *
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void importarBDD() {
            if (mDriveServiceHelper == null) {
                return;
            }
            mDriveServiceHelper.searchFile("bd", "application/octet-stream")
                    .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                            GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                            idFile = googleDriveFileHolder.getId();
                            Log.d("TAG 3", "" + idFile);

                            File dir = new File("/data/data/com.example.hilfe_ultimate/databases");
                            if (dir.isDirectory()) {
                                String[] archivos = dir.list();
                                for (int i = 0; i < archivos.length; i++) {
                                    new File(dir, archivos[i]).delete();
                                }
                                File archivoBDD = new File("/data/data/com.example.hilfe_ultimate/databases/bd");
                                try {
                                    archivoBDD.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            mDriveServiceHelper.downloadFile(new File("/data/data/com.example.hilfe_ultimate/databases/", "bd"), "" + idFile + "")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Los datos han sido restaurados.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Ha habido un problema descargando el archivo de respaldo.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            });


        }

    }

}