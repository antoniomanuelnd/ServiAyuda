package com.serviayuda.pc.serviayuda.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 13/05/2018.
 */

public class PerfilFragmentAdapt extends Fragment{

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil_adapt, container, false);
        FrameLayout fr = view.findViewById(R.id.frame_perfil_adapt);
        return view;
    }

}
