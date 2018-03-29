package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by PC on 28/03/2018.
 */

public class RecibeSolicitudEnCursoActivity extends AsyncTask {

    private Context context;
    private String email;
    private DatabaseHelper databaseHelper;
    private Activity activity;


    public RecibeSolicitudEnCursoActivity(Context context, Activity activity, String email) {
        this.context = context;
        this.email = email;
        this.databaseHelper = new DatabaseHelper(activity);
        this.activity = activity;

    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/recibeSolicitudEnCurso.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String linea = "";

            //Leer respuesta del servidor
            while ((linea = reader.readLine()) != null) {
                sb.append(linea);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Excepción: " + e.getMessage());
        }
    }

    protected void onPostExecute(Object res) {
        String resultado = res.toString();
        if (resultado.compareTo("ERROR") != 0) {

            String respuesta[] = resultado.split("<.>");

            Solicitud solicitud = new Solicitud();

            solicitud.setEmailSolicitante(respuesta[0]);
            solicitud.setEmailProveedor(respuesta[1]);
            solicitud.setTipoAnuncio(respuesta[2]);
            solicitud.setEstado(respuesta[3]);
            solicitud.setClienteCheck(respuesta[4]);
            solicitud.setProveedorCheck(respuesta[5]);
            databaseHelper.addSolicitud(solicitud);

            if (email.compareTo(respuesta[0]) == 0) { //Si estoy desde la cuenta del anunciante/solicitante
                    new RecibeAnuncioYUsuarioActivity(context, activity, respuesta[1], email).execute(); //Pido el usuario proveedor
            } else { //Si estoy desde la cuenta del proveedor
                    new RecibeAnuncioYUsuarioActivity(context, activity, respuesta[0], respuesta[0]).execute(); //Pido el usuario anunciante
            }
        }
    }
}


