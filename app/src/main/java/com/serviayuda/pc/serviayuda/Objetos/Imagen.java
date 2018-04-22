package com.serviayuda.pc.serviayuda.Objetos;

/**
 * Created by PC on 22/04/2018.
 */

import com.google.firebase.database.Exclude;

public class Imagen {
    private String nombre;
    private String uri;
    private String key;

    public Imagen() {

    }

    public Imagen(String nombre, String uri) {
        if (nombre.trim().equals("")) {
            nombre = "Sin nombre";
        }

        this.nombre = nombre;
        this.uri = uri;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUri() {
        return uri;
    }

    public void seturi(String uri) {
        this.uri = uri;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

}
