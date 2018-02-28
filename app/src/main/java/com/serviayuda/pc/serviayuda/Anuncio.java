package com.serviayuda.pc.serviayuda;

/**
 * Created by PC on 27/02/2018.
 */

public class Anuncio {

    private String nombre;
    private String email;
    private String tipoanuncio;
    private String descripcion;
    private String horas;

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getTipoAnuncio(){
        return tipoanuncio;
    }
    public void setTipoAnuncio(String tipoanuncio){
        this.tipoanuncio = tipoanuncio;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setAnuncio(String descripcion){
        this.descripcion = descripcion;
    }
    public String getHoras(){
        return descripcion;
    }
    public void setHoras(String horas){
        this.horas = horas;
    }

}
