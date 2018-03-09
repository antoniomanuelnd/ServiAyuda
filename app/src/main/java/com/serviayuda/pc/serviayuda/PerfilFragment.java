package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PerfilFragment extends Fragment {

    ImageView imagenPerfil;
    //Campos de datos del usuario
    TextView campoNombre;
    TextView campoProfesion;
    TextView campoDescripcion;
    TextView campoUbicacion;
    TextView campoExperiencia;
    TextView campoHorario;
    //Fin
    ImageView botonAjustes;
    ImageView botonEditar;
    ManejadorPreferencias mp;
    DatabaseHelper databaseHelper;
    Usuario usuario = new Usuario();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);
        iniciarVistas();
        iniciarListeners();
        return view;
    }

    private void iniciarVistas() {

        //Actualización de campos del perfil

        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        campoNombre = view.findViewById(R.id.perfilNombre);
        campoProfesion = view.findViewById(R.id.perfilProfesion);
        campoDescripcion = view.findViewById(R.id.perfilDescripcion);
        campoExperiencia = view.findViewById(R.id.perfilExperiencia);
        campoUbicacion = view.findViewById(R.id.perfilUbicacion);
        campoHorario = view.findViewById(R.id.perfilHorario);

        databaseHelper = new DatabaseHelper(getActivity());
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));
        mp.guardarPreferencias("KEY_NOMBRE", usuario.getNombre());

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
        campoProfesion.setText(usuario.getTipoServicio());

        imagenPerfil = view.findViewById(R.id.perfilImagen);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        botonAjustes = view.findViewById(R.id.perfilBotonAjustes);
        botonAjustes.setBackgroundResource(R.drawable.botonajusteslayout);
        botonEditar = view.findViewById(R.id.perfilBotonEditar);
        botonEditar.setBackgroundResource(R.drawable.botoneditarlayout);

        campoNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        campoDescripcion.setText(usuario.getDescripcion());
        campoExperiencia.setText(usuario.getExperiencia());
        campoUbicacion.setText(usuario.getUbicacion());
        campoHorario.setText(usuario.getHorario());
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
                Intent i = new Intent(view.getContext(), Ajustes.class);
                startActivity(i);
            }
        });
    }

}
