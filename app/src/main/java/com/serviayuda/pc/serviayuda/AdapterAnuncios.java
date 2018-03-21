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

/**
 * Created by PC on 19/03/2018.
 */

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.VistaAnuncios> implements View.OnClickListener{

    ArrayList<Anuncio> listAnuncios;
    private View.OnClickListener listener;
    RecibirAnuncioFragment context;
    Activity activity;

    public AdapterAnuncios(ArrayList<Anuncio> listAnuncios, RecibirAnuncioFragment context, Activity activity){
        this.listAnuncios = listAnuncios;
        this.context = context;
        this.activity = activity;
    }

    //Enlace adaptar con archivo itemlist
    @Override
    public AdapterAnuncios.VistaAnuncios onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistaanuncio, parent, false);
        view.setOnClickListener(this);
        return new AdapterAnuncios.VistaAnuncios(view);
    }

    @Override
    public void onBindViewHolder(AdapterAnuncios.VistaAnuncios holder, final int position) {
        holder.asignarAnuncio(listAnuncios.get(position));
        holder.ampliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AnuncioAmpliado.class);
                Bundle mbundle = new Bundle();
                mbundle.putParcelable("anuncio", listAnuncios.get(position));
                i.putExtras(mbundle);
                new RecibeUsuarioPerfilActivity(context, activity).execute(listAnuncios.get(position).getEmail());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAnuncios.size();
    }


    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class VistaAnuncios extends RecyclerView.ViewHolder {
        //Declaración de vistas
        TextView nombre;
        TextView tipo_anuncio;
        TextView descripcion;
        ImageView ampliar;

        public VistaAnuncios(View itemView) {
            super(itemView);
            //Inicialización de vistas

            nombre = itemView.findViewById(R.id.muestraNombreAnuncio);
            tipo_anuncio = itemView.findViewById(R.id.muestraTipoAnuncio);
            descripcion = itemView.findViewById(R.id.muestraDescripcion);
            ampliar = itemView.findViewById(R.id.vistaAnuncioObservar);
            ampliar.setBackgroundResource(R.drawable.botoneye);

            //Estilos

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(23.0f);
            gd.setColor(Color.parseColor("#1D7196"));
            itemView.setBackground(gd);

        }

        public void asignarAnuncio(Anuncio a){
            nombre.setText(a.getNombre());
            tipo_anuncio.setText(a.getTipoAnuncio());
            descripcion.setText(a.getDescripcion());

        }
    }
}
