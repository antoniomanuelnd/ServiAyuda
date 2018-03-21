package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by PC on 19/03/2018.
 */

public class RechazaSolicitudActivity extends AsyncTask {

    private VerSolicitudesFragment context;
    private Solicitud solicitud;
    private DatabaseHelper databaseHelper;
    private Activity activity;

    public RechazaSolicitudActivity(VerSolicitudesFragment context, Activity activity, Solicitud solicitud) {
        this.context = context;
        this.solicitud = solicitud;
        this.databaseHelper = new DatabaseHelper(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/rechazaSolicitud.php";
            String data = URLEncoder.encode("emailSolicitante", "UTF-8") + "=" +
                    URLEncoder.encode(solicitud.getEmailSolicitante(), "UTF-8");
            data += "&" + URLEncoder.encode("emailProveedor", "UTF-8") + "=" +
                    URLEncoder.encode(solicitud.getEmailProveedor(), "UTF-8");
            data += "&" + URLEncoder.encode("tipo_anuncio", "UTF-8") + "=" +
                    URLEncoder.encode(solicitud.getTipoAnuncio(), "UTF-8");

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
        if (resultado.compareTo("OK") == 0) {
            Toast.makeText(activity.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            databaseHelper.setEliminaSolicitud(solicitud);
        }
    }
}
