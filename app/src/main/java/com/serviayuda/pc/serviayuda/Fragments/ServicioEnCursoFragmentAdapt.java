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

import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudEnCursoActivity;
import com.serviayuda.pc.serviayuda.Adapters.AdapterSolicitudesAdapt;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 13/05/2018.
 */

public class ServicioEnCursoFragmentAdapt extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_servicioencurso_adapt, container, false);


        return view;
    }

}
