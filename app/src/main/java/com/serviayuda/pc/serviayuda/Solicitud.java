package com.serviayuda.pc.serviayuda;

/**
 * Created by PC on 12/03/2018.
 */

public class Solicitud {

    private String emailSolicitante;
    private String emailProveedor;
    private String tipoAnuncio;

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
}
