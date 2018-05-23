package com.serviayuda.pc.serviayuda.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudEnCursoActivity;
import com.serviayuda.pc.serviayuda.Adapters.AdapterSolicitudes;
import com.serviayuda.pc.serviayuda.Adapters.AdapterSolicitudesAdapt;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 13/05/2018.
 */

public class SolicitudesFragmentAdapt extends Fragment {

    Button actualizar;
    ManejadorPreferencias mp;
    String email;
    RecyclerView recycler;
    AdapterSolicitudesAdapt adapter;
    DatabaseHelper databaseHelper;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_solicitudes_adapt, container, false);

        iniciarVistas();
        iniciarListeners();

        new RecibeSolicitudActivity(getContext(), getActivity(), email).execute();
        return view;
    }

    public void iniciarVistas() {

        actualizar = view.findViewById(R.id.actualizarAdapt);

        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        email = mp.cargarPreferencias("KEY_EMAIL");
        recycler = view.findViewById(R.id.recyclerSolicitudesAdapt);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        databaseHelper = new DatabaseHelper(getActivity());
        new RecibeSolicitudEnCursoActivity(getContext(), getActivity(), email).execute();

    }

    public void iniciarListeners() {

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSolicitudes();
            }
        });
    }

    private void mostrarSolicitudes() {

        final List<Solicitud> lista = databaseHelper.getSolicitudesPendientes(email);
        adapter = new AdapterSolicitudesAdapt((ArrayList<Solicitud>) lista, this, getActivity());
        recycler.setAdapter(adapter);
        new RecibeSolicitudActivity(getContext(), getActivity(), email).execute();
    }
}
