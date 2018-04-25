package com.serviayuda.pc.serviayuda.Objetos;

/**
 * Created by PC on 27/02/2018.
 */

public class Usuario {

    private String nombre;
    private String apellidos;
    private String email;
    private String verificado;
    private String tipo_perfil;
    private String tipo_servicio;
    private String ciudad;
    private String localidad;
    private String direccion;
    private String codigo_postal;
    private String descripcion;
    private String experiencia;
    private String horario;
    private String edad;

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getVerificado(){
        return verificado;
    }
    public void setVerificado(String verificado){
        this.verificado = verificado;
    }
    public String getTipoPerfil(){
        return  tipo_perfil;
    }
    public void setTipoPerfil(String tipo_perfil){
        this.tipo_perfil = tipo_perfil;
    }
    public String getTipoServicio(){
        return tipo_servicio;
    }
    public void setTipoServicio(String tipo_servicio){
        this.tipo_servicio = tipo_servicio;
    }
    public String getCiudad(){
        return ciudad;
    }
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    public String getLocalidad(){
        return localidad;
    }
    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public String getCodigoPostal(){
        return codigo_postal;
    }
    public void setCodigoPostal(String codigo_postal){
        this.codigo_postal = codigo_postal;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public String getExperiencia(){
        return experiencia;
    }
    public void setExperiencia(String experiencia){
        this.experiencia = experiencia;
    }
    public String getHorario(){
        return horario;
    }
    public void setHorario(String horario){
        this.horario = horario;
    }
    public String getEdad(){
        return edad;
    }
    public void setEdad(String edad){
        this.edad = edad;
    }
}