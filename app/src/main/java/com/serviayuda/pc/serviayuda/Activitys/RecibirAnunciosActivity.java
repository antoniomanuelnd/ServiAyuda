package com.serviayuda.pc.serviayuda.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.serviayuda.pc.serviayuda.Adapters.AdapterAnuncios;
import com.serviayuda.pc.serviayuda.Fragments.RecibirAnuncioFragment;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 28/02/2018.
 */

public class RecibirAnunciosActivity extends AsyncTask {

    private Context context;
    private Activity activity;
    private DatabaseHelper databaseHelper;
    private RecyclerView recycler;
    private String tipo;
    private AdapterAnuncios adapter;
    private RecibirAnuncioFragment recibirAnuncioFragment;
    private String email;

    public RecibirAnunciosActivity(Context context, Activity activity, DatabaseHelper databaseHelper, RecyclerView recycler, String tipo, AdapterAnuncios adapter, RecibirAnuncioFragment recibirAnuncioFragment, String email){
        this.context = context;
        this.activity = activity;
        this.databaseHelper = new DatabaseHelper(activity);
        this.recycler = recycler;
        this.tipo = tipo;
        this.adapter = adapter;
        this.recibirAnuncioFragment = recibirAnuncioFragment;
        this.email = email;
    }

    protected void onPreExecute() {
    }

    protected Object doInBackground(Object[] arg0) {
        //Método POST
        try {
            //Obtenemos los elementos a insertar en la BBDD

            //Generamos el link
            String link = "https://apptfg.000webhostapp.com/recibeAnuncios.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode("email", "UTF-8");

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
        String aux = res.toString();
        if(aux.compareTo("ERROR")!=0) {
            String respuesta[] = aux.split("<.>");
            Anuncio anuncio = new Anuncio();
            Anuncio anuncioPermanente;

            Solicitud sol = databaseHelper.getSolicitud(email);
            if(sol.getEstado()==null) {
                for (int i = 0; i < respuesta.length; i = i + 7) {

                    anuncio.setEmail(respuesta[i]);
                    anuncio.setNombre(respuesta[i + 1]);
                    anuncio.setTipoAnuncio(respuesta[i + 2]);
                    anuncio.setHoras(respuesta[i + 3]);
                    anuncio.setHoraDeseada(respuesta[i + 4]);
                    anuncio.setDescripcion(respuesta[i + 5]);
                    anuncio.setEstado(respuesta[i + 6]);

                    databaseHelper.addAnuncio(anuncio);
                }

            }else if(sol.getEstado().compareTo("En curso") == 0){
                anuncioPermanente = databaseHelper.getAnuncio(databaseHelper.getSolicitudEstado(email, "En curso").getEmailSolicitante());
                databaseHelper.setEliminaTodosLosAnuncios();
                databaseHelper.addAnuncio(anuncioPermanente);
            }
            final List<Anuncio> lista = databaseHelper.getAnunciosPorTipo(tipo);
            adapter = new AdapterAnuncios((ArrayList<Anuncio>)lista, recibirAnuncioFragment, activity);
            recycler.setAdapter(adapter);
        }
    }
}
