package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 29/03/2018.
 */

public class PendienteDePuntuacionActivity extends AsyncTask {
    private Context context;
    private String email;
    private String estado;
    private String notificar;
    private Activity activity;

    public PendienteDePuntuacionActivity(Context context, Activity activity, String email, String estado, String notificar) {
        this.context = context;
        this.email = email;
        this.estado = estado;
        this.activity = activity;
        this.notificar = notificar;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/pendienteDePuntuacion.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("estado", "UTF-8") + "=" +
                    URLEncoder.encode(estado, "UTF-8");
            data += "&" + URLEncoder.encode("notificar", "UTF-8") + "=" +
                    URLEncoder.encode(notificar, "UTF-8");

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



