package com.serviayuda.pc.serviayuda.Actividades;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.serviayuda.pc.serviayuda.Objetos.Imagen;
import com.serviayuda.pc.serviayuda.Objetos.Utilidades;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;
import com.squareup.picasso.Picasso;

/**
 * Created by PC on 22/04/2018.
 */

public class Editar extends AppCompatActivity {

    private Button elegirFoto, subir;
    private ImageView fotoPerfil;
    private ProgressBar barraProgreso;
    private Uri fotoUri;

    ManejadorPreferencias mp;

    //Firebase
    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;
    private StorageTask subidaTask;
    String referencia;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        iniciarVistas();
        iniciarListeners();
    }
    private void iniciarVistas(){
        elegirFoto = findViewById(R.id.elegirFotoPerfil);
        subir = findViewById(R.id.subirImagen);
        fotoPerfil = findViewById(R.id.editarImagenPerfil);
        barraProgreso = findViewById(R.id.progresoSubida);

        SharedPreferences preferences = this.getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);

        referencia = Utilidades.creaClave(mp.cargarPreferencias("KEY_EMAIL"));
        StorageRef = FirebaseStorage.getInstance().getReference(referencia);
        DatabaseRef = FirebaseDatabase.getInstance().getReference(referencia);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        fotoPerfil.setImageDrawable(roundedImagen);

    }

    private void iniciarListeners(){

        elegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subidaTask != null && subidaTask.isInProgress()){
                    Toast.makeText(Editar.this, "Ya se está subiendo una foto", Toast.LENGTH_LONG).show();
                }else {
                    subirImagen();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            fotoUri = data.getData();

            Picasso.with(this).load(fotoUri).into(fotoPerfil);
        }
    }

    private void subirImagen(){



        if(fotoUri != null){
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getExtension(fotoUri));

            subidaTask = fileReference.putFile(fotoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    barraProgreso.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Editar.this, "Subida exitosa", Toast.LENGTH_LONG).show();
                            Imagen img = new Imagen(referencia, taskSnapshot.getDownloadUrl().toString());
                            String imgid = DatabaseRef.push().getKey();
                            DatabaseRef.child(imgid).setValue(img);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Editar.this, "No se ha podido subir", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progreso = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            barraProgreso.setProgress((int) progreso);
                        }
                    });
        }else {
            Toast.makeText(this, "No se ha seleccionado ningún archivo", Toast.LENGTH_LONG).show();
        }
    }


    private String getExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
