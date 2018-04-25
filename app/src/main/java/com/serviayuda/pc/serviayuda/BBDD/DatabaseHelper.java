package com.serviayuda.pc.serviayuda.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 27/02/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Versión de la BBDD
    private static final int DATABASE_VERSION = 1;
    //Nombre de la BBDD
    private static final String DATABASE_NAME = "UserManager.db";
    //Nombre de la tabla
    private static final String TABLE_ANUNCIO = "tabla_anuncio";
    private static final String TABLE_USUARIO = "tabla_usuario";
    private static final String TABLE_SOLICITUDES = "tabla_solicitudes";

    //Columnas de la tabla usuario
    private static final String COLUMN_USUARIO_NOMBRE = "nombre";
    private static final String COLUMN_USUARIO_APELLIDOS = "apellidos";
    private static final String COLUMN_USUARIO_EMAIL = "email";
    private static final String COLUMN_USUARIO_VERIFICADO = "verificado";
    private static final String COLUMN_USUARIO_TIPOPERFIL = "tipo_perfil";
    private static final String COLUMN_USUARIO_TIPOSERVICIO = "tipo_servicio";
    private static final String COLUMN_USUARIO_CIUDAD = "ciudad";
    private static final String COLUMN_USUARIO_LOCALIDAD = "localidad";
    private static final String COLUMN_USUARIO_DIRECCION = "direccion";
    private static final String COLUMN_USUARIO_CODIGOPOSTAL = "codigo_postal";
    private static final String COLUMN_USUARIO_DESCRIPCION = "descripcion";
    private static final String COLUMN_USUARIO_EXPERIENCIA = "experiencia";
    private static final String COLUMN_USUARIO_HORARIO = "horario";
    private static final String COLUMN_USUARIO_EDAD = "edad";

    //Columnas de la tabla anuncio
    private static final String COLUMN_ANUNCIO_NOMBRE = "nombre";
    private static final String COLUMN_ANUNCIO_EMAIL = "email";
    private static final String COLUMN_ANUNCIO_TIPOANUNCIO = "tipo_anuncio";
    private static final String COLUMN_ANUNCIO_DESCRIPCION = "anuncio";
    private static final String COLUMN_ANUNCIO_HORAS = "horas";
    private static final String COLUMN_ANUNCIO_HORADESEADA = "hora_deseada";
    private static final String COLUMN_ANUNCIO_ESTADO = "estado";

    //Columnas de la tabla solicitudes
    private static final String COLUMN_SOLICITUDES_EMAILSOLICITANTE = "email_solicitante";
    private static final String COLUMN_SOLICITUDES_EMAILPROVEEDOR = "email_proveedor";
    private static final String COLUMN_SOLICITUDES_TIPOANUNCIO = "tipo_anuncio";
    private static final String COLUMN_SOLICITUDES_ESTADO = "estado";
    private static final String COLUMN_SOLICITUDES_CLIENTECHECK = "clientecheck";
    private static final String COLUMN_SOLICITUDES_PROVEEDORCHECK = "proveedorcheck";

    //Creación de la tabla anuncio
    private String CREATE_ANUNCIO_TABLE = "CREATE TABLE " + TABLE_ANUNCIO + " ( "
            + COLUMN_ANUNCIO_NOMBRE + " TEXT, " + COLUMN_ANUNCIO_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_ANUNCIO_TIPOANUNCIO + " TEXT, "
            + COLUMN_ANUNCIO_DESCRIPCION + " TEXT, " + COLUMN_ANUNCIO_HORAS + " TEXT, " + COLUMN_ANUNCIO_HORADESEADA + " TEXT, "
            + COLUMN_ANUNCIO_ESTADO + " TEXT" + " )";
    //Creación de la tabla usuario
    private String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + " ( " + COLUMN_USUARIO_NOMBRE + " TEXT, "
            + COLUMN_USUARIO_APELLIDOS + " TEXT, " + COLUMN_USUARIO_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_USUARIO_VERIFICADO + " TEXT, "
            + COLUMN_USUARIO_TIPOPERFIL + " TEXT, " + COLUMN_USUARIO_TIPOSERVICIO + " TEXT, " + COLUMN_USUARIO_CIUDAD + " TEXT, "
            + COLUMN_USUARIO_LOCALIDAD + " TEXT, " + COLUMN_USUARIO_DIRECCION + " TEXT, " + COLUMN_USUARIO_CODIGOPOSTAL + " TEXT, "
            + COLUMN_USUARIO_DESCRIPCION + " TEXT, " + COLUMN_USUARIO_EXPERIENCIA + " TEXT, " + COLUMN_USUARIO_HORARIO + " TEXT, "
            + COLUMN_USUARIO_EDAD + " TEXT" + " )";

    //Creación de la tabla solicitudes
    private String CREATE_SOLICITUDES_TABLE = "CREATE TABLE " + TABLE_SOLICITUDES + " ( " + COLUMN_SOLICITUDES_EMAILSOLICITANTE + " TEXT, "
            + COLUMN_SOLICITUDES_EMAILPROVEEDOR + " TEXT, " + COLUMN_SOLICITUDES_TIPOANUNCIO + " TEXT, " + COLUMN_SOLICITUDES_ESTADO + " TEXT, "
            + COLUMN_SOLICITUDES_CLIENTECHECK + " TEXT, " + COLUMN_SOLICITUDES_PROVEEDORCHECK + " TEXT" + " )";

    //Eliminación de la tabla anuncio
    private String DROP_ANUNCIO_TABLE = "DROP TABLE IF EXISTS " + TABLE_ANUNCIO;

    //Eliminación de la tabla usuario
    private String DROP_USUARIO_TABLE = "DROP TABLE IF EXISTS " + TABLE_USUARIO;

    //Eliminación de la tabla solicitudes
    private String DROP_SOLICITUDES_TABLE = "DROP TABLE IF EXISTS " + TABLE_SOLICITUDES;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ANUNCIO_TABLE);
        db.execSQL(CREATE_USUARIO_TABLE);
        db.execSQL(CREATE_SOLICITUDES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la tabla si existe
        db.execSQL(DROP_ANUNCIO_TABLE);
        db.execSQL(DROP_USUARIO_TABLE);
        db.execSQL(DROP_SOLICITUDES_TABLE);
        //Se crea la tabla de nuevo
        onCreate(db);
    }

    //ANUNCIOS

    //Comprueba si se ha insertado un anuncio en la tabla anuncio para
    //un determinado usuario
    public boolean checkEmailAnuncio(String email) {
        String[] columns = {
                COLUMN_ANUNCIO_NOMBRE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ANUNCIO_EMAIL + " =?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_ANUNCIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //[POSIBLEMENTE SE ELIMINE]
    public boolean checkEmail(String email) {
        String[] columns = {
                COLUMN_ANUNCIO_TIPOANUNCIO
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ANUNCIO_EMAIL + " =?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_ANUNCIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Añade un anuncio a la base de datos
    public void addAnuncio(Anuncio anuncio) {
        String email = anuncio.getEmail();
        Boolean check = checkEmailAnuncio(email);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ANUNCIO_NOMBRE, anuncio.getNombre());
        values.put(COLUMN_ANUNCIO_EMAIL, anuncio.getEmail());
        values.put(COLUMN_ANUNCIO_TIPOANUNCIO, anuncio.getTipoAnuncio());
        values.put(COLUMN_ANUNCIO_DESCRIPCION, anuncio.getDescripcion());
        values.put(COLUMN_ANUNCIO_HORAS, anuncio.getHoras());
        values.put(COLUMN_ANUNCIO_HORADESEADA, anuncio.getHoraDeseada());
        values.put(COLUMN_ANUNCIO_ESTADO, anuncio.getEstado());


        if (check) { //Si existe un anuncio, actualiza
            String[] args = new String[]{email};
            db.update(TABLE_ANUNCIO, values, "email = ?", args);
        } else { //En caso contrario, inserta
            db.insert(TABLE_ANUNCIO, null, values);
        }
        db.close();
    }

    //Obtiene un anuncio de la base de datos
    public Anuncio getAnuncio(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Anuncio anuncio = new Anuncio();

        String[] columns = {COLUMN_ANUNCIO_NOMBRE,
                COLUMN_ANUNCIO_EMAIL,
                COLUMN_ANUNCIO_TIPOANUNCIO,
                COLUMN_ANUNCIO_DESCRIPCION,
                COLUMN_ANUNCIO_HORAS,
                COLUMN_ANUNCIO_HORADESEADA,
                COLUMN_ANUNCIO_ESTADO};
        String selection = COLUMN_ANUNCIO_EMAIL + " =?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_ANUNCIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToFirst();

            anuncio.setNombre(cursor.getString(0));
            anuncio.setEmail(cursor.getString(1));
            anuncio.setTipoAnuncio(cursor.getString(2));
            anuncio.setDescripcion(cursor.getString(3));
            anuncio.setHoras(cursor.getString(4));
            anuncio.setHoraDeseada(cursor.getString(5));
            anuncio.setEstado(cursor.getString(6));
            cursor.close();
            db.close();
            return anuncio;
        }
        else{
            cursor.close();
            db.close();
            return null;
        }


    }

    //Modifica el estado del anuncio
    public void modificaEstado(String email, String estado) {
        Boolean check = checkEmailAnuncio(email);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANUNCIO_ESTADO, estado);
        String[] args = new String[]{email};
        db.update(TABLE_ANUNCIO, values, "email =?", args);
        db.close();
    }
    public void setEliminaTodosLosAnuncios() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ANUNCIO, null, null);
        db.close();

    }

    //Método que devuelve los anuncios según el tipo indicando por parámetros
    //y también que estén libres
    public List<Anuncio> getAnunciosPorTipo(String tipo_anuncio) {
        String[] columns = {
                COLUMN_ANUNCIO_NOMBRE,
                COLUMN_ANUNCIO_EMAIL,
                COLUMN_ANUNCIO_TIPOANUNCIO,
                COLUMN_ANUNCIO_DESCRIPCION,
                COLUMN_ANUNCIO_HORAS,
                COLUMN_ANUNCIO_HORADESEADA
        };
        String sortOrder = COLUMN_ANUNCIO_NOMBRE + " ASC";
        List<Anuncio> anuncioList = new ArrayList<Anuncio>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ANUNCIO_TIPOANUNCIO + " =?" + " AND " + COLUMN_ANUNCIO_ESTADO + " =?";

        String[] selectionArgs = {tipo_anuncio, "Pendiente"};
        Cursor cursor = db.query(TABLE_ANUNCIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Anuncio anuncio = new Anuncio();
                anuncio.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_NOMBRE)));
                anuncio.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_EMAIL)));
                anuncio.setTipoAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_TIPOANUNCIO)));
                anuncio.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_DESCRIPCION)));
                anuncio.setHoras(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_HORAS)));
                anuncio.setHoraDeseada(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_HORADESEADA)));
                anuncioList.add(anuncio);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return anuncioList;
    }

    //Método que devuelve todos los anuncios. [POSIBLEMENTE SE ELIMINE]
    public List<Anuncio> getAllAnuncios() {
        String[] columns = {
                COLUMN_ANUNCIO_NOMBRE,
                COLUMN_ANUNCIO_EMAIL,
                COLUMN_ANUNCIO_TIPOANUNCIO,
                COLUMN_ANUNCIO_DESCRIPCION,
                COLUMN_ANUNCIO_HORAS,
                COLUMN_ANUNCIO_HORADESEADA
        };
        String sortOrder = COLUMN_ANUNCIO_NOMBRE + " ASC";
        List<Anuncio> anuncioList = new ArrayList<Anuncio>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ANUNCIO,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Anuncio anuncio = new Anuncio();
                anuncio.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_NOMBRE)));
                anuncio.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_EMAIL)));
                anuncio.setTipoAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_TIPOANUNCIO)));
                anuncio.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_DESCRIPCION)));
                anuncio.setHoras(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_HORAS)));
                anuncio.setHoraDeseada(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_HORADESEADA)));
                anuncioList.add(anuncio);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return anuncioList;
    }

    //USUARIOS

    //Comprueba la existencia de un usuario
    public boolean checkUsuario(String email) {
        String[] columns = {
                COLUMN_USUARIO_NOMBRE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USUARIO_EMAIL + " =?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USUARIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Añade un usuario a la base de datos
    public void addUsuario(Usuario usuario) {
        String email = usuario.getEmail();
        Boolean check = checkUsuario(email);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOMBRE, usuario.getNombre());
        values.put(COLUMN_USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(COLUMN_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMN_USUARIO_VERIFICADO, usuario.getVerificado());
        values.put(COLUMN_USUARIO_TIPOPERFIL, usuario.getTipoPerfil());
        values.put(COLUMN_USUARIO_TIPOSERVICIO, usuario.getTipoServicio());
        values.put(COLUMN_USUARIO_CIUDAD, usuario.getCiudad());
        values.put(COLUMN_USUARIO_LOCALIDAD, usuario.getLocalidad());
        values.put(COLUMN_USUARIO_DIRECCION, usuario.getDireccion());
        values.put(COLUMN_USUARIO_CODIGOPOSTAL, usuario.getCodigoPostal());
        values.put(COLUMN_USUARIO_DESCRIPCION, usuario.getDescripcion());
        values.put(COLUMN_USUARIO_EXPERIENCIA, usuario.getExperiencia());
        values.put(COLUMN_USUARIO_HORARIO, usuario.getHorario());
        values.put(COLUMN_USUARIO_EDAD, usuario.getEdad());

        if (check) { //Si existe el usuario, lo actualiza
            String[] args = new String[]{email};
            db.update(TABLE_USUARIO, values, "email = ?", args);
        } else { //En caso contrario, inserta el nuevo usuario
            db.insert(TABLE_USUARIO, null, values);
        }
        db.close();

    }

    //Obtiene un usuario de la base de datos
    public Usuario getUsuario(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Usuario us = new Usuario();

        String[] columns = {COLUMN_USUARIO_NOMBRE,
                COLUMN_USUARIO_APELLIDOS,
                COLUMN_USUARIO_EDAD,
                COLUMN_USUARIO_VERIFICADO,
                COLUMN_USUARIO_TIPOSERVICIO,
                COLUMN_USUARIO_TIPOPERFIL,
                COLUMN_USUARIO_EXPERIENCIA,
                COLUMN_USUARIO_DESCRIPCION,
                COLUMN_USUARIO_CIUDAD,
                COLUMN_USUARIO_LOCALIDAD,
                COLUMN_USUARIO_DIRECCION,
                COLUMN_USUARIO_CODIGOPOSTAL,
                COLUMN_USUARIO_HORARIO};
        String selection = COLUMN_USUARIO_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USUARIO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToFirst();

            us.setEmail(email);
            us.setNombre(cursor.getString(0));
            us.setApellidos(cursor.getString(1));
            us.setEdad(cursor.getString(2));
            us.setVerificado(cursor.getString(3));
            us.setTipoServicio(cursor.getString(4));
            us.setTipoPerfil(cursor.getString(5));
            us.setExperiencia(cursor.getString(6));
            us.setDescripcion(cursor.getString(7));
            us.setCiudad(cursor.getString(8));
            us.setLocalidad(cursor.getString(9));
            us.setDireccion(cursor.getString(10));
            us.setCodigoPostal(cursor.getString(11));
            us.setHorario(cursor.getString(12));
        }
        cursor.close();
        db.close();
        return us;
    }

    //SOLICITUDES
    //Comprueba la existencia de una solicitud
    public boolean checkSolicitud(Solicitud solicitud) {
        String[] columns = {
                COLUMN_SOLICITUDES_EMAILSOLICITANTE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?" + " AND " + COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?" + " AND "
                + COLUMN_SOLICITUDES_TIPOANUNCIO + " =?";

        String[] selectionArgs = {solicitud.getEmailSolicitante(), solicitud.getEmailProveedor(), solicitud.getTipoAnuncio()};

        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Comprueba si hay alguna solicitud en curso [ARREGLAR EL ORDEN DE selection]
    public boolean checkSolicitudEnCurso(String email) {
        String[] columns = {
                COLUMN_SOLICITUDES_EMAILSOLICITANTE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_SOLICITUDES_ESTADO + " =?" + " AND " + COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?" + " OR " + COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?";

        String[] selectionArgs = {"En curso", email, email};

        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Añade una solicitud a la base de datos
    public void addSolicitud(Solicitud solicitud) {
        Boolean check = checkSolicitud(solicitud);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOLICITUDES_EMAILSOLICITANTE, solicitud.getEmailSolicitante());
        values.put(COLUMN_SOLICITUDES_EMAILPROVEEDOR, solicitud.getEmailProveedor());
        values.put(COLUMN_SOLICITUDES_TIPOANUNCIO, solicitud.getTipoAnuncio());
        values.put(COLUMN_SOLICITUDES_ESTADO, solicitud.getEstado());
        values.put(COLUMN_SOLICITUDES_CLIENTECHECK, solicitud.getClienteCheck());
        values.put(COLUMN_SOLICITUDES_PROVEEDORCHECK, solicitud.getProveedorCheck());


        if (check) { //Si existe la solicitud, lo actualiza
            db.update(TABLE_SOLICITUDES, values, COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?", new String[]{solicitud.getEmailProveedor()});
            db.close();
        } else { //En caso contrario, inserta la nueva solicitud
            db.insert(TABLE_SOLICITUDES, null, values);
            db.close();
        }
    }

    //Modifica el estado de la solicitud
    public void setEstadoSolicitud(Solicitud solicitud, String estado) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SOLICITUDES_EMAILSOLICITANTE, solicitud.getEmailSolicitante());
        values.put(COLUMN_SOLICITUDES_EMAILPROVEEDOR, solicitud.getEmailProveedor());
        values.put(COLUMN_SOLICITUDES_TIPOANUNCIO, solicitud.getTipoAnuncio());
        values.put(COLUMN_SOLICITUDES_ESTADO, estado);
        values.put(COLUMN_SOLICITUDES_CLIENTECHECK, solicitud.getClienteCheck());
        values.put(COLUMN_SOLICITUDES_PROVEEDORCHECK, solicitud.getProveedorCheck());

        String[] args = new String[]{solicitud.getEmailProveedor()};
        db.update(TABLE_SOLICITUDES, values, COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?", args);

        db.close();
    }

    //Devuelve una lista de solicitudes según un email solicitante dado
    public List<Solicitud> getAllSolicitudes(String email) {
        String[] columns = {
                COLUMN_SOLICITUDES_EMAILSOLICITANTE,
                COLUMN_SOLICITUDES_EMAILPROVEEDOR,
                COLUMN_SOLICITUDES_TIPOANUNCIO
        };
        List<Solicitud> solicitudList = new ArrayList<Solicitud>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?";

        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                Solicitud solicitud = new Solicitud();
                solicitud.setEmailSolicitante(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_EMAILSOLICITANTE)));
                solicitud.setEmailProveedor(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_EMAILPROVEEDOR)));
                solicitud.setTipoAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_TIPOANUNCIO)));
                solicitudList.add(solicitud);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return solicitudList;
    }

    //Devuelve una lista de solicitudes con estado "Pendiente
    public List<Solicitud> getSolicitudesPendientes(String email) {
        String[] columns = {
                COLUMN_SOLICITUDES_EMAILSOLICITANTE,
                COLUMN_SOLICITUDES_EMAILPROVEEDOR,
                COLUMN_SOLICITUDES_TIPOANUNCIO
        };
        List<Solicitud> solicitudList = new ArrayList<Solicitud>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?" + " AND " + COLUMN_SOLICITUDES_ESTADO + " =?";

        String[] selectionArgs = {email, "Pendiente"};
        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                Solicitud solicitud = new Solicitud();
                solicitud.setEmailSolicitante(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_EMAILSOLICITANTE)));
                solicitud.setEmailProveedor(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_EMAILPROVEEDOR)));
                solicitud.setTipoAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_SOLICITUDES_TIPOANUNCIO)));
                solicitudList.add(solicitud);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return solicitudList;
    }

    //Obtiene la solicitud que se encuentra en curso
    public Solicitud getSolicitudEstado(String email, String estado) {

        SQLiteDatabase db = this.getReadableDatabase();
        Solicitud solicitud = new Solicitud();

        String[] columns = {COLUMN_SOLICITUDES_EMAILSOLICITANTE,
                COLUMN_SOLICITUDES_EMAILPROVEEDOR,
                COLUMN_SOLICITUDES_TIPOANUNCIO,
                COLUMN_SOLICITUDES_ESTADO,
                COLUMN_SOLICITUDES_CLIENTECHECK,
                COLUMN_SOLICITUDES_PROVEEDORCHECK
                };
        String selection = COLUMN_SOLICITUDES_ESTADO + " =?" + " AND " + COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?" + " OR " + COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?";

        String[] selectionArgs = {estado, email, email};

        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToFirst();

            solicitud.setEmailSolicitante(cursor.getString(0));
            solicitud.setEmailProveedor(cursor.getString(1));
            solicitud.setTipoAnuncio(cursor.getString(2));
            solicitud.setEstado(cursor.getString(3));
            solicitud.setClienteCheck(cursor.getString(4));
            solicitud.setProveedorCheck(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return solicitud;
    }
    //Obtiene la solicitud que se encuentra en curso
    public Solicitud getSolicitud(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Solicitud solicitud = new Solicitud();

        String[] columns = {COLUMN_SOLICITUDES_EMAILSOLICITANTE,
                COLUMN_SOLICITUDES_EMAILPROVEEDOR,
                COLUMN_SOLICITUDES_TIPOANUNCIO,
                COLUMN_SOLICITUDES_ESTADO,
                COLUMN_SOLICITUDES_CLIENTECHECK,
                COLUMN_SOLICITUDES_PROVEEDORCHECK
        };
        String selection = COLUMN_SOLICITUDES_EMAILSOLICITANTE + " =?" + " OR " + COLUMN_SOLICITUDES_EMAILPROVEEDOR + " =?";

        String[] selectionArgs = {email, email};

        Cursor cursor = db.query(TABLE_SOLICITUDES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToFirst();

            solicitud.setEmailSolicitante(cursor.getString(0));
            solicitud.setEmailProveedor(cursor.getString(1));
            solicitud.setTipoAnuncio(cursor.getString(2));
            solicitud.setEstado(cursor.getString(3));
            solicitud.setClienteCheck(cursor.getString(4));
            solicitud.setProveedorCheck(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return solicitud;
    }

    //Elimina solicitud

    public void setEliminaSolicitud(Solicitud solicitud) {
        Boolean check = checkSolicitud(solicitud);
        SQLiteDatabase db = this.getWritableDatabase();

        if (check) { //Si existe la solicitud, no lo elimina
            db.delete(TABLE_SOLICITUDES, "email_solicitante=? AND email_proveedor=? AND tipo_anuncio=?", new String[]{solicitud.getEmailSolicitante(), solicitud.getEmailProveedor(), solicitud.getTipoAnuncio()});
            db.close();
        } else { //En caso contrario cierra la conexión
            db.close();
        }
    }

}
