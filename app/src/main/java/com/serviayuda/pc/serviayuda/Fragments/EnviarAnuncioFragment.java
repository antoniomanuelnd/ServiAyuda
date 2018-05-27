package com.serviayuda.pc.serviayuda.Fragments;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.serviayuda.pc.serviayuda.Activitys.EnviaAnuncioActivity;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;


public class EnviarAnuncioFragment extends Fragment {

    Spinner spinnerTipos;
    Spinner spinnerHoras;
    TextView campoHora;
    TextView campoDescripción;
    Button botonEnviar;
    ManejadorPreferencias mp;

    AdapterView.OnItemSelectedListener spinnerListenerTipos;
    AdapterView.OnItemSelectedListener spinnerListenerHoras;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.enviaranuncio_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);

        iniciarVistas();
        iniciarListeners();
        return view;
    }

    private void iniciarVistas(){

        spinnerTipos = view.findViewById(R.id.envioAnuncioSpinnerTipo);
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipos.setAdapter(adapterTipos);
        spinnerTipos.setOnItemSelectedListener(spinnerListenerTipos);

        spinnerHoras = view.findViewById(R.id.envioAnuncioSpinnerHoras);
        ArrayAdapter<CharSequence> adapterHoras = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerHoras, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerHoras.setAdapter(adapterHoras);
        spinnerHoras.setOnItemSelectedListener(spinnerListenerHoras);

        campoHora = view.findViewById(R.id.envioAnuncioTextTime);
        campoDescripción = view.findViewById(R.id.envioAnuncioTextAnuncio);

        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);

        botonEnviar = view.findViewById(R.id.enviarAnuncioBoton);
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
        spinnerListenerHoras = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Anuncio anuncio = new Anuncio();
                anuncio.setEmail(mp.cargarPreferencias("KEY_EMAIL"));
                anuncio.setNombre(mp.cargarPreferencias("KEY_NOMBRE"));
                anuncio.setDescripcion(campoDescripción.getText().toString());
                anuncio.setTipoAnuncio(spinnerTipos.getSelectedItem().toString());
                anuncio.setHoras(spinnerHoras.getSelectedItem().toString());
                anuncio.setHoraDeseada(campoHora.getText().toString());
                enviaAnuncio(anuncio);
            }
        });
    }

    //Método que inserta/actualiza un anuncio en la base de datos
    private void enviaAnuncio(Anuncio anuncio){
        new EnviaAnuncioActivity(getContext(), anuncio).execute();
    }
}
