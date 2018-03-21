package com.serviayuda.pc.serviayuda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.recibiranuncio_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);

        iniciarVistas();
        iniciarListeners();
        new RecibirAnunciosActivity(getContext(), getActivity(), databaseHelper).execute();
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

        final List<Anuncio> lista = databaseHelper.getAnunciosPorTipo(spinnerTipos.getSelectedItem().toString());
        adapter = new AdapterAnuncios((ArrayList<Anuncio>) lista, this, getActivity());
        recycler.setAdapter(adapter);

    }
}
