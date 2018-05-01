package com.serviayuda.pc.serviayuda.Objetos;


/**
 * Created by PC on 22/04/2018.
 */

public class Utilidades {

    public static String creaClave(String email){

        String partes [] = email.split("@");
        String partes2 [] = partes[1].split("\\.");
        return partes[0] + partes2[0];

    }
}
