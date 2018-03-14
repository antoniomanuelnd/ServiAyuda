package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by PC on 05/03/2018.
 */

public class AnuncioAmpliado extends AppCompatActivity {

    TextView campoNombre, campoTipo, campoHoraDeseada, campoHoras, campoUbicacion, campoDescripcion;
    Button botonVisitarPerfil, botonVolver, botonSolicitar;
    String emailProveedor;
    LinearLayout linearLayout;
    Anuncio anuncio = new Anuncio();
    ManejadorPreferencias mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio_ampliado);
        Intent i = getIntent();
        anuncio = getIntent().getExtras().getParcelable("anuncio");
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas(){

        //Inicialización de vistas
        campoNombre = findViewById(R.id.anuncioAmpliadoNombre);
        campoTipo = findViewById(R.id.anuncioAmpliadoTipo);
        campoUbicacion = findViewById(R.id.anuncioAmpliadoUbicacion);
        campoHoraDeseada = findViewById(R.id.anuncioAmpliadoHoraDeseada);
        campoHoras = findViewById(R.id.anuncioAmpliadoHoras);
        campoDescripcion = findViewById(R.id.anuncioAmpliadoDescripcion);
        linearLayout = findViewById(R.id.anuncioAmpliadoLayout);

        campoNombre.setText(anuncio.getNombre());
        campoTipo.setText(anuncio.getTipoAnuncio());
        campoUbicacion.setText("Sevilla");
        campoHoraDeseada.setText(anuncio.getHoraDeseada());
        campoHoras.setText(anuncio.getHoras());
        String descripcion = anuncio.getDescripcion();
        if (descripcion.isEmpty()){
            campoDescripcion.setText("El usuario no ha indicado más detalles");
        }else{
            campoDescripcion.setText(anuncio.getDescripcion());
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(23.0f);
        gd.setColor(Color.parseColor("#1D7196"));
        linearLayout.setBackground(gd);

        botonVisitarPerfil = findViewById(R.id.anuncioAmpliadoBotonPerfil);
        botonVolver = findViewById(R.id.anuncioAmpliadoBotonVolver);
        botonSolicitar = findViewById(R.id.anuncioAmpliadoBotonSolicitar);

        SharedPreferences preferences = this.getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        emailProveedor = mp.cargarPreferencias("KEY_EMAIL");

    }

    private void iniciarListeners(){
        botonVisitarPerfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnuncioAmpliado.this, VisitarPerfil.class);
                Bundle mbundle = new Bundle();
                mbundle.putString("email", anuncio.getEmail());
                i.putExtras(mbundle);
                startActivity(i);
            }
        });
        botonVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AnuncioAmpliado.super.finish();
            }
        });
        botonSolicitar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new EnviaSolicitudActivity(AnuncioAmpliado.this, anuncio).execute(emailProveedor);
            }
        });

    }
}
