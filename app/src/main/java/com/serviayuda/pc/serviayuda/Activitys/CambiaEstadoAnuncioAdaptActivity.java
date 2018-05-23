package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.os.AsyncTask;

import com.serviayuda.pc.serviayuda.Fragments.SolicitudesFragmentAdapt;
import com.serviayuda.pc.serviayuda.Fragments.VerSolicitudesFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 27/03/2018.
 */

public class CambiaEstadoAnuncioAdaptActivity extends AsyncTask {

    private SolicitudesFragmentAdapt context;
    private String email;
    private String estado;

    private Activity activity;

    public CambiaEstadoAnuncioAdaptActivity(SolicitudesFragmentAdapt context, Activity activity, String email, String estado) {
        this.context = context;
        this.email = email;
        this.estado = estado;
        this.activity = activity;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/cambiaEstadoAnuncio.php";
            String data = URLEncoder.encode("emailSolicitante", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("estado", "UTF-8") + "=" +
                    URLEncoder.encode(estado, "UTF-8");

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

    }
}
