package com.serviayuda.pc.serviayuda.Actividades;

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

import com.serviayuda.pc.serviayuda.Activitys.EnviaSolicitudActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 05/03/2018.
 */

public class AnuncioAmpliado extends AppCompatActivity {

    TextView campoNombre, campoTipo, campoHoraDeseada, campoHoras, campoCiudad, campoDescripcion, campoLocalidad;
    Button botonVisitarPerfil, botonVolver, botonSolicitar;
    String emailProveedor;
    LinearLayout linearLayout;
    Anuncio anuncio = new Anuncio();
    ManejadorPreferencias mp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio_ampliado);
        anuncio = getIntent().getExtras().getParcelable("anuncio");
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas(){

        databaseHelper = new DatabaseHelper(this);

        Usuario usuario = databaseHelper.getUsuario(anuncio.getEmail());
        //Inicialización de vistas
        campoNombre = findViewById(R.id.anuncioAmpliadoNombre);
        campoTipo = findViewById(R.id.anuncioAmpliadoTipo);
        campoCiudad = findViewById(R.id.anuncioAmpliadoCiudad);
        campoLocalidad = findViewById(R.id.anuncioAmpliadoLocalidad);
        campoHoraDeseada = findViewById(R.id.anuncioAmpliadoHoraDeseada);
        campoHoras = findViewById(R.id.anuncioAmpliadoHoras);
        campoDescripcion = findViewById(R.id.anuncioAmpliadoDescripcion);
        linearLayout = findViewById(R.id.anuncioAmpliadoLayout);

        campoNombre.setText(anuncio.getNombre());
        campoTipo.setText(anuncio.getTipoAnuncio());
        campoCiudad.setText(usuario.getCiudad());
        campoLocalidad.setText(usuario.getLocalidad());

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
