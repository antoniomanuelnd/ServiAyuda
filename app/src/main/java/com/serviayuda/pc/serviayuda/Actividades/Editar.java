package com.serviayuda.pc.serviayuda.Actividades;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.serviayuda.pc.serviayuda.Activitys.EditarActivity;
import com.serviayuda.pc.serviayuda.Activitys.EditarCampoActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Imagen;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Objetos.Utilidades;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

/**
 * Created by PC on 22/04/2018.
 */

public class Editar extends AppCompatActivity {

    private Button elegirFoto, subir, descripcionEditar, descripcionGuardar, experienciaEditar, experienciaGuardar,
            ciudadEditar, ciudadGuardar, localidadEditar, localidadGuardar, direccionEditar, direccionGuardar, cpEditar, cpGuardar, nombreEditar, nombreGuardar,
            apellidosEditar, apellidosGuardar, edadEditar, edadGuardar;
    private ImageView fotoPerfil;
    private ProgressBar barraProgreso;
    private Uri fotoUri;
    private LinearLayout experienciaLayout, direccionLayout, cpLayout;
    private TextView descripcionTv, descripcionCont, experienciaTv, experienciaCont, ciudadTv, ciudadCont, localidadTv, localidadCont, direccionTv, direccionCont, cpTv, cpCont,
            nombreTv, nombreCont, apellidosTv, apellidosCont, edadTv, edadCont;
    private EditText descripcionEt, experienciaEt, ciudadEt, localidadEt, direccionEt, cpEt, nombreEt, apellidosEt, edadEt;
    Usuario usuario;

    ManejadorPreferencias mp;
    private DatabaseHelper databaseHelper;

    //Firebase
    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;
    private StorageTask subidaTask;
    String referencia;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        iniciarVistas();
        iniciarListeners();
    }

    private void iniciarVistas() {

        //Botones
        elegirFoto = findViewById(R.id.elegirFotoPerfil);
        subir = findViewById(R.id.subirImagen);
        descripcionEditar = findViewById(R.id.editarDescripcionEditar);
        descripcionGuardar = findViewById(R.id.editarDescripcionGuardar);
        experienciaEditar = findViewById(R.id.editarExperienciaEditar);
        experienciaGuardar = findViewById(R.id.editarExperienciaGuardar);
        ciudadEditar = findViewById(R.id.editarCiudadEditar);
        ciudadGuardar = findViewById(R.id.editarCiudadGuardar);
        localidadEditar = findViewById(R.id.editarLocalidadEditar);
        localidadGuardar = findViewById(R.id.editarLocalidadGuardar);
        direccionEditar = findViewById(R.id.editarDireccionEditar);
        direccionGuardar = findViewById(R.id.editarDireccionGuardar);
        cpEditar = findViewById(R.id.editarCPEditar);
        cpGuardar = findViewById(R.id.editarCPGuardar);
        nombreEditar = findViewById(R.id.editarNombreEditar);
        nombreGuardar = findViewById(R.id.editarNombreGuardar);
        apellidosEditar = findViewById(R.id.editarApellidosEditar);
        apellidosGuardar = findViewById(R.id.editarApellidosGuardar);
        edadEditar = findViewById(R.id.editarEdadEditar);
        edadGuardar = findViewById(R.id.editarEdadGuardar);

        //Foto de perfil
        fotoPerfil = findViewById(R.id.editarImagenPerfil);

        //Bara de progreso
        barraProgreso = findViewById(R.id.progresoSubida);

        //Layouts
        descripcionTv = findViewById(R.id.editarDescripcionTextView);
        descripcionEt = findViewById(R.id.editarDescripcionEditText);
        descripcionCont = findViewById(R.id.editarDescripcionCont);

        experienciaLayout = findViewById(R.id.editarExperienciaLayout);
        experienciaTv = findViewById(R.id.editarExperienciaTextView);
        experienciaEt = findViewById(R.id.editarExperienciaEditText);
        experienciaCont = findViewById(R.id.editarExperienciaCont);

        ciudadTv = findViewById(R.id.editarCiudadTextView);
        ciudadEt = findViewById(R.id.editarCiudadEditText);
        ciudadCont = findViewById(R.id.editarCiudadCont);

        localidadTv = findViewById(R.id.editarLocalidadTextView);
        localidadEt = findViewById(R.id.editarLocalidadEditText);
        localidadCont = findViewById(R.id.editarLocalidadCont);

        direccionLayout = findViewById(R.id.editarDireccionLayout);
        direccionTv = findViewById(R.id.editarDireccionTextView);
        direccionEt = findViewById(R.id.editarDireccionEditText);
        direccionCont = findViewById(R.id.editarDireccionCont);

        cpLayout = findViewById(R.id.editarCPLayout);
        cpTv = findViewById(R.id.editarCPTextView);
        cpEt = findViewById(R.id.editarCPEditText);
        cpCont = findViewById(R.id.editarCPCont);

        nombreTv = findViewById(R.id.editarNombreTextView);
        nombreEt = findViewById(R.id.editarNombreEditText);
        nombreCont = findViewById(R.id.editarNombreCont);

        apellidosTv = findViewById(R.id.editarApellidosTextView);
        apellidosEt = findViewById(R.id.editarApellidosEditText);
        apellidosCont = findViewById(R.id.editarApellidosCont);

        edadTv = findViewById(R.id.editarEdadTextView);
        edadEt = findViewById(R.id.editarEdadEditText);
        edadCont = findViewById(R.id.editarEdadCont);

        //Preferencias
        SharedPreferences preferences = this.getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);

        //Firebase
        referencia = Utilidades.creaClave(mp.cargarPreferencias("KEY_EMAIL"));
        StorageRef = FirebaseStorage.getInstance().getReference(referencia);
        DatabaseRef = FirebaseDatabase.getInstance().getReference(referencia);

        //Foto de perfil
        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        fotoPerfil.setImageDrawable(roundedImagen);

        //Base de datos
        databaseHelper = new DatabaseHelper(this);
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));

        //Información de la BBDD
        descripcionTv.setText(usuario.getDescripcion());
        experienciaTv.setText(usuario.getExperiencia());
        ciudadTv.setText(usuario.getCiudad());
        localidadTv.setText(usuario.getLocalidad());
        direccionTv.setText(usuario.getDireccion());
        cpTv.setText(usuario.getCodigoPostal());
        nombreTv.setText(usuario.getNombre());
        apellidosTv.setText(usuario.getApellidos());
        edadTv.setText(usuario.getEdad());

    }

    private void iniciarListeners() {

        elegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subidaTask != null && subidaTask.isInProgress()) {
                    Toast.makeText(Editar.this, "Ya se está subiendo una foto", Toast.LENGTH_LONG).show();
                } else {
                    subirImagen();
                }
            }
        });

        //Nombre
        nombreEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreTv.setVisibility(View.GONE);
                nombreEditar.setVisibility(View.GONE);
                nombreGuardar.setVisibility(View.VISIBLE);
                nombreEt.setVisibility(View.VISIBLE);
                nombreEt.setText(usuario.getNombre());
                nombreCont.setVisibility(View.VISIBLE);
                nombreCont.setText("30/" + usuario.getNombre().length());
                nombreEt.addTextChangedListener(editarWatcherNombre);
            }
        });
        nombreGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreEt.getText().toString().isEmpty()) {
                    if (nombreEt.getText().toString().compareTo(nombreTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("nombre", nombreEt.getText().toString());
                        nombreEt.setVisibility(View.GONE);
                        nombreGuardar.setVisibility(View.GONE);
                        nombreEditar.setVisibility(View.VISIBLE);
                        nombreCont.setVisibility(View.GONE);
                        nombreTv.setVisibility(View.VISIBLE);
                        nombreTv.setText(nombreEt.getText().toString());
                        Alerter.create(Editar.this)
                                .setTitle("Editado")
                                .setText("Editado con éxito")
                                .show();
                    } else {
                        nombreEt.setVisibility(View.GONE);
                        nombreGuardar.setVisibility(View.GONE);
                        nombreEditar.setVisibility(View.VISIBLE);
                        nombreCont.setVisibility(View.GONE);
                        nombreTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Apellidos
        apellidosEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apellidosTv.setVisibility(View.GONE);
                apellidosEditar.setVisibility(View.GONE);
                apellidosGuardar.setVisibility(View.VISIBLE);
                apellidosEt.setVisibility(View.VISIBLE);
                apellidosEt.setText(usuario.getApellidos());
                apellidosCont.setVisibility(View.VISIBLE);
                apellidosCont.setText("30/" + usuario.getApellidos().length());
                apellidosEt.addTextChangedListener(editarWatcherApellidos);
            }
        });
        apellidosGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!apellidosEt.getText().toString().isEmpty()) {
                    if (apellidosEt.getText().toString().compareTo(apellidosTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("apellidos", apellidosEt.getText().toString());
                        apellidosEt.setVisibility(View.GONE);
                        apellidosGuardar.setVisibility(View.GONE);
                        apellidosEditar.setVisibility(View.VISIBLE);
                        apellidosCont.setVisibility(View.GONE);
                        apellidosTv.setVisibility(View.VISIBLE);
                        apellidosTv.setText(apellidosEt.getText().toString());
                    } else {
                        apellidosEt.setVisibility(View.GONE);
                        apellidosGuardar.setVisibility(View.GONE);
                        apellidosEditar.setVisibility(View.VISIBLE);
                        apellidosCont.setVisibility(View.GONE);
                        apellidosTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Edad
        edadEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edadTv.setVisibility(View.GONE);
                edadEditar.setVisibility(View.GONE);
                edadGuardar.setVisibility(View.VISIBLE);
                edadEt.setVisibility(View.VISIBLE);
                edadEt.setText(usuario.getEdad());
                edadCont.setVisibility(View.VISIBLE);
                edadCont.setText("3/" + usuario.getEdad().length());
                edadEt.addTextChangedListener(editarWatcherEdad);
            }
        });
        edadGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edad = edadEt.getText().toString();
                if (!edad.isEmpty()) {
                    Integer iedad = Integer.parseInt(edad);
                    if (iedad < 120 && iedad >= 18) {
                        if (edadEt.getText().toString().compareTo(edadTv.getText().toString()) != 0) {
                            new EditarCampoActivity(Editar.this, usuario).execute("edad", edadEt.getText().toString());
                            edadEt.setVisibility(View.GONE);
                            edadGuardar.setVisibility(View.GONE);
                            edadEditar.setVisibility(View.VISIBLE);
                            edadCont.setVisibility(View.GONE);
                            edadTv.setVisibility(View.VISIBLE);
                            edadTv.setText(edadEt.getText().toString());
                        } else {
                            edadEt.setVisibility(View.GONE);
                            edadGuardar.setVisibility(View.GONE);
                            edadEditar.setVisibility(View.VISIBLE);
                            edadCont.setVisibility(View.GONE);
                            edadTv.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(Editar.this, "La edad es incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Editar.this, "La edad no puede quedar vacía", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Descripción
        descripcionEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descripcionTv.setVisibility(View.GONE);
                descripcionEditar.setVisibility(View.GONE);
                descripcionGuardar.setVisibility(View.VISIBLE);
                descripcionEt.setVisibility(View.VISIBLE);
                descripcionEt.setText(usuario.getDescripcion());
                descripcionCont.setVisibility(View.VISIBLE);
                descripcionCont.setText("300/" + usuario.getDescripcion().length());
                descripcionEt.addTextChangedListener(editarWatcherDescripcion);
            }
        });
        descripcionGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descripcionEt.getText().toString().compareTo(descripcionTv.getText().toString()) != 0) {
                    new EditarCampoActivity(Editar.this, usuario).execute("descripcion", descripcionEt.getText().toString());
                    descripcionEt.setVisibility(View.GONE);
                    descripcionGuardar.setVisibility(View.GONE);
                    descripcionEditar.setVisibility(View.VISIBLE);
                    descripcionCont.setVisibility(View.GONE);
                    descripcionTv.setVisibility(View.VISIBLE);
                    descripcionTv.setText(descripcionEt.getText().toString());
                } else {
                    descripcionEt.setVisibility(View.GONE);
                    descripcionGuardar.setVisibility(View.GONE);
                    descripcionEditar.setVisibility(View.VISIBLE);
                    descripcionCont.setVisibility(View.GONE);
                    descripcionTv.setVisibility(View.VISIBLE);
                }
            }
        });

        //Experiencia
        if (usuario.getTipoPerfil().compareTo("Proveedor") == 0) {
            experienciaEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    experienciaTv.setVisibility(View.GONE);
                    experienciaEditar.setVisibility(View.GONE);
                    experienciaGuardar.setVisibility(View.VISIBLE);
                    experienciaEt.setVisibility(View.VISIBLE);
                    experienciaEt.setText(usuario.getExperiencia());
                    experienciaCont.setVisibility(View.VISIBLE);
                    experienciaCont.setText("300/" + usuario.getExperiencia().length());
                    experienciaEt.addTextChangedListener(editarWatcherExperiencia);
                }
            });
            experienciaGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (experienciaEt.getText().toString().compareTo(experienciaTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("experiencia", experienciaEt.getText().toString());
                        experienciaEt.setVisibility(View.GONE);
                        experienciaGuardar.setVisibility(View.GONE);
                        experienciaEditar.setVisibility(View.VISIBLE);
                        experienciaCont.setVisibility(View.GONE);
                        experienciaTv.setVisibility(View.VISIBLE);
                        experienciaTv.setText(experienciaEt.getText().toString());
                    } else {
                        experienciaEt.setVisibility(View.GONE);
                        experienciaGuardar.setVisibility(View.GONE);
                        experienciaEditar.setVisibility(View.VISIBLE);
                        experienciaCont.setVisibility(View.GONE);
                        experienciaTv.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            experienciaLayout.setVisibility(View.GONE);
        }
        //Ciudad
        ciudadEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ciudadTv.setVisibility(View.GONE);
                ciudadEditar.setVisibility(View.GONE);
                ciudadGuardar.setVisibility(View.VISIBLE);
                ciudadEt.setVisibility(View.VISIBLE);
                ciudadEt.setText(usuario.getCiudad());
                ciudadCont.setVisibility(View.VISIBLE);
                ciudadCont.setText("50/" + usuario.getCiudad().length());
                ciudadEt.addTextChangedListener(editarWatcherCiudad);
            }
        });
        ciudadGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ciudadEt.getText().toString().isEmpty()) {
                    if (ciudadEt.getText().toString().compareTo(ciudadTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("ciudad", ciudadEt.getText().toString());
                        ciudadEt.setVisibility(View.GONE);
                        ciudadGuardar.setVisibility(View.GONE);
                        ciudadEditar.setVisibility(View.VISIBLE);
                        ciudadCont.setVisibility(View.GONE);
                        ciudadTv.setVisibility(View.VISIBLE);
                        ciudadTv.setText(ciudadEt.getText().toString());
                    } else {
                        ciudadEt.setVisibility(View.GONE);
                        ciudadGuardar.setVisibility(View.GONE);
                        ciudadEditar.setVisibility(View.VISIBLE);
                        ciudadCont.setVisibility(View.GONE);
                        ciudadTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Localidad
        localidadEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localidadTv.setVisibility(View.GONE);
                localidadEditar.setVisibility(View.GONE);
                localidadGuardar.setVisibility(View.VISIBLE);
                localidadEt.setVisibility(View.VISIBLE);
                localidadEt.setText(usuario.getLocalidad());
                localidadCont.setVisibility(View.VISIBLE);
                localidadCont.setText("50/" + usuario.getLocalidad().length());
                localidadEt.addTextChangedListener(editarWatcherLocalidad);
            }
        });
        localidadGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!localidadEt.getText().toString().isEmpty()) {
                    if (localidadEt.getText().toString().compareTo(localidadTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("localidad", localidadEt.getText().toString());
                        localidadEt.setVisibility(View.GONE);
                        localidadGuardar.setVisibility(View.GONE);
                        localidadEditar.setVisibility(View.VISIBLE);
                        localidadCont.setVisibility(View.GONE);
                        localidadTv.setVisibility(View.VISIBLE);
                        localidadTv.setText(localidadEt.getText().toString());
                    } else {
                        localidadEt.setVisibility(View.GONE);
                        localidadGuardar.setVisibility(View.GONE);
                        localidadEditar.setVisibility(View.VISIBLE);
                        localidadCont.setVisibility(View.GONE);
                        localidadTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Direccion
        if (usuario.getTipoPerfil().compareTo("Solicitante") == 0) {
            direccionEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    direccionTv.setVisibility(View.GONE);
                    direccionEditar.setVisibility(View.GONE);
                    direccionGuardar.setVisibility(View.VISIBLE);
                    direccionEt.setVisibility(View.VISIBLE);
                    direccionEt.setText(usuario.getDireccion());
                    direccionCont.setVisibility(View.VISIBLE);
                    direccionCont.setText("100/" + usuario.getDireccion().length());
                    direccionEt.addTextChangedListener(editarWatcherDireccion);
                }
            });
        } else {
            direccionLayout.setVisibility(View.GONE);
        }
        direccionGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!direccionEt.getText().toString().isEmpty()) {
                    if (direccionEt.getText().toString().compareTo(direccionTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("direccion", direccionEt.getText().toString());
                        direccionEt.setVisibility(View.GONE);
                        direccionGuardar.setVisibility(View.GONE);
                        direccionEditar.setVisibility(View.VISIBLE);
                        direccionCont.setVisibility(View.GONE);
                        direccionTv.setVisibility(View.VISIBLE);
                        direccionTv.setText(direccionEt.getText().toString());
                    } else {
                        direccionEt.setVisibility(View.GONE);
                        direccionGuardar.setVisibility(View.GONE);
                        direccionEditar.setVisibility(View.VISIBLE);
                        direccionCont.setVisibility(View.GONE);
                        direccionTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Código postal
        if (usuario.getTipoPerfil().compareTo("Solicitante") == 0) {
            cpEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cpTv.setVisibility(View.GONE);
                    cpEditar.setVisibility(View.GONE);
                    cpGuardar.setVisibility(View.VISIBLE);
                    cpEt.setVisibility(View.VISIBLE);
                    cpEt.setText(usuario.getCodigoPostal());
                    cpCont.setVisibility(View.VISIBLE);
                    cpCont.setText("5/" + usuario.getCodigoPostal().length());
                    cpEt.addTextChangedListener(editarWatcherCP);
                }
            });
        } else {
            cpLayout.setVisibility(View.GONE);
        }
        cpGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cpEt.getText().toString().isEmpty()) {
                    if (cpEt.getText().toString().compareTo(cpTv.getText().toString()) != 0) {
                        new EditarCampoActivity(Editar.this, usuario).execute("codigo_postal", cpEt.getText().toString());
                        cpEt.setVisibility(View.GONE);
                        cpGuardar.setVisibility(View.GONE);
                        cpEditar.setVisibility(View.VISIBLE);
                        cpCont.setVisibility(View.GONE);
                        cpTv.setVisibility(View.VISIBLE);
                        cpTv.setText(cpEt.getText().toString());
                    } else {
                        cpEt.setVisibility(View.GONE);
                        cpGuardar.setVisibility(View.GONE);
                        cpEditar.setVisibility(View.VISIBLE);
                        cpCont.setVisibility(View.GONE);
                        cpTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    private TextWatcher editarWatcherNombre = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nombre = nombreEt.getText().toString();
            nombreCont.setText("30/" + nombre.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherApellidos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String apellidos = apellidosEt.getText().toString();
            apellidosCont.setText("30/" + apellidos.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherEdad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String edad = edadEt.getText().toString();
            edadCont.setText("3/" + edad.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherDescripcion = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String descripcion = descripcionEt.getText().toString();
            descripcionCont.setText("300/" + descripcion.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherExperiencia = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String experiencia = experienciaEt.getText().toString();
            experienciaCont.setText("300/" + experiencia.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherCiudad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String ciudad = ciudadEt.getText().toString();
            ciudadCont.setText("50/" + ciudad.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherLocalidad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String localidad = localidadEt.getText().toString();
            localidadCont.setText("50/" + localidad.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherDireccion = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String direccion = direccionEt.getText().toString();
            direccionCont.setText("100/" + direccion.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher editarWatcherCP = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String cp = cpEt.getText().toString();
            cpCont.setText("5/" + cp.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            fotoUri = data.getData();

            Picasso.with(this).load(fotoUri).into(fotoPerfil);
        }
    }

    private void subirImagen() {

        if (fotoUri != null) {
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getExtension(fotoUri));

            subidaTask = fileReference.putFile(fotoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    barraProgreso.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Editar.this, "Subida exitosa", Toast.LENGTH_LONG).show();
                            Imagen img = new Imagen(referencia, taskSnapshot.getDownloadUrl().toString());
                            String imgid = DatabaseRef.push().getKey();
                            DatabaseRef.child(imgid).setValue(img);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Editar.this, "No se ha podido subir", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progreso = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            barraProgreso.setProgress((int) progreso);
                        }
                    });
        } else {
            Toast.makeText(this, "No se ha seleccionado ningún archivo", Toast.LENGTH_LONG).show();
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
/*
    public void showAlerter(View v){

        Alerter.create(this)
                .setTitle("Editado")
                .setText("Editado con éxito")
                .show();
    }
*/
}
