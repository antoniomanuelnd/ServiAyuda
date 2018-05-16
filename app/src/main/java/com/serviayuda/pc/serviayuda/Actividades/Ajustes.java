package com.serviayuda.pc.serviayuda.Actividades;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.Activitys.InterfazActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 12/02/2018.
 */

public class Ajustes extends AppCompatActivity {


    Switch switchInterfaz, switchSesion;
    Button cerrarSesion;
    TextView snInterfaz, snSesion, textInterfaz;
    String estadoInterfaz;
    private ManejadorPreferencias mp;
    private DatabaseHelper databaseHelper;
    Usuario usuario;
    Boolean estadoPrevioSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes);
        iniciarVistas();
        iniciarListeners();

    }

    private void iniciarVistas() {

        switchInterfaz = findViewById(R.id.switchInterfaz);
        snInterfaz = findViewById(R.id.SNInterfaz);
        textInterfaz = findViewById(R.id.textInterfaz);

        estadoPrevioSwitch = switchInterfaz.isChecked();
        SharedPreferences preferences = this.getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        estadoInterfaz = mp.cargarPreferencias("KEY_INTERFAZ");

        databaseHelper = new DatabaseHelper(Ajustes.this);
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));

        if(usuario.getTipoPerfil().compareTo("Solicitante")==0){
            switchInterfaz.setVisibility(View.VISIBLE);
            snInterfaz.setVisibility(View.VISIBLE);
            textInterfaz.setVisibility(View.VISIBLE);
            if (estadoInterfaz.compareTo("No") == 0 || estadoInterfaz.compareTo("No existe la información") == 0) {
                switchInterfaz.setChecked(false);
                snInterfaz.setText("No");
            } else {
                switchInterfaz.setChecked(true);
                snInterfaz.setText("Si");
            }
        }else{
            switchInterfaz.setVisibility(View.GONE);
            snInterfaz.setVisibility(View.GONE);
            textInterfaz.setVisibility(View.GONE);
        }

    }

    private void iniciarListeners() {

        switchInterfaz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    snInterfaz.setText("Si");
                    mp.guardarPreferencias("KEY_INTERFAZ", "Si");
                    new InterfazActivity(Ajustes.this, mp.cargarPreferencias("KEY_EMAIL"), "Si").execute();
                } else {
                    snInterfaz.setText("No");
                    mp.guardarPreferencias("KEY_INTERFAZ", "No");
                    new InterfazActivity(Ajustes.this, mp.cargarPreferencias("KEY_EMAIL"), "No").execute();
                }
            }
        });
    }

    @Nullable
    @Override
    public ComponentName getCallingActivity() {
        return super.getCallingActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i;
        if (switchInterfaz.isChecked()) {
            i = new Intent(this, MenuInterfazAdaptada.class);
            finishAffinity();
        }else{
            i = new Intent(this, ActivitySetViewPagerSolicitante.class);
            finishAffinity();
        }
        startActivity(i);
    }
}
