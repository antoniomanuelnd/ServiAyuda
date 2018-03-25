package com.serviayuda.pc.serviayuda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

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
    private Usuario usuario = new Usuario();

    public InicioSesionActivity(Context context, TextView res, AppCompatActivity activity) {
        this.context = context;
        this.res = res;
        this.activity = activity;
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
            Toast.makeText(context.getApplicationContext(), "Iniciando sesión", Toast.LENGTH_LONG).show();
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Solicitante");
        } else if (respuesta.compareTo("Proveedor") == 0) {
            Toast.makeText(context.getApplicationContext(), "Iniciando sesión", Toast.LENGTH_LONG).show();
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Proveedor");
        }else if (respuesta.compareTo("Administrador") == 0){
            Toast.makeText(context.getApplicationContext(), "Iniciando sesión", Toast.LENGTH_LONG).show();
            new RecibeUsuarioActivity(context, activity, usuario).execute(usuario.getEmail(), "Administrador");
        } else {
            this.res.setTextColor(Color.parseColor("#CF000F"));
            this.res.setText("No se ha podido iniciar sesión");
        }
    }

}
