package com.serviayuda.pc.serviayuda.Actividades;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Activitys.InicioSesionActivity;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 06/02/2018.
 */

public class InicioSesion extends AppCompatActivity {

    Button botonIniciarSesion;
    EditText campoEmail;
    EditText campoPassword;
    TextView campoResultado;
    CheckBox sesion;
    ManejadorPreferencias mp;
    String sSesion;
    private final AppCompatActivity activity = InicioSesion.this;

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
        sesion = findViewById(R.id.checkbox1Sesion);
        mp = new ManejadorPreferencias((getSharedPreferences("SESION", Activity.MODE_PRIVATE)));
        sSesion = mp.cargarPreferencias("KEY_SESION");

        if(sSesion.compareTo("No")==0 || sSesion.compareTo("No existe la información") == 0){
            sesion.setChecked(false);
        }else{
            sesion.setChecked(true);
        }

    }

    private void iniciarListeners() {
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sesion.isChecked()){
                    sSesion = "Si";
                }else{
                    sSesion = "No";
                }
            }
        });
    }

    private void iniciarSesion() {
        String email = campoEmail.getText().toString();
        String password = campoPassword.getText().toString();
        new InicioSesionActivity(this, campoResultado, activity, mp, sSesion).execute(email, password);
    }
}