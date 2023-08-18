package com.codificatus.ayudanteTecnico.respaldodb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codificatus.ayudanteTecnico.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.IOException;
import java.io.OutputStream;

public class funcRespaldoActivity extends AppCompatActivity {

    private Button backupButton;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_respaldo);

        backupButton = findViewById(R.id.backup_button);
        downloadButton = findViewById(R.id.download_button);

    }
}
/*
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performBackup();
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDownload();
            }
        });
    }

 */

    /*
    private void performBackup() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            DriveResourceClient driveResourceClient = Drive.getDriveResourceClient(this, account);

            // Cambia 'backupFileName' por el nombre que deseas para el archivo de copia de seguridad
            String backupFileName = "backup_db.txt";

            MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                    .setTitle(backupFileName)
                    .setMimeType("text/plain")
                    .build();

            driveResourceClient.getRootFolder()
                    .addOnSuccessListener(rootFolder -> {
                        driveResourceClient.createFile(rootFolder, metadataChangeSet, null)
                                .addOnSuccessListener(backupFile -> {
                                    driveResourceClient.openFile(backupFile, DriveFile.MODE_WRITE_ONLY, null)
                                            .addOnSuccessListener(driveContents -> {
                                                // Aquí es donde debes escribir los datos de la copia de seguridad en el driveContents
                                                // Obtén un OutputStream desde driveContents
                                                OutputStream outputStream = driveContents.getOutputStream();

                                                // Ahora puedes escribir tus datos en el outputStream
                                                // Por ejemplo, si tienes una cadena de texto llamada 'backupData':
                                                String backupData = "Datos de la copia de seguridad";
                                                try {
                                                    outputStream.write(backupData.getBytes());
                                                    outputStream.close();

                                                    // Cerrar y guardar los cambios en el archivo
                                                    driveResourceClient.commitContents(driveContents, null)
                                                            .addOnSuccessListener(result -> {
                                                                Log.d("Backup", "Backup successful");
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Log.e("Backup", "Error committing changes: " + e.getMessage());
                                                            });
                                                } catch (IOException e) {
                                                    Log.e("Backup", "Error writing data: " + e.getMessage());
                                                }
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e("Backup", "Error opening file: " + e.getMessage());
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Backup", "Error creating file: " + e.getMessage());
                                });
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Backup", "Error getting root folder: " + e.getMessage());
                    });
        }
    }





    private void performDownload() {
        // Aquí implementa la lógica para descargar la copia de seguridad desde Google Drive
        // Puedes usar Drive API para obtener el archivo de la carpeta específica
        // y leer los datos para restaurar la base de datos
        // Consulta la documentación de Drive API para más detalles
    }

     */

