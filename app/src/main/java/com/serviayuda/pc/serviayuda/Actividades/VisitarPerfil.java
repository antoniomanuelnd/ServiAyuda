package com.serviayuda.pc.serviayuda.Actividades;

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

import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 07/03/2018.
 */

public class VisitarPerfil extends AppCompatActivity{

    String email;
    //Campos de datos del usuario
    ImageView imagenPerfil;
    TextView campoNombre;
    TextView campoProfesion;
    TextView campoDescripcion;
    TextView campoUbicacion;
    TextView campoExperiencia;
    TextView campoHorario;

    DatabaseHelper databaseHelper;
    Usuario usuario = new Usuario();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitarperfil);
        email = getIntent().getExtras().getString("email");
        databaseHelper = new DatabaseHelper(this);
        usuario = databaseHelper.getUsuario(email);
        iniciarVistas();
    }
    private void iniciarVistas(){
        //Actualización de campos del perfil
        imagenPerfil = findViewById(R.id.visitarPerfilImagen);
        campoNombre = findViewById(R.id.visitarPerfilNombre);
        campoProfesion = findViewById(R.id.visitarPerfilProfesion);
        campoDescripcion = findViewById(R.id.visitarPerfilDescripcion);
        campoExperiencia = findViewById(R.id.visitarPerfilExperiencia);
        campoUbicacion = findViewById(R.id.visitarPerfilUbicacion);
        campoHorario = findViewById(R.id.visitarPerfilHorario);

        campoNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        campoDescripcion.setText(usuario.getDescripcion());
        campoExperiencia.setText(usuario.getExperiencia());
        campoUbicacion.setText(usuario.getUbicacion());
        campoHorario.setText(usuario.getHorario());

        //Estilo foto de perfil
        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#1D7196"));
        gd.setCornerRadius(15.0f);
        campoProfesion.setBackground(gd);
        campoProfesion.setText(usuario.getTipoServicio());

    }
}
