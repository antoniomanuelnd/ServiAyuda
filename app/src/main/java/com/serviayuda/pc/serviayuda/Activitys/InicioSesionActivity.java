package com.serviayuda.pc.serviayuda.Activitys;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.tapadoo.alerter.Alerter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 07/02/2018.
 */

public class InicioSesionActivity extends AsyncTask {

    private Context context;
    private TextView res;
    private final AppCompatActivity activity;
    private ManejadorPreferencias mp;
    private String sSesion;
    private Usuario usuario = new Usuario();

    public InicioSesionActivity(Context context, TextView res, AppCompatActivity activity, ManejadorPreferencias mp, String sSesion) {
        this.context = context;
        this.res = res;
        this.activity = activity;
        this.mp = mp;
        this.sSesion = sSesion;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Obtenemos los elementos a insertar en la BBDD
            String email = (String) arg0[0];
            usuario.setEmail(email);
            String password = (String) arg0[1];
            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/inicioSesion.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("token", "UTF-8") + "=" +
                    URLEncoder.encode(FirebaseInstanceId.getInstance().getToken(), "UTF-8");


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
            Alerter.create(activity)
                    .setTitle("INICIANDO SESIÓN")
                    .setText("Iniciando sesión")
                    .setBackgroundColorInt(Color.MAGENTA)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
            mp.guardarPreferencias("KEY_SESION", sSesion);
            mp.guardarPreferencias("KEY_EMAIL", usuario.getEmail());
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Solicitante");
        } else if (respuesta.compareTo("Proveedor") == 0) {
            Alerter.create(activity)
                    .setTitle("INICIANDO SESIÓN")
                    .setText("Iniciando sesión")
                    .setBackgroundColorInt(Color.MAGENTA)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
            mp.guardarPreferencias("KEY_SESION", sSesion);
            mp.guardarPreferencias("KEY_EMAIL", usuario.getEmail());
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Proveedor");
        }else if (respuesta.compareTo("Administrador") == 0){
            Alerter.create(activity)
                    .setTitle("INICIANDO SESIÓN")
                    .setText("Iniciando sesión")
                    .setBackgroundColorInt(Color.MAGENTA)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .show();
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Administrador");
        } else {
            Alerter.create(activity)
                    .setTitle("ERROR AL INICIAR SESIÓN")
                    .setText("Error en las credenciales")
                    .setBackgroundColorInt(Color.RED)
                    .setDuration(4000)
                    .enableSwipeToDismiss()
                    .show();
        }
    }

}
