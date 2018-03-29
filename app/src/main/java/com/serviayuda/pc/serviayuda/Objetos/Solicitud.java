package com.serviayuda.pc.serviayuda.Objetos;

/**
 * Created by PC on 12/03/2018.
 */

public class Solicitud {

    private String emailSolicitante;
    private String emailProveedor;
    private String tipoAnuncio;
    private String estado;
    private String clienteCheck;
    private String proveedorCheck;


    public String getEmailSolicitante() {
        return emailSolicitante;
    }
    public void setEmailSolicitante(String emailSolicitante){
        this.emailSolicitante = emailSolicitante;
    }
    public String getEmailProveedor(){
        return emailProveedor;
    }
    public void setEmailProveedor(String emailProveedor){
        this.emailProveedor = emailProveedor;
    }
    public String getTipoAnuncio(){
        return tipoAnuncio;
    }
    public void setTipoAnuncio(String tipoAnuncio){
        this.tipoAnuncio = tipoAnuncio;
    }
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getClienteCheck(){
        return clienteCheck;
    }
    public void setClienteCheck(String clienteCheck){
        this.clienteCheck = clienteCheck;
    }
    public String getProveedorCheck(){
        return proveedorCheck;
    }
    public void setProveedorCheck(String proveedorCheck){
        this.proveedorCheck = proveedorCheck;
    }
}
