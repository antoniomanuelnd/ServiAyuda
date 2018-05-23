package com.serviayuda.pc.serviayuda.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudEnCursoActivity;
import com.serviayuda.pc.serviayuda.Adapters.AdapterSolicitudes;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;

import java.util.ArrayList;
import java.util.List;


public class VerSolicitudesFragment extends Fragment {


    ManejadorPreferencias mp;
    View view;
    String email;
    RecyclerView recycler;
    AdapterSolicitudes adapter;
    Button actualizar;
    DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.versolicitudes_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);

        iniciarVistas();
        iniciarListeners();

        new RecibeSolicitudActivity(getContext(), getActivity(), email).execute();
        return view;
    }

    private void iniciarVistas() {

        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        email = mp.cargarPreferencias("KEY_EMAIL");
        recycler = view.findViewById(R.id.recyclerSolicitudes);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        actualizar = view.findViewById(R.id.actualizar);
        databaseHelper = new DatabaseHelper(getActivity());
        new RecibeSolicitudEnCursoActivity(getContext(), getActivity(), email).execute();
    }

    private void iniciarListeners() {
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSolicitudes();
            }
        });
    }

    private void mostrarSolicitudes() {

        final List<Solicitud> lista = databaseHelper.getSolicitudesPendientes(email);
        adapter = new AdapterSolicitudes((ArrayList<Solicitud>) lista, this, getActivity());
        recycler.setAdapter(adapter);
        new RecibeSolicitudActivity(getContext(), getActivity(), email).execute();
    }


}
