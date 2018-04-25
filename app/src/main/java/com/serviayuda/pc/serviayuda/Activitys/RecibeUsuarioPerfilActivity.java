package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.os.AsyncTask;

import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Fragments.RecibirAnuncioFragment;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

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
    private RecibirAnuncioFragment context;
    private final Activity activity;
    private DatabaseHelper databaseHelper;


    public RecibeUsuarioPerfilActivity(RecibirAnuncioFragment context, Activity activity) {
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
        if (respuesta.compareTo("ERROR") != 0) {
            String res[] = respuesta.split("<.>");

            Usuario usuario = new Usuario();
            usuario.setNombre(res[0]);
            usuario.setApellidos(res[1]);
            usuario.setEmail(res[2]);
            usuario.setVerificado(res[3]);
            usuario.setTipoPerfil(res[4]);
            usuario.setTipoServicio(res[5]);
            usuario.setCiudad(res[6]);
            usuario.setLocalidad(res[7]);
            usuario.setDireccion(res[8]);
            usuario.setCodigoPostal(res[9]);
            usuario.setDescripcion(res[10]);
            usuario.setExperiencia(res[11]);
            usuario.setHorario(res[12]);
            usuario.setEdad((res[13]));

            databaseHelper.addUsuario(usuario);
        }
    }
}