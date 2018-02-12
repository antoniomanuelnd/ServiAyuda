package com.serviayuda.pc.serviayuda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    Button botonIniciarSesion;
    Button botonCrearCuenta;

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
    }
}
