package com.serviayuda.pc.serviayuda.Actividades;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Imagen;
import com.serviayuda.pc.serviayuda.Objetos.RoundedCornerTransformation;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Objetos.Utilidades;
import com.serviayuda.pc.serviayuda.R;
import com.squareup.picasso.Picasso;

/**
 * Created by PC on 07/03/2018.
 */

public class VisitarPerfil extends AppCompatActivity{

    String email;
    //Campos de datos del usuario
    ImageView imagenPerfil;
    TextView campoNombre;
    TextView campoProfesion;
    TextView campoDescripcion;
    TextView campoCiudad;
    TextView campoLocalidad;
    TextView campoExperiencia;
    TextView campoDireccion;
    TextView campoEdad;
    LinearLayout perfilExperiencia, perfilDireccion;
    DatabaseHelper databaseHelper;
    Usuario usuario = new Usuario();

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitarperfil);
        email = getIntent().getExtras().getString("email");
        databaseHelper = new DatabaseHelper(this);
        usuario = databaseHelper.getUsuario(email);
        iniciarVistas();
    }
    private void iniciarVistas(){
        //Actualización de campos del perfil
        imagenPerfil = findViewById(R.id.visitarPerfilImagen);
        campoNombre = findViewById(R.id.visitarPerfilNombre);
        campoProfesion = findViewById(R.id.visitarPerfilProfesion);
        campoDescripcion = findViewById(R.id.visitarPerfilDescripcion);
        campoExperiencia = findViewById(R.id.visitarPerfilExperiencia);
        campoCiudad = findViewById(R.id.visitarPerfilCiudad);
        campoLocalidad = findViewById(R.id.visitarPerfilLocalidad);
        campoDireccion = findViewById(R.id.visitarPerfilDireccion);
        campoEdad = findViewById(R.id.visitarPerfilEdad);
        perfilExperiencia = findViewById(R.id.visitarPerfilExperienciaLayout);
        perfilDireccion = findViewById(R.id.visitarPerfilDireccionLayout);

        campoNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        campoDescripcion.setText(usuario.getDescripcion());
        if(usuario.getTipoPerfil().compareTo("Solicitante")==0){
            perfilExperiencia.setVisibility(View.GONE);
        }else{
            campoExperiencia.setText(usuario.getExperiencia());
        }

        campoCiudad.setText(usuario.getCiudad());
        campoLocalidad.setText(usuario.getLocalidad());

        Solicitud solicitud = databaseHelper.getSolicitud(email);
        if (solicitud.getEstado() == null){
            perfilDireccion.setVisibility(View.GONE);
        }else if (solicitud.getEstado().compareTo("En curso") == 0 && usuario.getTipoPerfil().compareTo("Solicitante")==0) {
            campoDireccion.setText(usuario.getDireccion());
        }

        if (usuario.getTipoPerfil().compareTo("Proveedor")==0){
            perfilDireccion.setVisibility(View.GONE);
        }
        campoDireccion.setText(usuario.getDireccion());
        campoEdad.setText(usuario.getEdad());

        //Estilo foto de perfil
        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);
        estableceFotoPerfil();

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
        campoProfesion.setText(usuario.getTipoServicio());



    }
    private void estableceFotoPerfil() {
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Utilidades.creaClave(usuario.getEmail()));

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Imagen img = postSnapshot.getValue(Imagen.class);
                    img.setKey(postSnapshot.getKey());
                    final int radius = 5;
                    final int margin = 5;

                    Picasso.with(VisitarPerfil.this)
                            .load(img.getUri())
                            .placeholder(R.drawable.defaultphotoprofile)
                            .fit()
                            .centerCrop()
                            .transform(new RoundedCornerTransformation())
                            .into(imagenPerfil);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VisitarPerfil.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
