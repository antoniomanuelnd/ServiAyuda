package com.serviayuda.pc.serviayuda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class PerfilFragment extends Fragment {

    ImageView imagenPerfil;
    TextView campoProfesion;
    ImageView botonAjustes;
    ImageView botonEditar;
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

    private void iniciarVistas(){
        imagenPerfil = view.findViewById(R.id.perfilImagen);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.yo);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        botonAjustes = view.findViewById(R.id.perfilBotonAjustes);
        botonAjustes.setBackgroundResource(R.drawable.botonajusteslayout);
        botonEditar = view.findViewById(R.id.perfilBotonEditar);
        botonEditar.setBackgroundResource(R.drawable.botoneditarlayout);


        campoProfesion = view.findViewById(R.id.perfilProfesion);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
    }

    private void iniciarListeners(){

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
