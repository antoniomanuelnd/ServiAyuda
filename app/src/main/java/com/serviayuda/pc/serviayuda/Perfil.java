package com.serviayuda.pc.serviayuda;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PC on 08/02/2018.
 */

public class Perfil extends AppCompatActivity {

    ImageView imagenPerfil;
    TextView campoProfesion;
    ImageView botonAjustes;
    ImageView botonEditar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas(){
        imagenPerfil = findViewById(R.id.perfilImagen);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        botonAjustes = findViewById(R.id.perfilBotonAjustes);
        botonAjustes.setBackgroundResource(R.drawable.botonajusteslayout);
        botonEditar = findViewById(R.id.perfilBotonEditar);
        botonEditar.setBackgroundResource(R.drawable.botoneditarlayout);


        campoProfesion = findViewById(R.id.perfilProfesion);
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
                Intent i = new Intent(Perfil.this, Ajustes.class);
                startActivity(i);
            }
        });
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Perfil.this, Ajustes.class);
                startActivity(i);
            }
        });
    }
}
