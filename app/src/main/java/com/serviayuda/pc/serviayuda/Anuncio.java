package com.serviayuda.pc.serviayuda;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 27/02/2018.
 */

public class Anuncio implements Parcelable {

    private String nombre;
    private String email;
    private String tipoanuncio;
    private String descripcion;
    private String horas;
    private String horaDeseada;
    private String estado;

    public Anuncio(String nombre, String email, String tipoanuncio, String descripcion, String horas, String horaDeseada, String estado) {
        this.nombre = nombre;
        this.email = email;
        this.tipoanuncio = tipoanuncio;
        this.descripcion = descripcion;
        this.horas = horas;
        this.horaDeseada = horaDeseada;
        this.estado = estado;
    }
    public Anuncio() {
    }


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
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public String getHoras(){
        return horas;
    }
    public void setHoras(String horas){
        this.horas = horas;
    }
    public String getHoraDeseada(){
        return horaDeseada;
    }
    public void setHoraDeseada(String horaDeseada){
        this.horaDeseada = horaDeseada;
    }
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }

    protected Anuncio(Parcel in) {
        nombre = in.readString();
        email = in.readString();
        tipoanuncio = in.readString();
        descripcion = in.readString();
        horas = in.readString();
        horaDeseada = in.readString();
        estado = in.readString();
    }

    public static final Creator<Anuncio> CREATOR = new Creator<Anuncio>() {
        @Override
        public Anuncio createFromParcel(Parcel in) {
            return new Anuncio(in);
        }

        @Override
        public Anuncio[] newArray(int size) {
            return new Anuncio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(email);
        parcel.writeString(tipoanuncio);
        parcel.writeString(descripcion);
        parcel.writeString(horas);
        parcel.writeString(horaDeseada);
        parcel.writeString(estado);
    }
}
