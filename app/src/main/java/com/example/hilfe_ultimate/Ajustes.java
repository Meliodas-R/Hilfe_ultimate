package com.example.hilfe_ultimate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
                    pedirCredenciales();
                    mostrarDialogoCopia();
                    return false;
                }


            });

            Preference preferenciaRespaldo = (Preference) findPreference("Recuperacion");
            preferenciaRespaldo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(final Preference preference) {
                    pedirCredenciales();
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

        private void pedirCredenciales() {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (account == null) {
                signIn();
            } else {
                mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getActivity(), account, "appName"));
            }
        }

        private void signIn() {

            mGoogleSignInClient = buildGoogleSignInClient();
            startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        }

        private GoogleSignInClient buildGoogleSignInClient() {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(Drive.SCOPE_FILE)
                            .requestEmail()
                            .build();
            return GoogleSignIn.getClient(getActivity(), signInOptions);
        }

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

        public void test() {
            System.out.println("test");
        }

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

        private void mostrarDialogoCopia(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Desea realizar una copia de seguridad?.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface dialog, int id) {
                            exportDatabase();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "La operacion se ha cancelado.", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        private void mostrarDialogoRestaurar(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Desea restaurar la copia de seguridad?.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface dialog, int id) {
                            importDatabase();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "La operacion se ha cancelado.", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void exportDatabase() {
            if (mDriveServiceHelper == null) {
                return;
            }

            File archivoBD = new File("/data/data/com.example.hilfe_ultimate/databases/bd");
            File archivoBD2 = new File("/data/data/com.example.hilfe_ultimate/databases/bd");

            mDriveServiceHelper.searchFile("bd", "application/octet-stream")
                    .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                            if (googleDriveFileHolders == null) {
                                GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                                idFile = googleDriveFileHolder.getId();
                                Toast.makeText(getActivity(), "Se ha realizado una copia de seguridad", Toast.LENGTH_SHORT).show();
                            }
                            Log.d(TAG, "onSuccess2: " + idFile);
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


            mDriveServiceHelper.uploadFile(new java.io.File("/data/data/com.example.hilfe_ultimate/databases/", "bd"), "application/octet-stream", null)
                    .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                        @Override
                        public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                            Gson gson = new Gson();
                            Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder));
                            idFile = googleDriveFileHolder.getId();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }
                    });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void importDatabase() {
            if (mDriveServiceHelper == null) {
                return;
            }
            mDriveServiceHelper.searchFile("bd", "application/octet-stream")
                    .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                            Gson gson = new Gson();
                            GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                            idFile = googleDriveFileHolder.getId();
                            Log.d("TAG 3", "" + idFile);

                            File dir = new File("/data/data/com.example.hilfe_ultimate/databases");
                            if (dir.isDirectory()) {
                                //obtiene un listado de los archivos contenidos en el directorio.
                                String[] hijos = dir.list();
                                //Elimina los archivos contenidos.
                                for (int i = 0; i < hijos.length; i++) {
                                    new File(dir, hijos[i]).delete();
                                }
                                Log.d("TAG", "Archivo Database Borrado");
                                File ficheroDatabase = new File("/data/data/com.example.hilfe_ultimate/databases/bd");
                                try {
                                    ficheroDatabase.createNewFile();
                                    Log.d("TAG", "Archivo Database Creado");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            Log.d("Id file", "" + idFile);
                            mDriveServiceHelper.downloadFile(new File("/data/data/com.example.hilfe_ultimate/databases/", "bd"), "" + idFile + "")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccesDownload: ");
                                            Toast.makeText(getActivity(), "Los datos han sido restaurados.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
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