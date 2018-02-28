package com.serviayuda.pc.serviayuda;

/**
 * Created by PC on 27/02/2018.
 */

public class Usuario {

    private String nombre;
    private String apellidos;
    private String email;
    private String tipo_perfil;
    private String tipo_servicio;
    private String ubicacion;
    private Integer codigo_postal;
    private String descripcion;
    private String experiencia;
    private String horario;
    private Integer edad;

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
    public String getUbicacion(){
        return ubicacion;
    }
    public void setUbicacion(String ubicacion){
        this.ubicacion = ubicacion;
    }
    public Integer getCodigoPostal(){
        return codigo_postal;
    }
    public void setCodigoPostal(Integer codigo_postal){
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
    public Integer getEdad(){
        return edad;
    }
    public void setEdad(Integer edad){
        this.edad = edad;
    }
}