package com.serviayuda.pc.serviayuda.Objetos;

import android.app.Activity;
import android.content.SharedPreferences;

import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;

/**
 * Created by PC on 22/04/2018.
 */

public class Utilidades {

    public static String creaClave(String email){


        //Método final, mientras se realicen pruebas, estará comentado
        /*
        String partes [] = email.split("@");
        String partes2 [] = partes[1].split("\\.");
        return partes[0] + partes2[0];
        */

        //Método de pruebas
        return email;
    }
}
