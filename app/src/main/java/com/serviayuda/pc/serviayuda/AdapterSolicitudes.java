package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 17/03/2018.
 */

public class AdapterSolicitudes extends RecyclerView.Adapter<AdapterSolicitudes.VistaSolicitudes> implements View.OnClickListener{

    ArrayList<Solicitud> listSolicitud;
    VerSolicitudesFragment context;
    private View.OnClickListener listener;
    Activity activity;
    DatabaseHelper databaseHelper;

    public AdapterSolicitudes(ArrayList<Solicitud> listUsuarios, VerSolicitudesFragment context, Activity activity){
        this.listSolicitud = listUsuarios;
        this.context = context;
        this.activity = activity;
        databaseHelper = new DatabaseHelper(activity);
    }

    //Enlace adaptar con archivo itemlist
    @Override
    public VistaSolicitudes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistasolicitud, parent, false);
        return new VistaSolicitudes(view);
    }

    @Override
    public void onBindViewHolder(VistaSolicitudes holder, final int position) {
        holder.asignarUsuario(listSolicitud.get(position));
        //Listeners

        holder.perfil.setOnClickListener(new View.OnClickListener(){
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
                new RechazaSolicitudActivity(context, activity, listSolicitud.get(position)).execute();
                removeItem(listSolicitud.get(position));
            }
        });

        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return listSolicitud.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    private void removeItem(Solicitud solicitud){
        int position = listSolicitud.indexOf(solicitud);
        listSolicitud.remove(position);
        notifyItemRemoved(position);
    }

    public class VistaSolicitudes extends RecyclerView.ViewHolder {
        //Declaración de vistas
        TextView nombre;
        ImageView perfil;
        ImageView aceptar;
        ImageView rechazar;

        public VistaSolicitudes(View itemView) {
            super(itemView);
            //Inicialización de vistas
            nombre = itemView.findViewById(R.id.vistaSolicitudNombre);
            perfil = itemView.findViewById(R.id.vistaSolicitudPerfil);
            aceptar = itemView.findViewById(R.id.vistaSolicitudAceptar);
            rechazar = itemView.findViewById(R.id.vistaSolicitudRechazar);

            //Botones
            perfil.setBackgroundResource(R.drawable.botonperfil);
            aceptar.setBackgroundResource(R.drawable.botontick);
            rechazar.setBackgroundResource(R.drawable.botoncross);

            //Estilos
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(23.0f);
            gd.setColor(Color.parseColor("#005073"));
            itemView.setBackground(gd);

        }

        public void asignarUsuario(Solicitud s){
            Usuario us = databaseHelper.getUsuario(s.getEmailProveedor());
            String n = us.getNombre();
            String a = us.getApellidos();
            nombre.setText(n + " " + a);
        }
    }
}
