package com.example.hilfe_ultimate;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;


/**
 * Realiza operaciones de lectura/escritura en los archivos de Drive mediante la API de REST y
 * abriendo  un selector de archivo.
 *
 */
public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    /**
     * Constructor de la clase DriveServiceHelper.
     *
     * @param driveService Servicio de Drive que permite interactuar con la nube.
     */
    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }

    /**
     * Recoge las credenciales del usuario y se conecta a Google Drive.
     *
     * @param context Contexto del método.
     * @param account Credencial de la cuenta de Google.
     * @param appName Nombre de la aplicación.
     * @return
     */
    public static Drive getGoogleDriveService(Context context, GoogleSignInAccount account, String appName) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton(DriveScopes.DRIVE_FILE));
        credential.setSelectedAccount(account.getAccount());

        Drive googleDriveService =
                new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName(appName)
                        .build();
        return googleDriveService;
    }

    /**
     * Borra un fichero de Google Drive a partir de una cadena de texto.
     *
     * @param fileId Nombre del fichero a borrar.
     * @return Null.
     */
    public Task<Void> deleteFolderFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute();
                }
                return null;
            }
        });
    }

    /**
     * Sube un fichero a Google Drive.
     *
     * @param localFile Archivo a subir.
     * @param mimeType Método de envío de información por la red.
     * @param folderId Identificador de la carpeta.
     * @return
     */
    public Task<GoogleDriveFileHolder> uploadFile(final java.io.File localFile, final String mimeType, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                List<String> root;
                if (folderId == null) {
                    root = Collections.singletonList("root");
                } else {

                    root = Collections.singletonList(folderId);
                }

                File metadata = new File()
                        .setParents(root)
                        .setMimeType(mimeType)
                        .setName(localFile.getName());

                FileContent fileContent = new FileContent(mimeType, localFile);

                File fileMeta = mDriveService.files().create(metadata, fileContent).execute();
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(fileMeta.getId());
                googleDriveFileHolder.setName(fileMeta.getName());
                return googleDriveFileHolder;
            }
        });
    }

    private static final String TAG = "DriveServiceHelper";

    /**
     * Busca un archivo en Google Drive.
     *
     * @param fileName Nombre del archivo.
     * @param mimeType Método de envío de información por la red.
     * @return Null.
     */
    public Task<List<GoogleDriveFileHolder>> searchFile(final String fileName, final String mimeType) {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
            @Override
            public List<GoogleDriveFileHolder> call() throws Exception {
                List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
                FileList result = mDriveService.files().list()
                        .setQ("name = '" + fileName + "' and mimeType ='" + mimeType + "'")
                        .setSpaces("drive")
                        .setFields("files(id, name,size,createdTime,modifiedTime,starred)")
                        .execute();

                if (!result.isEmpty()) {
                    for (int i = 0; i < result.getFiles().size(); i++) {
                        GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                        googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                        googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                        googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
                        googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
                        googleDriveFileHolderList.add(googleDriveFileHolder);

                    }
                    if (googleDriveFileHolderList != null) {
                        return googleDriveFileHolderList;
                    }
                }
                return null;
            }
        });
    }

    /**
     * Descarga un archivo de la nube Google Drive.
     *
     * @param fileSaveLocation Ruta donde se descargará el fichero.
     * @param fileId Identificador del fichero de Google Drive.
     * @return Null.
     */
    public Task<Void> downloadFile(final java.io.File fileSaveLocation, final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                OutputStream outputStream = new FileOutputStream(fileSaveLocation);
                mDriveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return null;
            }
        });
    }

}