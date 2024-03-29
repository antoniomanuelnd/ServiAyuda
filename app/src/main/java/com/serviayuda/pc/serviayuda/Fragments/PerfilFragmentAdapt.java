package com.serviayuda.pc.serviayuda.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.serviayuda.pc.serviayuda.Actividades.Ajustes;
import com.serviayuda.pc.serviayuda.Actividades.Editar;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudEnCursoActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Imagen;
import com.serviayuda.pc.serviayuda.Objetos.RoundedCornerTransformation;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Objetos.Utilidades;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;
import com.squareup.picasso.Picasso;

/**
 * Created by PC on 13/05/2018.
 */

public class PerfilFragmentAdapt extends Fragment{


    private ImageView imagenPerfil;
    //Campos de datos del usuario
    private TextView campoNombre;
    private TextView campoProfesion;
    private TextView campoDescripcion;
    private TextView campoCiudad;
    private TextView campoLocalidad;
    private TextView campoExperiencia;
    private TextView campoDireccion;
    private TextView campoEdad;
    //Fin
    private ImageView botonAjustes;
    private ImageView botonEditar;
    private ManejadorPreferencias mp;
    private DatabaseHelper databaseHelper;
    private Usuario usuario = new Usuario();
    private View view;
    private LinearLayout perfilExperiencia, perfilDireccion;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);
        iniciarVistas();
        iniciarListeners();

        //Realizar precarga de la solicitud en curso
        new RecibeSolicitudEnCursoActivity(getContext(), getActivity(), mp.cargarPreferencias("KEY_EMAIL")).execute();

        return view;
    }

    private void iniciarVistas() {

        //Actualización de campos del perfil


        campoNombre = view.findViewById(R.id.perfilNombre);
        campoProfesion = view.findViewById(R.id.perfilProfesion);
        campoDescripcion = view.findViewById(R.id.perfilDescripcion);
        campoExperiencia = view.findViewById(R.id.perfilExperiencia);
        campoCiudad = view.findViewById(R.id.perfilCiudad);
        campoLocalidad = view.findViewById(R.id.perfilLocalidad);
        campoDireccion = view.findViewById(R.id.perfilDireccion);
        campoEdad = view.findViewById(R.id.perfilEdad);
        perfilExperiencia = view.findViewById(R.id.perfilExperienciaLayout);
        perfilDireccion = view.findViewById(R.id.perfilDireccionLayout);

        //Botones
        botonAjustes = view.findViewById(R.id.perfilBotonAjustes);
        botonAjustes.setBackgroundResource(R.drawable.botonajusteslayout);
        botonEditar = view.findViewById(R.id.perfilBotonEditar);
        botonEditar.setBackgroundResource(R.drawable.botoneditarlayout);

        //Base de datos y preferencias
        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        databaseHelper = new DatabaseHelper(getActivity());
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));
        mp.guardarPreferencias("KEY_NOMBRE", usuario.getNombre());

        //Campo profesion
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
        campoProfesion.setText(usuario.getTipoServicio());

        //Imagen de perfil
        imagenPerfil = view.findViewById(R.id.perfilImagen);
        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);
        estableceFotoPerfil();

        //Rellena campos del perfil
        campoNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        campoDescripcion.setText(usuario.getDescripcion());

        if(usuario.getTipoPerfil().compareTo("Solicitante")==0){
            perfilExperiencia.setVisibility(View.GONE);
        }else{
            campoExperiencia.setText(usuario.getExperiencia());
            perfilDireccion.setVisibility(View.GONE);
        }

        campoCiudad.setText(usuario.getCiudad());
        campoLocalidad.setText(usuario.getLocalidad());
        campoDireccion.setText(usuario.getDireccion());
        campoEdad.setText(usuario.getEdad());
    }

    private void iniciarListeners() {

        botonAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Ajustes.class);
                startActivity(i);
            }
        });
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Editar.class);
                startActivity(i);
            }
        });

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

                    Picasso.with(getContext())
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
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}
