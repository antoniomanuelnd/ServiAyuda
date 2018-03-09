package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 07/03/2018.
 */

public class RecibeUsuarioPerfilActivity extends AsyncTask {
    private Context context;
    private final Activity activity;
    private DatabaseHelper databaseHelper;


    public RecibeUsuarioPerfilActivity(Context context, Activity activity) {
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

            String email = (String) arg0[0];
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/recibeUsuario.php";
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

    protected void onPostExecute(Object resultado) {
        String respuesta = resultado.toString();
        String res[] = respuesta.split("<.>");

        Usuario usuario = new Usuario();
        usuario.setNombre(res[0]);
        usuario.setApellidos(res[1]);
        usuario.setEmail(res[2]);
        usuario.setTipoPerfil(res[3]);
        usuario.setTipoServicio(res[4]);
        usuario.setUbicacion(res[5]);
        usuario.setCodigoPostal(res[6]);
        usuario.setDescripcion(res[7]);
        usuario.setExperiencia(res[8]);
        usuario.setHorario(res[9]);
        usuario.setEdad((res[10]));

        databaseHelper.addUsuario(usuario);

    }
}