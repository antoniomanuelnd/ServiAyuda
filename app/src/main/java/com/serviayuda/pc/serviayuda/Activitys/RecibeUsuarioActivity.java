package com.serviayuda.pc.serviayuda.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.serviayuda.pc.serviayuda.Actividades.ActivitySetViewPagerAdmin;
import com.serviayuda.pc.serviayuda.Actividades.ActivitySetViewPagerProveedor;
import com.serviayuda.pc.serviayuda.Actividades.ActivitySetViewPagerSolicitante;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 28/02/2018.
 */

public class RecibeUsuarioActivity extends AsyncTask {
    private Context context;
    private final AppCompatActivity activity;
    private DatabaseHelper databaseHelper;
    private Usuario usuario;
    private String tipo;

    public RecibeUsuarioActivity(Context context, AppCompatActivity activity, Usuario usuario) {
        this.context = context;
        this.activity = activity;
        this.databaseHelper = new DatabaseHelper(activity);
        this.usuario = usuario;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {

        //Método POST
        try {
            //Obtenemos los elementos a insertar en la BBDD

            String email = (String) arg0[0];
            tipo = (String) arg0[1];
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

            if (tipo.compareTo("Solicitante") == 0) {
                Intent i = new Intent(context, ActivitySetViewPagerSolicitante.class);
                context.startActivity(i);
            } else if (tipo.compareTo("Proveedor") == 0) {
                Intent i = new Intent(context, ActivitySetViewPagerProveedor.class);
                context.startActivity(i);
            } else if (tipo.compareTo("Administrador") == 0) {
                Intent i = new Intent(context, ActivitySetViewPagerAdmin.class);
                context.startActivity(i);
            }
        }
    }
}
