package com.serviayuda.pc.serviayuda.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.serviayuda.pc.serviayuda.Actividades.VisitarPerfil;
import com.serviayuda.pc.serviayuda.Activitys.EnviaPuntuacionActivity;
import com.serviayuda.pc.serviayuda.Activitys.PendienteDePuntuacionActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudActivity;
import com.serviayuda.pc.serviayuda.Activitys.RecibeSolicitudEnCursoActivity;
import com.serviayuda.pc.serviayuda.Adapters.AdapterSolicitudesAdapt;
import com.serviayuda.pc.serviayuda.BBDD.DatabaseHelper;
import com.serviayuda.pc.serviayuda.Objetos.Anuncio;
import com.serviayuda.pc.serviayuda.Objetos.Solicitud;
import com.serviayuda.pc.serviayuda.Objetos.Usuario;
import com.serviayuda.pc.serviayuda.Preferencias.ManejadorPreferencias;
import com.serviayuda.pc.serviayuda.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by PC on 13/05/2018.
 */

public class ServicioEnCursoFragmentAdapt extends Fragment {

    View view;
    LinearLayout serviciosLayout;
    DatabaseHelper databaseHelper;
    TextView info, ayuda, verPerfilDe;
    ImageView verPerfil, infoSonido, ayudaSonido;
    Button finalizar;
    String email;
    ManejadorPreferencias mp;
    Usuario usuario;
    Anuncio anuncio;
    Solicitud solicitud;

    //Rating
    RatingBar ratingBar;
    Button finalizarRating;
    CheckBox respetoSi, respetoNo, servicioSi, servicioNo, recomendarSi, recomendarNo;

    String sInfo, sAyuda;
    private TextToSpeech speaker;
    Float rating;
    Boolean bRespeto = null, bServicio = null, bRecomendar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_servicioencurso_adapt, container, false);
        iniciarVistas();
        return view;
    }

    private void iniciarVistas() {

        serviciosLayout = view.findViewById(R.id.serviciosEnCursoAdaptLayout);
        LinearLayout lay;
        databaseHelper = new DatabaseHelper(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("SESION", Activity.MODE_PRIVATE);
        mp = new ManejadorPreferencias(preferences);
        email = mp.cargarPreferencias("KEY_EMAIL");

        //TextToSpeech
        speaker = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanish = new Locale("spa", "ESP");
                    int res = speaker.setLanguage(locSpanish);

                    if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Lenguaje no soportado");
                    }
                } else {
                    Log.e("TTS", "Fallo al inicializar");
                }
            }
        });

        //Compruebo si hay alguna solicitud en curso
        Solicitud sol = databaseHelper.getSolicitud(email);
        if (sol.getEstado() == null) {
            lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistasinservicioencurso, null);
            serviciosLayout.addView(lay);
        } else if (sol.getEstado().compareTo("En curso") == 0) { //Si la hay
            solicitud = databaseHelper.getSolicitudEstado(email, "En curso");
            anuncio = databaseHelper.getAnuncio(sol.getEmailSolicitante());
            if (solicitud.getEmailSolicitante().compareTo(email) == 0) { //Compruebo si estoy desde la cuenta del solicitante/anunciante
                //Servicio solicitante

                usuario = databaseHelper.getUsuario(sol.getEmailProveedor());

                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.serviciosolicitanteadapt, null);
                info = lay.findViewById(R.id.servicioSolicitanteInfoAdapt);
                verPerfilDe = lay.findViewById(R.id.servicioSolicitanteEnCursoVerPerfilDeAdapt);
                ayuda = lay.findViewById(R.id.servicioSolicitanteAyudaAdapt);
                finalizar = lay.findViewById(R.id.servicioSolicitanteFinalizaAdapt);
                verPerfil = lay.findViewById(R.id.servicioSolicitanteEnCursoPerfilAdapt);
                verPerfil.setBackgroundResource(R.drawable.botonperfil);
                infoSonido = lay.findViewById(R.id.servicioSolicitanteInfoAdaptSonido);
                ayudaSonido = lay.findViewById(R.id.servicioSolicitanteAyudaAdaptSonido);

                if (anuncio.getHoraDeseada().compareTo("Hoy lo antes posible") == 0) {
                    sInfo = "El usuario " + usuario.getNombre() + " le va a prestar un servicio de " + anuncio.getTipoAnuncio().toLowerCase() + " hoy lo antes posible durante " + anuncio.getHoras() + ".";
                    info.setText(sInfo.toUpperCase());
                } else {
                    sInfo = "El usuario " + usuario.getNombre() + " le va a prestar un servicio de " + anuncio.getTipoAnuncio().toLowerCase() + " a partir de " + determinaPronombre(anuncio.getHoraDeseada()) + anuncio.getHoraDeseada() + " durante " + anuncio.getHoras() + ".";
                    info.setText(sInfo.toUpperCase());
                }
                verPerfilDe.setText("Ver perfil de " + usuario.getNombre());
                sAyuda = "Podrás pulsar el botón FINALIZAR cuando el servicio haya sido cumplido. Posteriormente se le pedirá que puntúe a la persona que le ha ofrecido el servicio e inmediatamente podrá solicitar un nuevo servicio. En caso de que el servicio no haya podido ser realizado, por favor, finalice el servicio, puntúe negativamente y solicite un nuevo servicio si lo desea.";
                ayuda.setText(sAyuda.toUpperCase());

                infoSonido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak(sInfo);
                    }
                });

                ayudaSonido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak(sAyuda);
                    }
                });

                //Botón ver perfil
                verPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), VisitarPerfil.class);
                        Bundle mbundle = new Bundle();
                        mbundle.putString("email", solicitud.getEmailProveedor());
                        i.putExtras(mbundle);
                        getContext().startActivity(i);
                    }
                });

                //Botón finalizar
                finalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayout lay2 = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacionclienteadapt, null);

                        //Modifico el estado de la solicitud en la base de datos del servidor
                        new PendienteDePuntuacionActivity(getContext(), getActivity(), solicitud.getEmailSolicitante(), "pdp", solicitud.getEmailProveedor()).execute();
                        //Modifico el estado de la solicitud en la base de datos SQLite
                        databaseHelper.setEstadoSolicitud(solicitud, "pdp");
                        serviciosLayout.removeAllViews();
                        serviciosLayout.addView(lay2);
                    }
                });

                //Estilos
                GradientDrawable gd = new GradientDrawable();
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadius(23.0f);
                gd.setColor(Color.parseColor("#005073"));
                lay.setBackground(gd);
                serviciosLayout.addView(lay);
            }
        } else { //Si no hay solicitud en curso
            if (sol.getEstado().compareTo("Pendiente") == 0) {
                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistasinservicioencurso, null);
                serviciosLayout.addView(lay);
            } else { //Vista de puntuación del cliente
                lay = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistapuntuacionclienteadapt, null);
                //Método rating del cliente
                ratingCliente(lay, sol);
                serviciosLayout.addView(lay);
            }
        }

    }

    public String determinaPronombre(String hora) {

        Integer primeraParte = Integer.parseInt(hora.substring(0, 2));
        if ((primeraParte >= 10) || (primeraParte == 0)) {
            return "las ";
        } else {
            return "la ";
        }

    }

    public void ratingCliente(LinearLayout lay, final Solicitud sol) {

        Button botonExcelente, botonGenial, botonBueno, botonRegular, botonMal, botonRespetoSi, botonRespetoNo,
                botonServicioSi, botonServicioNo, botonRecomendarSi, botonRecomendarNo;


        final ImageView tickExcelente, excelenteSonido, tickGenial, genialSonido, tickBueno, buenoSonido,
                tickRegular, regularSonido, tickMal, malSonido, tickRespetoSi, tickRespetoNo, respetoSiSonido,
                respetoNoSonido, tickServicioSi, servicioSiSonido, tickServicioNo, servicioNoSonido,
                tickRecomendarSi, tickRecomendarNo, recomendarSiSonido, recomendarNoSonido;


        //Botones
        botonExcelente = lay.findViewById(R.id.botonExcelente);
        botonGenial = lay.findViewById(R.id.botonGenial);
        botonBueno = lay.findViewById(R.id.botonBueno);
        botonRegular = lay.findViewById(R.id.botonRegular);
        botonMal = lay.findViewById(R.id.botonMal);

        botonRespetoSi = lay.findViewById(R.id.botonRespetoSi);
        botonRespetoNo = lay.findViewById(R.id.botonRespetoNo);

        botonServicioSi = lay.findViewById(R.id.botonServicioSi);
        botonServicioNo = lay.findViewById(R.id.botonServicioNo);

        botonRecomendarSi = lay.findViewById(R.id.botonRecomendarSi);
        botonRecomendarNo = lay.findViewById(R.id.botonRecomendarNo);

        //ImageViews

        tickExcelente = lay.findViewById(R.id.tickExcelente);
        excelenteSonido = lay.findViewById(R.id.excelenteSonido);
        tickGenial = lay.findViewById(R.id.tickGenial);
        genialSonido = lay.findViewById(R.id.genialSonido);
        tickBueno = lay.findViewById(R.id.tickBueno);
        buenoSonido = lay.findViewById(R.id.buenoSonido);
        tickRegular = lay.findViewById(R.id.tickRegular);
        regularSonido = lay.findViewById(R.id.regularSonido);
        tickMal = lay.findViewById(R.id.tickMal);
        malSonido = lay.findViewById(R.id.malSonido);

        tickRespetoSi = lay.findViewById(R.id.tickRespetoSi);
        respetoSiSonido = lay.findViewById(R.id.respetoSiSonido);

        tickRespetoNo = lay.findViewById(R.id.tickRespetoNo);
        respetoNoSonido = lay.findViewById(R.id.respetoNoSonido);

        tickServicioSi = lay.findViewById(R.id.tickServicioSi);
        servicioSiSonido = lay.findViewById(R.id.servicioSiSonido);

        tickServicioNo = lay.findViewById(R.id.tickServicioNo);
        servicioNoSonido = lay.findViewById(R.id.servicioNoSonido);

        tickRecomendarSi = lay.findViewById(R.id.tickRecomendarSi);
        recomendarSiSonido = lay.findViewById(R.id.recomendarSiSonido);

        tickRecomendarNo = lay.findViewById(R.id.tickRecomendarNo);
        recomendarNoSonido = lay.findViewById(R.id.recomendarNoSonido);

        finalizarRating = lay.findViewById(R.id.clienteFinalizar);

        //Listeners

        botonExcelente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickExcelente.setVisibility(View.VISIBLE);
                tickGenial.setVisibility(View.INVISIBLE);
                tickBueno.setVisibility(View.INVISIBLE);
                tickRegular.setVisibility(View.INVISIBLE);
                tickMal.setVisibility(View.INVISIBLE);
                rating = 5f;
            }
        });

        botonGenial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickExcelente.setVisibility(View.INVISIBLE);
                tickGenial.setVisibility(View.VISIBLE);
                tickBueno.setVisibility(View.INVISIBLE);
                tickRegular.setVisibility(View.INVISIBLE);
                tickMal.setVisibility(View.INVISIBLE);
                rating = 4f;
            }
        });

        botonBueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickExcelente.setVisibility(View.INVISIBLE);
                tickGenial.setVisibility(View.INVISIBLE);
                tickBueno.setVisibility(View.VISIBLE);
                tickRegular.setVisibility(View.INVISIBLE);
                tickMal.setVisibility(View.INVISIBLE);
                rating = 3f;
            }
        });

        botonRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickExcelente.setVisibility(View.INVISIBLE);
                tickGenial.setVisibility(View.INVISIBLE);
                tickBueno.setVisibility(View.INVISIBLE);
                tickRegular.setVisibility(View.VISIBLE);
                tickMal.setVisibility(View.INVISIBLE);
                rating = 2f;
            }
        });

        botonMal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickExcelente.setVisibility(View.INVISIBLE);
                tickGenial.setVisibility(View.INVISIBLE);
                tickBueno.setVisibility(View.INVISIBLE);
                tickRegular.setVisibility(View.INVISIBLE);
                tickMal.setVisibility(View.VISIBLE);
                rating = 1f;
            }
        });

        botonRespetoSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickRespetoSi.setVisibility(View.VISIBLE);
                tickRespetoNo.setVisibility(View.INVISIBLE);
                bRespeto = true;

            }
        });
        botonRespetoNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickRespetoSi.setVisibility(View.INVISIBLE);
                tickRespetoNo.setVisibility(View.VISIBLE);
                bRespeto = false;

            }
        });
        botonServicioSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickServicioSi.setVisibility(View.VISIBLE);
                tickServicioNo.setVisibility(View.INVISIBLE);
                bServicio = true;

            }
        });
        botonServicioNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickServicioSi.setVisibility(View.INVISIBLE);
                tickServicioNo.setVisibility(View.VISIBLE);
                bServicio = false;

            }
        });
        botonRecomendarSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickRecomendarSi.setVisibility(View.VISIBLE);
                tickRecomendarNo.setVisibility(View.INVISIBLE);
                bRecomendar = true;

            }
        });
        botonRecomendarNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickRecomendarSi.setVisibility(View.INVISIBLE);
                tickRecomendarNo.setVisibility(View.VISIBLE);
                bRecomendar = false;

            }
        });

        excelenteSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("EXCELENTE");
            }
        });
        genialSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("GENIAL");
            }
        });
        buenoSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("BUENO");
            }
        });
        regularSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("REGULAR");
            }
        });
        malSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("MAL");
            }
        });

        respetoSiSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("SI");
            }
        });
        respetoNoSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("NO");
            }
        });

        servicioSiSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("SI");
            }
        });
        servicioNoSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("NO");
            }
        });

        recomendarSiSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("SI");
            }
        });
        recomendarNoSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("NO");
            }
        });


        finalizarRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer votos_positivos = 0;
                Integer votos_negativos = 0;

                if (bRecomendar == null || bServicio == null || bRespeto == null) {
                    Toast.makeText(getContext(), "Existen preguntas sin puntuar", Toast.LENGTH_LONG).show();
                } else {
                    if (bRecomendar) {
                        votos_positivos = votos_positivos + 1;
                    } else {
                        votos_negativos = votos_negativos + 1;
                    }
                    if (bServicio) {
                        votos_positivos = votos_positivos + 1;
                    } else {
                        votos_negativos = votos_negativos + 1;
                    }
                    if (bRespeto) {
                        votos_positivos = votos_positivos + 1;
                    } else {
                        votos_negativos = votos_negativos + 1;
                    }

                    new EnviaPuntuacionActivity(getContext(), getActivity(), sol.getEmailProveedor(), sol.getEmailSolicitante(), rating, votos_positivos, votos_negativos).execute();
                    databaseHelper.setEliminaSolicitud(sol);
                    databaseHelper.setEliminaTodosLosAnuncios();
                    serviciosLayout.removeAllViews();
                    LinearLayout lay2 = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.vistasinservicioencurso, null);
                    serviciosLayout.addView(lay2);
                }
            }
        });
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
