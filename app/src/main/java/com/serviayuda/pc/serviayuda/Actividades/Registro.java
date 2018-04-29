package com.serviayuda.pc.serviayuda.Actividades;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.serviayuda.pc.serviayuda.Activitys.RegistroActivity;
import com.serviayuda.pc.serviayuda.Objetos.Imagen;
import com.serviayuda.pc.serviayuda.Objetos.RoundedCornerTransformation;
import com.serviayuda.pc.serviayuda.Objetos.Utilidades;
import com.serviayuda.pc.serviayuda.R;
import com.squareup.picasso.Picasso;

/**
 * Created by PC on 06/02/2018.
 */

public class Registro extends AppCompatActivity {

    Button botonFinalizar, elegirFoto;
    EditText campoNombre, campoApellidos, campoEdad, campoEmail, campoPassword1, campoPassword2, campoCiudad, campoLocalidad, campoCodigoPostal,
            campoCalle, campoNumero, campoBloque, campoPiso;
    TextView avisoNombre, avisoApellidos, avisoEdad, avisoEmail, avisoPassword1, avisoPassword2, avisoCiudad, avisoLocalidad, avisoCodigoPostal, campoResultado;
    CheckBox proveedor, solicitante;
    ImageView tickNombre, tickApellidos, tickEdad, tickEmail, tickPassword1, tickPassword2, tickCiudad, tickLocalidad, tickCalle, tickNumero,
            tickBloque, tickPiso, tickCodigoPostal, imagenPerfil;
    RoundedBitmapDrawable imagenTick;
    LinearLayout layoutDireccion, layoutCodigoPostal, layoutTipoServicio;
    Spinner spinnerTipos;
    AdapterView.OnItemSelectedListener spinnerListenerTipos;
    Uri fotoUri;

    boolean bProveedor = false, bSolicitante = false;

    //Firebase
    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;
    private StorageTask subidaTask;
    String referencia;


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
        avisoPassword1 = findViewById(R.id.registroAvisoPassword1);
        avisoPassword2 = findViewById(R.id.registroAvisoPassword2);
        avisoCiudad = findViewById(R.id.registroAvisoCiudad);
        avisoLocalidad = findViewById(R.id.registroAvisoLocalidad);
        avisoCodigoPostal = findViewById(R.id.registroAvisoCodigoPostal);

        //Layouts
        layoutDireccion = findViewById(R.id.registroDireccionLayout);
        layoutCodigoPostal = findViewById(R.id.registroCodigoPostalLayout);
        layoutTipoServicio = findViewById(R.id.registroTipoServicioLayout);

        //Ticks

        tickNombre = findViewById(R.id.registroTickNombre);
        tickApellidos = findViewById(R.id.registroTickApellidos);
        tickEdad = findViewById(R.id.registroTickEdad);
        tickEmail = findViewById(R.id.registroTickEmail);
        tickPassword1 = findViewById(R.id.registroTickPassword1);
        tickPassword2 = findViewById(R.id.registroTickPassword2);
        tickCiudad = findViewById(R.id.registroTickCiudad);
        tickLocalidad = findViewById(R.id.registroTickLocalidad);
        tickCalle = findViewById(R.id.registroTickCalle);
        tickNumero = findViewById(R.id.registroTickNumero);
        tickBloque = findViewById(R.id.registroTickBloque);
        tickPiso = findViewById(R.id.registroTickPiso);
        tickCodigoPostal = findViewById(R.id.registroTickCodigoPostal);

        //Imagen
        imagenPerfil = findViewById(R.id.editarImagenPerfilRegistro);
        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphotoprofile);
        RoundedBitmapDrawable roundedImagen = RoundedBitmapDrawableFactory.create(getResources(), foto);
        roundedImagen.setCornerRadius(200);
        imagenPerfil.setImageDrawable(roundedImagen);

        //Botón
        elegirFoto = findViewById(R.id.elegirFotoPerfilRegistro);

        //Checkboxs
        proveedor = findViewById(R.id.registroPrestar);
        solicitante = findViewById(R.id.registroSolicitar);

        //Tick imagen
        Bitmap bitmaptick = BitmapFactory.decodeResource(getResources(), R.drawable.tickverde);
        imagenTick = RoundedBitmapDrawableFactory.create(getResources(), bitmaptick);

        //Spinner
        spinnerTipos = findViewById(R.id.spinnerTipoServicio);
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(Registro.this, R.array.spinnerTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipos.setAdapter(adapterTipos);
        spinnerTipos.setOnItemSelectedListener(spinnerListenerTipos);


        if(!campoEmail.getText().toString().isEmpty()){
            referencia = Utilidades.creaClave(campoEmail.getText().toString());
            StorageRef = FirebaseStorage.getInstance().getReference(referencia);
            DatabaseRef = FirebaseDatabase.getInstance().getReference(referencia);
        }


    }

    private void iniciarListeners() {
        botonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizar();
            }
        });

        //Checkboxs
        proveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitante.setChecked(false);
                layoutDireccion.setVisibility(View.GONE);
                layoutCodigoPostal.setVisibility(View.GONE);
                layoutTipoServicio.setVisibility(View.VISIBLE);
                bProveedor = true;
                bSolicitante = false;
            }
        });
        solicitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proveedor.setChecked(false);
                layoutDireccion.setVisibility(View.VISIBLE);
                layoutCodigoPostal.setVisibility(View.VISIBLE);
                layoutTipoServicio.setVisibility(View.GONE);
                bProveedor = false;
                bSolicitante = true;
            }
        });

        //Botones
        elegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });

        //Spinner
        spinnerListenerTipos = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        //TextWatchers
        campoNombre.addTextChangedListener(registroWatcherNombre);
        campoApellidos.addTextChangedListener(registroWatcherApellidos);
        campoEdad.addTextChangedListener(registroWatcherEdad);
        campoEmail.addTextChangedListener(registroWatcherEmail);
        campoPassword1.addTextChangedListener(registroWatcherPassword);
        campoPassword2.addTextChangedListener(registroWatcherPassword);
        campoCiudad.addTextChangedListener(registroWatcherCiudad);
        campoLocalidad.addTextChangedListener(registroWatcherLocalidad);
        campoCalle.addTextChangedListener(registroWatcherCalle);
        campoNumero.addTextChangedListener(registroWatcherNumero);
        campoBloque.addTextChangedListener(registroWatcherBloque);
        campoPiso.addTextChangedListener(registroWatcherPiso);
        campoCodigoPostal.addTextChangedListener(registroWatcherCodigoPostal);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            fotoUri = data.getData();

            Picasso.with(Registro.this)
                    .load(fotoUri)
                    .placeholder(R.drawable.defaultphotoprofile)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedCornerTransformation())
                    .into(imagenPerfil);
        }
    }
    private void subirImagen(){

        if(fotoUri != null){
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getExtension(fotoUri));

            subidaTask = fileReference.putFile(fotoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Registro.this, "Subida exitosa", Toast.LENGTH_LONG).show();
                            Imagen img = new Imagen(referencia, taskSnapshot.getDownloadUrl().toString());
                            String imgid = DatabaseRef.push().getKey();
                            DatabaseRef.child(imgid).setValue(img);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registro.this, "No se ha podido subir", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(this, "No se ha seleccionado ningún archivo", Toast.LENGTH_LONG).show();
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
            String regularExpresion = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            String email = campoEmail.getText().toString();
            email = email.toLowerCase();
            Integer longitudEmail = email.length();
            String dominioCom = "", dominioEs = "";

            if (longitudEmail > 3) {
                dominioCom = email.substring(longitudEmail - 3);
                dominioEs = email.substring(longitudEmail - 2);
            }


            if (email.matches(regularExpresion) && (dominioCom.matches("com") || dominioEs.matches("es"))) {

                avisoEmail.setText("");
                avisoEmail.setVisibility(View.GONE);
                tickEmail.setImageDrawable(imagenTick);
            } else {
                avisoEmail.setText("El correo no es correcto o no está completo");
                avisoEmail.setTextColor(Color.RED);
                avisoEmail.setVisibility(View.VISIBLE);
                tickEmail.setImageResource(0);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String password1 = campoPassword1.getText().toString();
            String password2 = campoPassword2.getText().toString();
            Integer longPass1 = password1.length();
            Integer longPass2 = password2.length();

            if (!password1.isEmpty() && !password2.isEmpty()) {
                if (longPass1 == longPass2) {
                    if (password1.compareTo(password2) == 0) {
                        avisoPassword1.setText("");
                        avisoPassword1.setVisibility(View.GONE);
                        avisoPassword2.setText("");
                        avisoPassword2.setVisibility(View.GONE);
                        tickPassword1.setImageDrawable(imagenTick);
                        tickPassword2.setImageDrawable(imagenTick);
                    } else {
                        avisoPassword1.setText("Las contraseñas no coinciden");
                        avisoPassword1.setTextColor(Color.RED);
                        avisoPassword1.setVisibility(View.VISIBLE);
                        avisoPassword2.setText("Las contraseñas no coinciden");
                        avisoPassword2.setTextColor(Color.RED);
                        avisoPassword2.setVisibility(View.VISIBLE);
                        tickPassword1.setImageResource(0);
                        tickPassword2.setImageResource(0);
                    }
                } else {
                    avisoPassword1.setText("Las contraseñas no coinciden");
                    avisoPassword1.setTextColor(Color.RED);
                    avisoPassword1.setVisibility(View.VISIBLE);
                    avisoPassword2.setText("Las contraseñas no coinciden");
                    avisoPassword2.setTextColor(Color.RED);
                    avisoPassword2.setVisibility(View.VISIBLE);
                    tickPassword1.setImageResource(0);
                    tickPassword2.setImageResource(0);
                }
            } else {
                avisoPassword1.setText("");
                avisoPassword1.setVisibility(View.GONE);
                avisoPassword2.setText("");
                avisoPassword2.setVisibility(View.GONE);
                tickPassword1.setImageResource(0);
                tickPassword2.setImageResource(0);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherCiudad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String ciudad = campoCiudad.getText().toString();
            if(ciudad.isEmpty()){
                avisoCiudad.setText("Este campo no puede quedar vacío");
                avisoCiudad.setTextColor(Color.RED);
                avisoCiudad.setVisibility(View.VISIBLE);
                tickCiudad.setImageResource(0);
            } else {
                avisoCiudad.setText("");
                avisoCiudad.setVisibility(View.GONE);
                tickCiudad.setImageDrawable(imagenTick);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherLocalidad = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String localidad = campoLocalidad.getText().toString();
            if(localidad.isEmpty()){
                avisoLocalidad.setText("Este campo no puede quedar vacío");
                avisoLocalidad.setTextColor(Color.RED);
                avisoLocalidad.setVisibility(View.VISIBLE);
                tickLocalidad.setImageResource(0);
            } else {
                avisoLocalidad.setText("");
                avisoLocalidad.setVisibility(View.GONE);
                tickLocalidad.setImageDrawable(imagenTick);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherCodigoPostal = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String codigoPostal = campoCodigoPostal.getText().toString();
            if (codigoPostal.isEmpty()) {
                avisoCodigoPostal.setText("Este campo no puede quedar vacío");
                avisoCodigoPostal.setTextColor(Color.RED);
                avisoCodigoPostal.setVisibility(View.VISIBLE);
                tickCodigoPostal.setImageResource(0);
            } else if(codigoPostal.length() == 5){
                avisoCodigoPostal.setText("");
                avisoCodigoPostal.setVisibility(View.GONE);
                tickCodigoPostal.setImageDrawable(imagenTick);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherCalle = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String calle = campoCalle.getText().toString();
            if(!calle.isEmpty()){
                tickCalle.setVisibility(View.VISIBLE);
                tickCalle.setImageDrawable(imagenTick);
            }else{
                tickCalle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherNumero = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String numero = campoNumero.getText().toString();
            if(!numero.isEmpty()){
                tickNumero.setVisibility(View.VISIBLE);
                tickNumero.setImageDrawable(imagenTick);
            }else{
                tickNumero.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherBloque = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String bloque = campoBloque.getText().toString();
            if(!bloque.isEmpty()){
                tickBloque.setVisibility(View.VISIBLE);
                tickBloque.setImageDrawable(imagenTick);
            }else{
                tickBloque.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher registroWatcherPiso = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String piso = campoPiso.getText().toString();
            if(!piso.isEmpty()){
                tickPiso.setVisibility(View.VISIBLE);
                tickPiso.setImageDrawable(imagenTick);
            }else{
                tickPiso.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };



    private void finalizar() {
        String nombre = campoNombre.getText().toString();
        String apellidos = campoApellidos.getText().toString();
        String edad = campoEdad.getText().toString();
        String email = campoEmail.getText().toString();
        String password = campoPassword1.getText().toString();
        String ciudad = campoCiudad.getText().toString();
        String localidad = campoLocalidad.getText().toString();
        String calle = campoCalle.getText().toString();
        String numero = campoNumero.getText().toString();
        String bloque = campoBloque.getText().toString();
        String piso = campoPiso.getText().toString();
        String codigoPostal = campoCodigoPostal.getText().toString();
        String tipoServicio = spinnerTipos.getSelectedItem().toString();

        if(bProveedor){
            if(nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || email.isEmpty() || password.isEmpty() || ciudad.isEmpty() || localidad.isEmpty() || tipoServicio.compareTo("-- Seleccione un servicio --")==0){
                campoResultado.setVisibility(View.VISIBLE);
                campoResultado.setText("Hay algún campo incompleto");
                campoResultado.setTextColor(Color.RED);
            }else{
                //Activity que envía los datos del proveedor
                campoResultado.setVisibility(View.GONE);
            }
        }else if(bSolicitante){
            if(nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || email.isEmpty() || password.isEmpty() || ciudad.isEmpty() || localidad.isEmpty() || calle.isEmpty()
                    || numero.isEmpty() || bloque.isEmpty() || piso.isEmpty() || codigoPostal.isEmpty()){
                campoResultado.setVisibility(View.VISIBLE);
                campoResultado.setText("Hay algún campo incompleto");
                campoResultado.setTextColor(Color.RED);
            }else{
                //Activity que envía los datos del solicitante
                campoResultado.setVisibility(View.GONE);
            }
        }
        //new RegistroActivity(this, campoResultado).execute(nombre, email, password);
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