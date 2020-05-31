package com.example.hilfe_ultimate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class CopiaSeguridad extends AppCompatActivity {

    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveServiceHelper mDriveServiceHelper;
    private static final String TAG = "CopiaSeguridad";
    static String idFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copia_seguridad);
        this.setTitle("Copias de Seguridad");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account == null) {
            signIn();
        } else {
            mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), account, "appName"));
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
        return GoogleSignIn.getClient(getApplicationContext(), signInOptions);
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
                        mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName"));

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportDatabase(View view) {
        if (mDriveServiceHelper == null) {
            return;
        }

        File archivoBD = new File("/data/data/com.example.hilfe_ultimate/databases/bd");
        File archivoBD2 = new File("/data/data/com.example.hilfe_ultimate/databases/bd");

                mDriveServiceHelper.searchFile("bd", "application/octet-stream")
                .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                    @Override
                    public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                        Gson gson = new Gson();
                        if (googleDriveFileHolders == null) {
                            GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                            idFile = googleDriveFileHolder.getId();
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
    public void importDatabase(View view) {
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
                        mDriveServiceHelper.downloadFile(new java.io.File("/data/data/com.example.hilfe_ultimate/databases/", "bd"), "" + idFile + "")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccesDownload: ");
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
