package com.serviayuda.pc.serviayuda.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Activitys.RegistroActivity;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 06/02/2018.
 */

public class Registro extends AppCompatActivity {

    Button botonFinalizar;
    EditText campoNombre;
    EditText campoEmail;
    EditText campoPassword;
    TextView campoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas(){
        campoNombre = findViewById(R.id.registroNombre);
        campoEmail = findViewById(R.id.registroEmail);
        campoPassword = findViewById(R.id.registroPassword);
        campoResultado = findViewById(R.id.registroResultado);
        botonFinalizar = findViewById(R.id.botonFinalizaRegistro);
    }

    private void iniciarListeners(){
        botonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroBBDD();
            }
        });
    }

    private void registroBBDD(){
        String nombre = campoNombre.getText().toString();
        String email = campoEmail.getText().toString();
        String password = campoPassword.getText().toString();
        new RegistroActivity(this, campoResultado).execute(nombre, email, password);
    }
}

