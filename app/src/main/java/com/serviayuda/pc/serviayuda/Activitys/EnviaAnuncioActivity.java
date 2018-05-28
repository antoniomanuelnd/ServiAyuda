package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.Actividades.Editar;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.tapadoo.alerter.Alerter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 01/03/2018.
 */

public class EnviaAnuncioActivity extends AsyncTask {

    private Context context;
    private Anuncio anuncio;
    private Activity activity;

    public EnviaAnuncioActivity(Context context, Activity activity, Anuncio anuncio){
        this.context = context;
        this.anuncio = anuncio;
        this.activity = activity;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/insertarAnuncio.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getEmail(), "UTF-8");
            data += "&" + URLEncoder.encode("nombre", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getNombre(), "UTF-8");
            data += "&" + URLEncoder.encode("descripcion", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getDescripcion(), "UTF-8");
            data += "&" + URLEncoder.encode("tipo_anuncio", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getTipoAnuncio(), "UTF-8");
            data += "&" + URLEncoder.encode("horas", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getHoras(), "UTF-8");
            data += "&" + URLEncoder.encode("hora_deseada", "UTF-8") + "=" +
                    URLEncoder.encode(anuncio.getHoraDeseada(), "UTF-8");
            data += "&" + URLEncoder.encode("estado", "UTF-8") + "=" +
                    URLEncoder.encode("Pendiente", "UTF-8");

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

        if (respuesta.compareTo("ACTUALIZADO") == 0) {
            Alerter.create(activity)
                    .setTitle("ANUNCIO ACTUALIZADO")
                    .setText("El anuncio se ha actualizado con éxito")
                    .setBackgroundColorInt(Color.MAGENTA)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
        }else if (respuesta.compareTo("INSERTADO") == 0){
            Alerter.create(activity)
                    .setTitle("ANUNCIO ENVIADO")
                    .setText("El anuncio se ha enviado con éxito")
                    .setBackgroundColorInt(Color.MAGENTA)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
        }else {
            Alerter.create(activity)
                    .setTitle("ERROR")
                    .setText("El anuncio no ha podido ser enviado")
                    .setBackgroundColorInt(Color.RED)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
        }
    }

}
