package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 31/03/2018.
 */

public class EnviaPuntuacionActivity extends AsyncTask {

    private Context context;
    private Activity activity;
    private String email;
    private String email2;
    private Float rating;
    private Integer votos_positivos;
    private Integer votos_negativos;

    public EnviaPuntuacionActivity(Context context, Activity activity, String email, String email2, Float rating, Integer votos_positivos, Integer votos_negativos){
        this.context = context;
        this.activity = activity;
        this.email = email;
        this.email2 = email2;
        this.rating = rating;
        this.votos_positivos = votos_positivos;
        this.votos_negativos = votos_negativos;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/enviarPuntuacion.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("email2", "UTF-8") + "=" +
                    URLEncoder.encode(email2, "UTF-8");
            data += "&" + URLEncoder.encode("rating", "UTF-8") + "=" +
                    URLEncoder.encode(rating.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("votos_positivos", "UTF-8") + "=" +
                    URLEncoder.encode(votos_positivos.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("votos_negativos", "UTF-8") + "=" +
                    URLEncoder.encode(votos_negativos.toString(), "UTF-8");

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
        String respuesta = res.toString();
    }

}
