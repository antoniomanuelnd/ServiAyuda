package com.serviayuda.pc.serviayuda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PC on 08/02/2018.
 */

public class Perfil extends AppCompatActivity {

    ImageView imagenPerfil;
    TextView campoProfesion;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        iniciarVistas();
     //   iniciarListeners();
    }

    private void iniciarVistas(){
        imagenPerfil = findViewById(R.id.imagenPerfil);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yo);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        campoProfesion = findViewById(R.id.perfilProfesion);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
    }
}
