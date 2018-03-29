package com.serviayuda.pc.serviayuda.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Actividades.VisitarPerfil;
import com.serviayuda.pc.serviayuda.Activitys.PendienteDePuntuacionActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;

/**
 * Created by PC on 25/03/2018.
 */

public class ServicioEnCursoFragment extends Fragment {

    View view;
    LinearLayout serviciosLayout;
    DatabaseHelper databaseHelper;
    TextView info, ayuda, verPerfilDe;
    ImageView verPerfil;
    Button finalizar;
    String email;
    ManejadorPreferencias mp;
    Usuario usuario;
    Anuncio anuncio;
    Solicitud solicitud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.servicioencurso_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);
        iniciarVistas();
        return view;
    }

    private void iniciarVistas() {

        serviciosLayout = view.findViewById(R.id.serviciosEnCursoLayout);
        LinearLayout lay;
        databaseHelper = new DatabaseHelper(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        email = mp.cargarPreferencias("KEY_EMAIL");

        //Compruebo si hay alguna solicitud en curso
        Solicitud sol = databaseHelper.getSolicitud(email);
        if (sol.getEstado() == null) {
            lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistasinservicioencurso, null);
        } else if (sol.getEstado().compareTo("En curso") == 0) { //Si la hay
            solicitud = databaseHelper.getSolicitudEstado(email, "En curso");
            anuncio = databaseHelper.getAnuncio(solicitud.getEmailSolicitante());
            if (solicitud.getEmailSolicitante().compareTo(email) == 0) { //Compruebo si estoy desde la cuenta del solicitante/anunciante
                //Servicio solicitante

                usuario = databaseHelper.getUsuario(solicitud.getEmailProveedor());

                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.serviciosolicitante, null);
                info = lay.findViewById(R.id.servicioSolicitanteInfo);
                verPerfilDe = lay.findViewById(R.id.servicioSolicitanteEnCursoVerPerfilDe);
                ayuda = lay.findViewById(R.id.servicioSolicitanteAyuda);
                finalizar = lay.findViewById(R.id.servicioSolicitanteFinaliza);
                verPerfil = lay.findViewById(R.id.servicioSolicitanteEnCursoPerfil);
                verPerfil.setBackgroundResource(R.drawable.botonperfil);

                info.setText("El usuario " + usuario.getNombre() + " le va a prestar un servicio de " + anuncio.getTipoAnuncio().toLowerCase() + " a partir de " + determinaPronombre(anuncio.getHoraDeseada()) + anuncio.getHoraDeseada() + " durante " + anuncio.getHoras() + ".");
                verPerfilDe.setText("Ver perfil de " + usuario.getNombre());
                ayuda.setText("Podrás pulsar el botón FINALIZAR cuando el servicio haya sido cumplido. Posteriormente se le pedirá que puntúe a la persona que le ha ofrecido el servicio e inmediatamente podrá solicitar un nuevo servicio. En caso de que el servicio no haya podido ser realizado, por favor, finalice el servicio, puntúe negativamente y solicite de nuevo el servicio.");

                //Botón ver perfil
                verPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), VisitarPerfil.class);
                        Bundle mbundle = new Bundle();
                        mbundle.putString("email", solicitud.getEmailProveedor());
                        i.putExtras(mbundle);
                        getContext().startActivity(i);
                    }
                });

                //Botón finalizar
                finalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayout lay2 = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacioncliente, null);

                        //Modifico el estado de la solicitud en la base de datos del servidor
                        new PendienteDePuntuacionActivity(getContext(), getActivity(), solicitud.getEmailSolicitante(), "pdp", solicitud.getEmailProveedor()).execute();
                        //Modifico el estado de la solicitud en la base de datos SQLite
                        databaseHelper.setEstadoSolicitud(solicitud, "pdp");
                        serviciosLayout.removeAllViews();
                        serviciosLayout.addView(lay2);
                    }
                });

                //Estilos
                GradientDrawable gd = new GradientDrawable();
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadius(23.0f);
                gd.setColor(Color.parseColor("#005073"));
                lay.setBackground(gd);
            } else { //Estoy desde la cuenta del proveedor
                //Servicio proveedor

                usuario = databaseHelper.getUsuario(solicitud.getEmailSolicitante());

                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.servicioproveedor, null);
                info = lay.findViewById(R.id.servicioProveedorInfo);
                verPerfilDe = lay.findViewById(R.id.servicioProveedorEnCursoVerPerfilDe);
                ayuda = lay.findViewById(R.id.servicioProveedorAyuda);
                finalizar = lay.findViewById(R.id.servicioProveedorFinaliza);
                verPerfil = lay.findViewById(R.id.servicioProveedorEnCursoPerfil);
                verPerfil.setBackgroundResource(R.drawable.botonperfil);

                info.setText("Tienes que ofrecer un servicio de " + anuncio.getTipoAnuncio().toLowerCase() + " a " + usuario.getNombre() + " a partir de " + determinaPronombre(anuncio.getHoraDeseada()) + anuncio.getHoraDeseada() + " durante " + anuncio.getHoras() + ".");
                verPerfilDe.setText("Ver perfil de " + usuario.getNombre());
                ayuda.setText("Podrás pulsar el botón FINALIZAR cuando el servicio haya sido cumplido. Posteriormente se le pedirá que puntúe a la persona a la que has ofrecido el servicio e inmediatamente podrás satisfacer nuevos servicios. En caso de que el servicio no haya podido ser realizado, por favor, finalice el servicio y puntúe negativamente.");

                //Botón ver perfil
                verPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), VisitarPerfil.class);
                        Bundle mbundle = new Bundle();
                        mbundle.putString("email", solicitud.getEmailSolicitante());
                        i.putExtras(mbundle);
                        getContext().startActivity(i);
                    }
                });

                //Botón finalizar
                finalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayout lay2 = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacionproveedor, null);

                        //Modifico el estado de la solicitud en la base de datos del servidor
                        new PendienteDePuntuacionActivity(getContext(), getActivity(), solicitud.getEmailSolicitante(), "pdp", solicitud.getEmailSolicitante()).execute();
                        //Modifico el estado de la solicitud en la base de datos SQLite
                        databaseHelper.setEstadoSolicitud(solicitud, "pdp");
                        serviciosLayout.removeAllViews();
                        serviciosLayout.addView(lay2);
                    }
                });
                //Estilos
                GradientDrawable gd = new GradientDrawable();
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadius(23.0f);
                gd.setColor(Color.parseColor("#005073"));
                lay.setBackground(gd);
            }
        } else { //Si no hay solicitud en curso
            if(sol.getEstado().compareTo("Pendiente")==0){
                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistasinservicioencurso, null);
            }else if (sol.getEmailSolicitante().compareTo(email) == 0) { //Vista de puntuación del cliente
                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacioncliente, null);
            } else { //Vista de puntuación del proveedor
                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacionproveedor, null);

            }
        }
        serviciosLayout.addView(lay);
}

    public String determinaPronombre(String hora) {
        Integer primeraParte = Integer.parseInt(hora.substring(0, 2));
        if ((primeraParte >= 10) || (primeraParte == 0)) {
            return "las ";
        } else {
            return "la ";
        }
    }
}
