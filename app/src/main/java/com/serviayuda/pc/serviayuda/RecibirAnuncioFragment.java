package com.serviayuda.pc.serviayuda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;


public class RecibirAnuncioFragment extends Fragment {

    Spinner spinnerTipos;
    AdapterView.OnItemSelectedListener spinnerListenerTipos;
    View view;

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
        spinnerTipos = view.findViewById(R.id.recibirAnuncioSpinnerTipo);
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipos.setAdapter(adapterTipos);
        spinnerTipos.setOnItemSelectedListener(spinnerListenerTipos);
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
    }
}
