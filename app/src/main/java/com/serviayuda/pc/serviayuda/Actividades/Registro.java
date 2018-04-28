package com.serviayuda.pc.serviayuda.Actividades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Activitys.RegistroActivity;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 06/02/2018.
 */

public class Registro extends AppCompatActivity {

    Button botonFinalizar;
    EditText campoNombre, campoApellidos, campoEdad, campoEmail, campoPassword1, campoPassword2, campoCiudad, campoLocalidad, campoCodigoPostal,
            campoCalle, campoNumero, campoBloque, campoPiso;
    TextView avisoNombre, avisoApellidos, avisoEdad, avisoEmail;
    CheckBox proveedor, solicitante;
    TextView campoResultado;
    ImageView tickNombre, tickApellidos, tickEdad, tickEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas() {

        //Campos a rellenar
        campoNombre = findViewById(R.id.registroNombre);
        campoApellidos = findViewById(R.id.registroApellidos);
        campoEdad = findViewById(R.id.registroEdad);
        campoEmail = findViewById(R.id.registroEmail);
        campoPassword1 = findViewById(R.id.registroPassword1);
        campoPassword2 = findViewById(R.id.registroPassword2);
        campoCiudad = findViewById(R.id.registroCiudad);
        campoLocalidad = findViewById(R.id.registroLocalidad);
        campoCalle = findViewById(R.id.registroCalle);
        campoNumero = findViewById(R.id.registroNumero);
        campoBloque = findViewById(R.id.registroBloque);
        campoPiso = findViewById(R.id.registroPiso);
        campoCodigoPostal = findViewById(R.id.registroCodigoPostal);
        campoResultado = findViewById(R.id.registroResultado);
        botonFinalizar = findViewById(R.id.botonFinalizaRegistro);

        //Campo avisos

        avisoNombre = findViewById(R.id.registroAvisoNombre);
        avisoApellidos = findViewById(R.id.registroAvisoApellidos);
        avisoEdad = findViewById(R.id.registroAvisoEdad);
        avisoEmail = findViewById(R.id.registroAvisoEmail);

        //Ticks

        tickNombre = findViewById(R.id.registroTickNombre);
        tickApellidos = findViewById(R.id.registroTickApellidos);
        tickEdad = findViewById(R.id.registroTickEdad);
        tickEmail = findViewById(R.id.registroTickEmail);

        //Checkboxs
        proveedor = findViewById(R.id.registroPrestar);
        solicitante = findViewById(R.id.registroSolicitar);
    }

    private void iniciarListeners() {
        botonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroBBDD();
            }
        });

        //Checkboxs
        proveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitante.setChecked(false);
            }
        });
        solicitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proveedor.setChecked(false);
            }
        });

        campoNombre.addTextChangedListener(registroWatcherNombre);
        campoApellidos.addTextChangedListener(registroWatcherApellidos);
        campoEdad.addTextChangedListener(registroWatcherEdad);

    }

    //Método listener de los campos introducidos
    private TextWatcher registroWatcherNombre = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nombre = campoNombre.getText().toString();
            if (nombre.isEmpty()) {
                avisoNombre.setText("Este campo no puede quedar vacío");
                avisoNombre.setTextColor(Color.RED);
                avisoNombre.setVisibility(View.VISIBLE);
                tickNombre.setImageResource(0);
            } else {
                avisoNombre.setText("");
                avisoNombre.setVisibility(View.GONE);
                Bitmap bitmaptick = BitmapFactory.decodeResource(getResources(), R.drawable.tickverde);
                RoundedBitmapDrawable imagenTick = RoundedBitmapDrawableFactory.create(getResources(), bitmaptick);
                tickNombre.setImageDrawable(imagenTick);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherApellidos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String apellidos = campoApellidos.getText().toString();
            if (apellidos.isEmpty()) {
                avisoApellidos.setText("Este campo no puede quedar vacío");
                avisoApellidos.setTextColor(Color.RED);
                avisoApellidos.setVisibility(View.VISIBLE);
                tickApellidos.setImageResource(0);

            } else {
                avisoApellidos.setText("");
                avisoApellidos.setVisibility(View.GONE);
                Bitmap bitmaptick = BitmapFactory.decodeResource(getResources(), R.drawable.tickverde);
                RoundedBitmapDrawable imagenTick = RoundedBitmapDrawableFactory.create(getResources(), bitmaptick);
                tickApellidos.setImageDrawable(imagenTick);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherEdad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String sEdad = campoEdad.getText().toString();
            if (sEdad.isEmpty()) {
                avisoEdad.setText("Este campo no puede quedar vacío");
                avisoEdad.setTextColor(Color.RED);
                avisoEdad.setVisibility(View.VISIBLE);
                tickEdad.setImageResource(0);
            } else if (esNumerico(sEdad)) {
                Integer edad = Integer.parseInt(sEdad);
                if (edad < 18) {
                    avisoEdad.setText("La edad no puede ser inferior a 18 años");
                    avisoEdad.setTextColor(Color.RED);
                    avisoEdad.setVisibility(View.VISIBLE);
                    tickEdad.setImageResource(0);
                } else if (edad > 120) {
                    avisoEdad.setText("¿De verdad?");
                    avisoEdad.setTextColor(Color.RED);
                    avisoEdad.setVisibility(View.VISIBLE);
                    tickEdad.setImageResource(0);
                } else {
                    avisoEdad.setText("");
                    avisoEdad.setVisibility(View.GONE);
                    Bitmap bitmaptick = BitmapFactory.decodeResource(getResources(), R.drawable.tickverde);
                    RoundedBitmapDrawable imagenTick = RoundedBitmapDrawableFactory.create(getResources(), bitmaptick);
                    tickEdad.setImageDrawable(imagenTick);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherEmail = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void registroBBDD() {
        String nombre = campoNombre.getText().toString();
        String email = campoEmail.getText().toString();
        String password = campoPassword1.getText().toString();
        new RegistroActivity(this, campoResultado).execute(nombre, email, password);
    }

    //Método que comprueba si una cadena es de tipo numérico
    private boolean esNumerico(String cadena) {
        try {
            Integer i = Integer.parseInt(cadena);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

