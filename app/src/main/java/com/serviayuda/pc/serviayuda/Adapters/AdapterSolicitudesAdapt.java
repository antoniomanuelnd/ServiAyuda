package com.serviayuda.pc.serviayuda.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Actividades.VisitarPerfil;
import com.serviayuda.pc.serviayuda.Activitys.AceptaSolicitudAdaptActivity;
import com.serviayuda.pc.serviayuda.Activitys.CambiaEstadoAnuncioAdaptActivity;
import com.serviayuda.pc.serviayuda.Activitys.RechazaSolicitudAdaptActivity;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Fragments.SolicitudesFragmentAdapt;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;

/**
 * Created by PC on 17/03/2018.
 */

public class AdapterSolicitudesAdapt extends RecyclerView.Adapter<AdapterSolicitudesAdapt.VistaSolicitudesAdapt> implements View.OnClickListener {

    ArrayList<Solicitud> listSolicitud;
    SolicitudesFragmentAdapt context;
    private View.OnClickListener listener;
    Activity activity;
    DatabaseHelper databaseHelper;

    public AdapterSolicitudesAdapt(ArrayList<Solicitud> listSolicitud, SolicitudesFragmentAdapt context, Activity activity) {
        this.listSolicitud = listSolicitud;
        this.context = context;
        this.activity = activity;
        databaseHelper = new DatabaseHelper(activity);
    }

    //Enlace adaptar con archivo itemlist
    @Override
    public VistaSolicitudesAdapt onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistasolicitudadapt, parent, false);

        return new VistaSolicitudesAdapt(view);
    }

    @Override
    public void onBindViewHolder(VistaSolicitudesAdapt holder, final int position) {
        holder.asignarUsuarioAdapt(listSolicitud.get(position));
        //Listeners

        holder.perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, VisitarPerfil.class);
                Bundle mbundle = new Bundle();
                mbundle.putString("email", listSolicitud.get(position).getEmailProveedor());
                i.putExtras(mbundle);
                context.startActivity(i);
            }
        });

        holder.rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RechazaSolicitudAdaptActivity(context, activity, listSolicitud.get(position)).execute();
                removeItem(listSolicitud.get(position));
            }
        });

        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Modifica en la base de datos SQLite el estado de la solicitud
                databaseHelper.setEstadoSolicitud(listSolicitud.get(position), "En curso");

                //Obtengo la solicitud
                Solicitud solicitud = listSolicitud.get(position);

                //Elimino la solicitud de la lista
                removeItem(listSolicitud.get(position));

                //Envía al servidor la orden de modificar el estado de la solicitud
                new AceptaSolicitudAdaptActivity(context, activity, solicitud).execute();
                //Elimina de la base de datos SQLite y del servidor todas las solicitudes "Pendientes" y los elementos del recyclerview
                for (int i = 0; i < listSolicitud.size(); i++) {
                    new RechazaSolicitudAdaptActivity(context, activity, listSolicitud.get(i)).execute();
                    removeItem(listSolicitud.get(i));
                }

                //Cambia el estado del anuncio
                new CambiaEstadoAnuncioAdaptActivity(context, activity, solicitud.getEmailSolicitante(), "En curso").execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSolicitud.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    private void removeItem(Solicitud solicitud) {
        int position = listSolicitud.indexOf(solicitud);
        listSolicitud.remove(position);
        notifyItemRemoved(position);
    }

    public class VistaSolicitudesAdapt extends RecyclerView.ViewHolder {
        //Declaración de vistas
        TextView nombre;
        Button perfil;
        Button aceptar;
        Button rechazar;

        public VistaSolicitudesAdapt(View itemView) {
            super(itemView);
            //Inicialización de vistas
            nombre = itemView.findViewById(R.id.vistaSolicitudAdaptNombre);
            perfil = itemView.findViewById(R.id.vistaSolicitudAdaptPerfil);
            aceptar = itemView.findViewById(R.id.vistaSolicitudAdaptAceptar);
            rechazar = itemView.findViewById(R.id.vistaSolicitudAdaptRechazar);

            //Estilos
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(23.0f);
            gd.setColor(Color.parseColor("#005073"));
            itemView.setBackground(gd);

        }

        public void asignarUsuarioAdapt(Solicitud s) {
            Usuario us = databaseHelper.getUsuario(s.getEmailProveedor());
            String n = us.getNombre();
            String a = us.getApellidos();
            nombre.setText(n + " " + a);
        }
    }
}
