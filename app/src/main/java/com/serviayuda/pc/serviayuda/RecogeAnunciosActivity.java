package com.serviayuda.pc.serviayuda;

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

public class RecogeAnunciosActivity extends AsyncTask {

    private Context context;
    private final AppCompatActivity activity;
    private DatabaseHelper databaseHelper;

    public RecogeAnunciosActivity(Context context, AppCompatActivity activity, DatabaseHelper databaseHelper){
        this.context = context;
        this.activity = activity;
        this.databaseHelper = databaseHelper;
        this.databaseHelper = new DatabaseHelper(activity);
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Obtenemos los elementos a insertar en la BBDD
            String email = (String) arg0[0];
            String password = (String) arg0[1];
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/inicioSesion.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");


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

        if (respuesta.compareTo("Solicitante") == 0) {
            Toast.makeText(context.getApplicationContext(), "Iniciando sesión", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, MenuViewPagerSolicitante.class);
            context.startActivity(i);

        }
    }
}
