package com.serviayuda.pc.serviayuda.Activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.Actividades.InicioSesion;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;

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
    private TextView campoResultado;
    private Usuario usuario;

    public RegistroActivity(Context context, TextView campoResultado, Usuario usuario){
        this.context = context;
        this.campoResultado = campoResultado;
        this.usuario = usuario;
    }

    protected void onPreExecute(){
    }

    protected Object doInBackground(Object[] arg0){
        //Método POST
        try{
            String link;
            String data;
            if(usuario.getTipoPerfil().compareTo("Proveedor")==0){
                //Generamos el link
                link = "https://apptfg.000webhostapp.com/registroProveedor.php";
                data  = URLEncoder.encode("nombre", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getNombre(), "UTF-8");
                data += "&" + URLEncoder.encode("apellidos", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getApellidos(), "UTF-8");
                data += "&" + URLEncoder.encode("edad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getEdad(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode((String) arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("tipoperfil", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getTipoPerfil(), "UTF-8");
                data += "&" + URLEncoder.encode("tiposervicio", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getTipoServicio(), "UTF-8");
                data += "&" + URLEncoder.encode("ciudad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getCiudad(), "UTF-8");
                data += "&" + URLEncoder.encode("localidad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getLocalidad(), "UTF-8");
            }else{
                //Generamos el link
                link = "https://apptfg.000webhostapp.com/registroSolicitante.php";
                data  = URLEncoder.encode("nombre", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getNombre(), "UTF-8");
                data += "&" + URLEncoder.encode("apellidos", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getApellidos(), "UTF-8");
                data += "&" + URLEncoder.encode("edad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getEdad(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode((String) arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("tipoperfil", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getTipoPerfil(), "UTF-8");
                data += "&" + URLEncoder.encode("ciudad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getCiudad(), "UTF-8");
                data += "&" + URLEncoder.encode("localidad", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getLocalidad(), "UTF-8");
                data += "&" + URLEncoder.encode("direccion", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getDireccion(), "UTF-8");
                data += "&" + URLEncoder.encode("codigopostal", "UTF-8") + "=" +
                        URLEncoder.encode(usuario.getCodigoPostal(), "UTF-8");
            }

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
            this.campoResultado.setVisibility(View.VISIBLE);
            this.campoResultado.setTextColor(Color.GREEN);
            this.campoResultado.setText("Usuario creado con éxito");
            Toast.makeText(context.getApplicationContext(), "Usuario creado con éxito", Toast.LENGTH_LONG).show();
            //Aquí puede ir tanto a la actividad InicioSesion, como a la actividad Perfil
            Intent i = new Intent(context, InicioSesion.class);
            context.startActivity(i);
        }else {
            this.campoResultado.setVisibility(View.VISIBLE);
            this.campoResultado.setTextColor(Color.RED);
            this.campoResultado.setText("El usuario no ha podido ser registrado");
        }
    }

}
