package com.serviayuda.pc.serviayuda.Activitys;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 01/05/2018.
 */

public class InterfazActivity extends AsyncTask {

    private Context context;
    private String email, estado;
    private DatabaseHelper databaseHelper;

    public InterfazActivity(Context context, String email, String estado) {
        this.context = context;
        this.email = email;
        this.estado = estado;
        this.databaseHelper = new DatabaseHelper(context);
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {


            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/interfaz.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
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

    protected void onPostExecute(Object resultado) {
        String respuesta = resultado.toString();
        if (respuesta.compareTo("Correcto") == 0) {
            Toast.makeText(context.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
}
