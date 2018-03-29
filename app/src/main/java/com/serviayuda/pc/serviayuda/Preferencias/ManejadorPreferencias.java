package com.serviayuda.pc.serviayuda.Preferencias;

import android.content.SharedPreferences;

/**
 * Created by PC on 07/02/2018.
 */

public class ManejadorPreferencias{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ManejadorPreferencias(SharedPreferences preferences){
        this.preferences = preferences;
        this.editor = preferences.edit();
    }

    //Método que guarda el email en preferencias, para así poder
    //Mantener la sesión iniciada, o tener un identificador del usuario
    //que está haciendo uso de la aplicación
    public void guardarPreferencias(String key, String cadena){

        this.editor.putString(key, cadena);
        this.editor.commit();
    }

    //Si no se encontrara la información indicada, se mostraría por defecto el texto
    //"No existe la información"
    public String cargarPreferencias(String key){

        String cadena = this.preferences.getString(key, "No existe la información");
        return cadena;
    }

    //Elimina las preferencias, será usado al cerrar sesión
    public void eliminarPreferencias(String key){

        this.editor.remove(key);
        this.editor.commit();
    }

}