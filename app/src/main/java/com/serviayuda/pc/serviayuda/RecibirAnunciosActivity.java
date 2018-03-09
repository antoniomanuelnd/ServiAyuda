package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 28/02/2018.
 */

public class RecibirAnunciosActivity extends AsyncTask {

    private Context context;
    private Activity activity;
    private DatabaseHelper databaseHelper;

    public RecibirAnunciosActivity(Context context, Activity activity, DatabaseHelper databaseHelper){
        this.context = context;
        this.activity = activity;
        this.databaseHelper = new DatabaseHelper(activity);
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Obtenemos los elementos a insertar en la BBDD

            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/recibeAnuncios.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode("email", "UTF-8");

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
        String aux = res.toString();
        String respuesta[] = aux.split("<.>");
        Anuncio anuncio = new Anuncio();

        for (int i=0; i<respuesta.length; i=i+7){

            anuncio.setEmail(respuesta[i]);
            anuncio.setNombre(respuesta[i+1]);
            anuncio.setTipoAnuncio(respuesta[i+2]);
            anuncio.setHoras(respuesta[i+3]);
            anuncio.setHoraDeseada(respuesta[i+4]);
            anuncio.setDescripcion(respuesta[i+5]);
            anuncio.setEstado(respuesta[i+6]);

            databaseHelper.addAnuncio(anuncio);
        }
    }
}
