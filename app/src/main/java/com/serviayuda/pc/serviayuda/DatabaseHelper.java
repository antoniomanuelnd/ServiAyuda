package com.serviayuda.pc.serviayuda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    //Columnas de la tabla usuario
    private static final String COLUMN_USUARIO_NOMBRE = "nombre";
    private static final String COLUMN_USUARIO_APELLIDOS = "apellidos";
    private static final String COLUMN_USUARIO_EMAIL = "email";
    private static final String COLUMN_USUARIO_TIPOPERFIL = "tipo_perfil";
    private static final String COLUMN_USUARIO_TIPOSERVICIO = "tipo_servicio";
    private static final String COLUMN_USUARIO_UBICACION = "ubicacion";
    private static final String COLUMN_USUARIO_CODIGOPOSTAL = "codigo_postal";
    private static final String COLUMN_USUARIO_DESCRIPCION = "descripcion";
    private static final String COLUMN_USUARIO_EXPERIENCIA = "experiencia";
    private static final String COLUMN_USUARIO_HORARIO = "horario";
    private static final String COLUMN_USUARIO_EDAD = "edad";

    //Columnas de la tabla anuncio
    private static final String COLUMN_ANUNCIO_NOMBRE = "nombre";
    private static final String COLUMN_ANUNCIO_EMAIL = "email";
    private static final String COLUMN_ANUNCIO_TIPOANUNCIO = "tipoanuncio";
    private static final String COLUMN_ANUNCIO_DESCRIPCION = "anuncio";

    //Creación de la tabla anuncio
    private String CREATE_ANUNCIO_TABLE = "CREATE TABLE " + TABLE_ANUNCIO + " ( "
            + COLUMN_ANUNCIO_NOMBRE + " TEXT, " + COLUMN_ANUNCIO_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_ANUNCIO_TIPOANUNCIO + " TEXT, "
            + COLUMN_ANUNCIO_DESCRIPCION + " TEXT" + " )";
    //Creación de la tabla usuario
    private String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + " ( " + COLUMN_USUARIO_NOMBRE + " TEXT, "
            + COLUMN_USUARIO_APELLIDOS + " TEXT, " + COLUMN_USUARIO_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_USUARIO_TIPOPERFIL
            + " TEXT, " + COLUMN_USUARIO_TIPOSERVICIO + " TEXT, " + COLUMN_USUARIO_UBICACION + " TEXT, " + COLUMN_USUARIO_CODIGOPOSTAL
            + " INTEGER, " + COLUMN_USUARIO_DESCRIPCION + " TEXT, " + COLUMN_USUARIO_EXPERIENCIA + " TEXT, " + COLUMN_USUARIO_HORARIO +
            " TEXT, " + COLUMN_USUARIO_EDAD + " INTEGER" + " )";


    //Eliminación de la tabla anuncio
    private String DROP_ANUNCIO_TABLE = "DROP TABLE IF EXISTS " + TABLE_ANUNCIO;

    //Eliminación de la tabla usuario
    private String DROP_USUARIO_TABLE = "DROP TABLE IF EXITS " + TABLE_USUARIO;

    //Constructor
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_ANUNCIO_TABLE);
        db.execSQL(CREATE_USUARIO_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Se elimina la tabla si existe
        db.execSQL(DROP_ANUNCIO_TABLE);
        db.execSQL(DROP_USUARIO_TABLE);
        //Se crea la tabla de nuevo
        onCreate(db);
    }

    //ANUNCIOS
    public void addAnuncio(Anuncio anuncio){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ANUNCIO_NOMBRE, anuncio.getNombre());
        values.put(COLUMN_ANUNCIO_EMAIL, anuncio.getEmail());
        values.put(COLUMN_ANUNCIO_TIPOANUNCIO, anuncio.getTipoAnuncio());
        values.put(COLUMN_ANUNCIO_DESCRIPCION, anuncio.getDescripcion());

        //Inserta una fila
        db.insert(TABLE_ANUNCIO, null, values);
        db.close();
    }

    public List<Anuncio> getAllAnuncios(){
        String[] columns = {
                COLUMN_ANUNCIO_NOMBRE,
                COLUMN_ANUNCIO_EMAIL,
                COLUMN_ANUNCIO_TIPOANUNCIO,
                COLUMN_ANUNCIO_DESCRIPCION
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
        if(cursor.moveToFirst()){
            do {
                Anuncio anuncio = new Anuncio();
                anuncio.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_NOMBRE)));
                anuncio.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_EMAIL)));
                anuncio.setTipoAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_TIPOANUNCIO)));
                anuncio.setAnuncio(cursor.getString(cursor.getColumnIndex(COLUMN_ANUNCIO_DESCRIPCION)));

                anuncioList.add(anuncio);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return anuncioList;
    }

    public boolean checkEmail(String email){
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

        if (cursorCount > 0){
            return true;
        }else {
            return false;
        }
    }

    //USUARIOS
    public void addUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOMBRE, usuario.getNombre());
        values.put(COLUMN_USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(COLUMN_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMN_USUARIO_TIPOPERFIL, usuario.getTipoPerfil());
        values.put(COLUMN_USUARIO_TIPOSERVICIO, usuario.getTipoServicio());
        values.put(COLUMN_USUARIO_UBICACION, usuario.getUbicacion());
        values.put(COLUMN_USUARIO_CODIGOPOSTAL, usuario.getCodigoPostal());
        values.put(COLUMN_USUARIO_DESCRIPCION, usuario.getDescripcion());
        values.put(COLUMN_USUARIO_EXPERIENCIA, usuario.getExperiencia());
        values.put(COLUMN_USUARIO_HORARIO, usuario.getHorario());
        values.put(COLUMN_USUARIO_EDAD, usuario.getEdad());

        //Inserta una fila
        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    public Usuario getUsuario(String email){

        SQLiteDatabase db = this.getReadableDatabase();
        Usuario us = new Usuario();
        try {
            Cursor c = null;
            c = db.rawQuery("SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMN_USUARIO_EMAIL + " = " + email, null);
            c.moveToFirst();
            us.setNombre(c.getString(c.getColumnIndex(COLUMN_USUARIO_NOMBRE)));
            us.setApellidos(c.getString(c.getColumnIndex(COLUMN_USUARIO_APELLIDOS)));
            us.setNombre(email);
            us.setTipoPerfil(c.getString(c.getColumnIndex(COLUMN_USUARIO_TIPOPERFIL)));
            us.setTipoServicio(c.getString(c.getColumnIndex(COLUMN_USUARIO_TIPOSERVICIO)));
            us.setUbicacion(c.getString(c.getColumnIndex(COLUMN_USUARIO_UBICACION)));
            us.setCodigoPostal(c.getInt(c.getColumnIndex(COLUMN_USUARIO_CODIGOPOSTAL)));
            us.setDescripcion(c.getString(c.getColumnIndex(COLUMN_USUARIO_DESCRIPCION)));
            us.setExperiencia(c.getString(c.getColumnIndex(COLUMN_USUARIO_EXPERIENCIA)));
            us.setHorario(c.getString(c.getColumnIndex(COLUMN_USUARIO_HORARIO)));
            us.setEdad(c.getInt(c.getColumnIndex(COLUMN_USUARIO_EDAD)));
        }catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        return us;
    }
}
