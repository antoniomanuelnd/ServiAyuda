package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 28/03/2018.
 */

public class RecibeAnuncioYUsuarioActivity extends AsyncTask {

    private Context context;
    private String emailUsuario;
    private String emailAnuncio;
    private DatabaseHelper databaseHelper;
    private Activity activity;


    public RecibeAnuncioYUsuarioActivity(Context context, Activity activity, String emailUsuario, String emailAnuncio) {
        this.context = context;
        this.emailUsuario = emailUsuario;
        this.emailAnuncio = emailAnuncio;
        this.databaseHelper = new DatabaseHelper(activity);
        this.activity = activity;

    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/recibeUsuarioYAnuncio.php";
            String data = URLEncoder.encode("emailUsuario", "UTF-8") + "=" +
                    URLEncoder.encode(emailUsuario, "UTF-8");
            data += "&" + URLEncoder.encode("emailAnuncio", "UTF-8") + "=" +
                    URLEncoder.encode(emailAnuncio, "UTF-8");

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
        if (resultado.compareTo("ERROR") != 0) {

            String respuesta[] = resultado.split("<.>");
            Usuario usuario = new Usuario();
            Anuncio anuncio = new Anuncio();

            usuario.setNombre(respuesta[0]);
            usuario.setApellidos(respuesta[1]);
            usuario.setEmail(respuesta[2]);
            usuario.setTipoPerfil(respuesta[3]);
            usuario.setTipoServicio(respuesta[4]);
            usuario.setUbicacion(respuesta[5]);
            usuario.setCodigoPostal(respuesta[6]);
            usuario.setDescripcion(respuesta[7]);
            usuario.setExperiencia(respuesta[8]);
            usuario.setHorario(respuesta[9]);
            usuario.setEdad(respuesta[10]);

            anuncio.setEmail(respuesta[11]);
            anuncio.setNombre(respuesta[12]);
            anuncio.setTipoAnuncio(respuesta[13]);
            anuncio.setHoras(respuesta[14]);
            anuncio.setHoraDeseada(respuesta[15]);
            anuncio.setDescripcion(respuesta[16]);
            anuncio.setEstado(respuesta[17]);

            databaseHelper.addUsuario(usuario);
            databaseHelper.addAnuncio(anuncio);
        }
    }
}




