package com.serviayuda.pc.serviayuda.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.Manifest;
import com.serviayuda.pc.serviayuda.R;

import java.util.Locale;

/**
 * Created by PC on 13/05/2018.
 */

public class ServiciosFragmentAdapt extends Fragment {

    private View view;
    private TextToSpeech speaker;
    ImageButton sonidoEmergencia, sonidoAsistencia, sonidoPeluqueria, sonidoFisioterapia, sonidoLimpieza;
    Button botonEmergencia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_servicios_adapt, container, false);
        FrameLayout fr = view.findViewById(R.id.frame_servicios_adapt);

        iniciarVistas();
        iniciarListeners();
        return view;
    }

    private void iniciarVistas() {

        sonidoEmergencia = view.findViewById(R.id.botonEmergenciaSonido);
        sonidoAsistencia = view.findViewById(R.id.botonAsistenciaSonido);
        sonidoPeluqueria = view.findViewById(R.id.botonPeluqueriaSonido);
        sonidoFisioterapia = view.findViewById(R.id.botonFisioterapiaSonido);
        sonidoLimpieza = view.findViewById(R.id.botonLimpiezaSonido);

        botonEmergencia = view.findViewById(R.id.botonEmergencia);
    }

    private void iniciarListeners() {
        speaker = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanish = new Locale("spa", "MEX");
                    int res = speaker.setLanguage(locSpanish);

                    if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Lenguaje no soportado");
                    } else {
                        sonidoEmergencia.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Fallo al inicializar");
                }
            }
        });

        sonidoEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Solicitar una emergencia");
            }
        });
        sonidoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Solicitar un servicio de asistencia");
            }
        });
        sonidoPeluqueria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Solicitar un servicio de peluquería");
            }
        });
        sonidoFisioterapia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Solicitar un servicio de fisioterapia");
            }
        });
        sonidoLimpieza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Solicitar un servicio de limpieza");
            }
        });

        botonEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.confirmacion_emergencia, null);

                Button si, no;
                si = view.findViewById(R.id.emergenciaSi);
                no = view.findViewById(R.id.emergenciaNo);

                speak("¿DESEA REALIZAR UNA LLAMADA DE EMERGENCIA?");

                mBuilder.setView(view);
                final AlertDialog dialog = mBuilder.create();
                si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak("Llamando a emergencias");
                        llamar();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void llamar(){
        String telefono = "112";
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] { android.Manifest.permission.CALL_PHONE}, 1);
        }else{
            String dial = "tel:" + telefono;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                llamar();
            } else {
                Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void speak(String cadena) {
        speaker.setPitch(1.0f);
        speaker.setSpeechRate(0.8f);
        speaker.speak(cadena, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        if (speaker != null) {
            speaker.stop();
            speaker.shutdown();
        }
        super.onDestroy();
    }
}
