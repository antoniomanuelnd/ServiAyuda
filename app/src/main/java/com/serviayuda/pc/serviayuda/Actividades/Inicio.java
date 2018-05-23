package com.serviayuda.pc.serviayuda.Actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.serviayuda.pc.serviayuda.Activitys.RecibeUsuarioActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeUsuarioSesionActivity;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

public class Inicio extends AppCompatActivity {

    Button botonIniciarSesion;
    Button botonCrearCuenta;
    ManejadorPreferencias mp;
    private final AppCompatActivity activity = Inicio.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        iniciarVistas();
        iniciarListeners();
    }


    private void iniciarVistas(){
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        botonCrearCuenta = findViewById(R.id.botonCrearCuenta);
        mp = new ManejadorPreferencias((getSharedPreferences("SESION", Activity.MODE_PRIVATE)));
    }

    private void iniciarListeners(){
        botonIniciarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio.this, InicioSesion.class);
                startActivity(i);
            }
        });

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio.this, Registro.class);
                startActivity(i);
            }
        });

        String sSesion = mp.cargarPreferencias("KEY_SESION");

        if(sSesion.compareTo("Si")==0){
            String email = mp.cargarPreferencias("KEY_EMAIL");
            new RecibeUsuarioSesionActivity(Inicio.this, activity).execute(email);
        }
    }
}