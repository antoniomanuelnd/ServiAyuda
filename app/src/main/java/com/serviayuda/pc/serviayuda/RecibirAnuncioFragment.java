package com.serviayuda.pc.serviayuda;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecibirAnuncioFragment extends Fragment {

    Spinner spinnerTipos;
    Button botonMostrarAnuncios;
    AdapterView.OnItemSelectedListener spinnerListenerTipos;
    View view;
    LinearLayout scrollVistaAnuncio;
    DatabaseHelper databaseHelper;

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
        scrollVistaAnuncio = view.findViewById(R.id.layoutMostrarAnuncios);
        databaseHelper = new DatabaseHelper(getActivity());
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
        scrollVistaAnuncio.removeAllViews();
        List<Anuncio> lista = databaseHelper.getAnunciosPorTipo(spinnerTipos.getSelectedItem().toString());
        for (int i=0; i<lista.size(); i++){
            final LinearLayout anun = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistaanuncio, null);
            TextView nombre = anun.findViewById(R.id.muestraNombreAnuncio);
            TextView tipo_anuncio = anun.findViewById(R.id.muestraTipoAnuncio);
            TextView descripcion = anun.findViewById(R.id.muestraDescripcion);
            final Anuncio an = lista.get(i);
            nombre.setText(an.getNombre());
            tipo_anuncio.setText(an.getTipoAnuncio());
            descripcion.setText(an.getDescripcion());

            //Estilo

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(23.0f);
            gd.setColor(Color.parseColor("#1D7196"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8,3,8,3);
            anun.setBackground(gd);

            //Añade un listener a cada anuncio
            anun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), AnuncioAmpliado.class);
                    Bundle mbundle = new Bundle();
                    mbundle.putParcelable("anuncio", an);
                    i.putExtras(mbundle);
                    Usuario usuario = new Usuario();
                    new RecibeUsuarioPerfilActivity(getContext(), getActivity()).execute(an.getEmail());
                    startActivity(i);
                }
            });

            //Añade el anuncio a la vista
            scrollVistaAnuncio.addView(anun, lp);
        }
    }
}
