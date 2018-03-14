package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class VerSolicitudesFragment extends Fragment {


    ManejadorPreferencias mp;
    View view;
    String email;
    LinearLayout vistaSol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.versolicitudes_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);

        iniciarVistas();
        muestraSolicitudes();
        //iniciarListeners();

        return view;
    }

    private void iniciarVistas(){

        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        email = mp.cargarPreferencias("KEY_EMAIL");
        vistaSol = view.findViewById(R.id.verSolicitudesMuestraSolicitudes);

    }

    //private void iniciarListeners(){

    //}
    private void muestraSolicitudes(){
        new RecibeSolicitudActivity(getContext(), getActivity(), email, vistaSol).execute();
    }

}
