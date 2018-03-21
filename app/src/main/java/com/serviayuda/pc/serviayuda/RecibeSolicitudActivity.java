package com.serviayuda.pc.serviayuda;

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
import java.util.ArrayList;

/**
 * Created by PC on 13/03/2018.
 */

public class RecibeSolicitudActivity extends AsyncTask {

    private Context context;
    private String email;
    private DatabaseHelper databaseHelper;
    private Activity activity;


    public RecibeSolicitudActivity(Context context, Activity activity, String email) {
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
            String link = "https://apptfg.000webhostapp.com/recibirSolicitud2.php";
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
        if (resultado.compareTo("ERROR") == 0) {
            Toast.makeText(activity.getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        } else {
            String respuesta[] = resultado.split("<.>");
            Solicitud solicitud = new Solicitud();
            ArrayList<Solicitud> list = new ArrayList<>();
            Usuario usuario = new Usuario();
            for (int i = 0; i < respuesta.length; i = i + 13) {

                solicitud.setEmailSolicitante(respuesta[i]);
                solicitud.setEmailProveedor(respuesta[i + 1]);
                solicitud.setTipoAnuncio(respuesta[i + 2]);
                databaseHelper.addSolicitud(solicitud);
                usuario.setNombre(respuesta[i + 3]);
                usuario.setApellidos(respuesta[i + 4]);
                usuario.setEmail(respuesta[i + 1]);
                usuario.setTipoPerfil(respuesta[i + 5]);
                usuario.setTipoServicio(respuesta[i + 6]);
                usuario.setUbicacion(respuesta[i + 7]);
                usuario.setCodigoPostal(respuesta[i + 8]);
                usuario.setDescripcion(respuesta[i + 9]);
                usuario.setExperiencia(respuesta[i + 10]);
                usuario.setHorario(respuesta[i + 11]);
                usuario.setEdad(respuesta[i + 12]);
                databaseHelper.addUsuario(usuario);
                list.add(solicitud);
            }

        }
    }
}


