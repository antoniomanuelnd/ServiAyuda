package com.serviayuda.pc.serviayuda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by PC on 07/02/2018.
 */

public class RegistroActivity extends AsyncTask {

    private Context context;
    private TextView res;

    public RegistroActivity(Context context, TextView res){
        this.context = context;
        this.res = res;
    }

    protected void onPreExecute(){
    }

    protected Object doInBackground(Object[] arg0){
        //Método POST
        try{
            //Obtenemos los elementos a insertar en la BBDD
            String nombre = (String) arg0[0];
            String email = (String) arg0[1];
            String password = (String) arg0[2];
            //Generamos el link
            String link="https://apptfg.000webhostapp.com/registro.php";
            String data  = URLEncoder.encode("nombre", "UTF-8") + "=" +
                    URLEncoder.encode(nombre, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
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
            while ((linea = reader.readLine()) != null){
                sb.append(linea);
                break;
            }
            return sb.toString();
        }catch (Exception e){
            return new String("Excepción: " + e.getMessage());
        }
    }

    protected void onPostExecute(Object resultado){
        String respuesta = resultado.toString();
        if(respuesta.compareTo("Correcto")==0){
            Toast.makeText(context.getApplicationContext(), "Usuario creado con éxito", Toast.LENGTH_LONG).show();
            //Aquí puede ir tanto a la actividad InicioSesion, como a la actividad Perfil
            Intent i = new Intent(context, InicioSesion.class);
            context.startActivity(i);
        }else {
            this.res.setTextColor(Color.parseColor("#CF000F"));
            this.res.setText("El usuario no ha podido ser registrado");
        }
    }

}
