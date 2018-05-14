package com.serviayuda.pc.serviayuda.Activitys;

        import android.app.Activity;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.widget.Toast;

        import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
        import com.serviayuda.pc.serviayuda.Objetos.Usuario;
        import com.tapadoo.alerter.Alert;
        import com.tapadoo.alerter.Alerter;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.URL;
        import java.net.URLConnection;
        import java.net.URLEncoder;

/**
 * Created by PC on 01/05/2018.
 */

public class EditarCampoActivity extends AsyncTask {

    private Context context;
    private Usuario usuario;
    private String whatSQLite, whereSQLite;
    private DatabaseHelper databaseHelper;

    public EditarCampoActivity(Context context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
        this.databaseHelper = new DatabaseHelper(context);
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {

            String campoACambiar = (String) arg0[0];
            String info = (String) arg0[1];
            whereSQLite = campoACambiar;
            whatSQLite = info;

            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/editarPerfil.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(usuario.getEmail(), "UTF-8");
            data += "&" + URLEncoder.encode("campoACambiar", "UTF-8") + "=" +
                    URLEncoder.encode(campoACambiar, "UTF-8");
            data += "&" + URLEncoder.encode("info", "UTF-8") + "=" +
                    URLEncoder.encode(info, "UTF-8");


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

            switch (whereSQLite) {
                case "nombre":
                    usuario.setNombre(whatSQLite);
                    break;
                case "apellidos":
                    usuario.setApellidos(whatSQLite);
                    break;
                case "edad":
                    usuario.setEdad(whatSQLite);
                    break;
                case "descripcion":
                    usuario.setDescripcion(whatSQLite);
                    break;
                case "experiencia":
                    usuario.setExperiencia(whatSQLite);
                    break;
                case "ciudad":
                    usuario.setCiudad(whatSQLite);
                    break;
                case "localidad":
                    usuario.setLocalidad(whatSQLite);
                    break;
                case "direccion":
                    usuario.setDireccion(whatSQLite);
                    break;
                case "codigo_postal":
                    usuario.setCodigoPostal(whatSQLite);
                    break;
            }
            databaseHelper.addUsuario(usuario);

            Toast.makeText(context.getApplicationContext(), "Editado con éxito", Toast.LENGTH_LONG).show();
            //Aquí puede ir tanto a la actividad InicioSesion, como a la actividad Perfil

        } else {
            Toast.makeText(context.getApplicationContext(), "No se ha podido editar", Toast.LENGTH_LONG).show();
        }
    }
}
