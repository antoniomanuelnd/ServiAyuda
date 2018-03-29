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
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;
import java.util.List;


public class RecibirAnuncioFragment extends Fragment {

    Spinner spinnerTipos;
    Button botonMostrarAnuncios;
    AdapterView.OnItemSelectedListener spinnerListenerTipos;
    View view;

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
        spinnerTipos = view.findViewById(R.id.recibirAnunciosSpinnerTipo);
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipos.setAdapter(adapterTipos);
        spinnerTipos.setOnItemSelectedListener(spinnerListenerTipos);
        botonMostrarAnuncios = view.findViewById(R.id.recibirAnunciosBotonMostrar);
        databaseHelper = new DatabaseHelper(getActivity());

        recycler = view.findViewById(R.id.recyclerAnuncios);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
    }

    private void iniciarListeners(){
        spinnerListenerTipos = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        botonMostrarAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAnuncios();
            }
        });
    }
    private void mostrarAnuncios(){

        new RecibirAnunciosActivity(getContext(), getActivity(), databaseHelper, recycler, spinnerTipos.getSelectedItem().toString(), adapter, this, mp.cargarPreferencias("KEY_EMAIL")).execute();

    }
}
