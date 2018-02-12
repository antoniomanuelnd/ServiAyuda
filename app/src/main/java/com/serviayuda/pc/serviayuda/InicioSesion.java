package com.serviayuda.pc.serviayuda;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PC on 06/02/2018.
 */

public class InicioSesion extends AppCompatActivity {

    Button botonIniciarSesion;
    EditText campoEmail;
    EditText campoPassword;
    TextView campoResultado;
    ManejadorPreferencias mp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciosesion);
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas() {
        campoEmail = findViewById(R.id.inicioSesionEmail);
        campoPassword = findViewById(R.id.inicioSesionPassword);
        botonIniciarSesion = findViewById(R.id.inicioSesionEntrar);
        campoResultado = findViewById(R.id.inicioSesionResultado);
        mp = new ManejadorPreferencias((getSharedPreferences("SESION", Context.MODE_PRIVATE)));
    }

    private void iniciarListeners() {
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestraResultado();
            }
        });
    }

    private void muestraResultado() {

        String email = campoEmail.getText().toString();
        String password = campoPassword.getText().toString();
        mp.guardarPreferencias("KEY_EMAIL", email);
        new InicioSesionActivity(this, campoResultado).execute(email, password);

    }
}