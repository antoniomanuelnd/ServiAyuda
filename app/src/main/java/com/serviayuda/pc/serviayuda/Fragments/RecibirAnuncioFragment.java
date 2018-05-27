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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.serviayuda.pc.serviayuda.Activitys.RecibirAnunciosActivity;
import com.serviayuda.pc.serviayuda.Adapters.AdapterAnuncios;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;
import java.util.List;


public class RecibirAnuncioFragment extends Fragment {

    Button botonMostrarAnuncios;
    View view;
    Usuario usuario;
    DatabaseHelper databaseHelper;
    RecyclerView recycler;
    AdapterAnuncios adapter;
    ManejadorPreferencias mp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.recibiranuncio_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);

        iniciarVistas();
        iniciarListeners();

        return view;
    }

    private void iniciarVistas(){

        botonMostrarAnuncios = view.findViewById(R.id.recibirAnunciosBotonMostrar);
        databaseHelper = new DatabaseHelper(getActivity());

        recycler = view.findViewById(R.id.recyclerAnuncios);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));

    }

    private void iniciarListeners(){

        botonMostrarAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAnuncios();
            }
        });
    }
    private void mostrarAnuncios(){

        new RecibirAnunciosActivity(getContext(), getActivity(), databaseHelper, recycler, usuario.getTipoServicio(), usuario.getCiudad(), adapter, this, mp.cargarPreferencias("KEY_EMAIL")).execute();

    }
}
