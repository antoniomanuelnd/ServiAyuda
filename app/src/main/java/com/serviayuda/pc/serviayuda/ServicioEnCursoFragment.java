package com.serviayuda.pc.serviayuda;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by PC on 25/03/2018.
 */

public class ServicioEnCursoFragment extends Fragment {

    View view;
    ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.servicioencurso_fragment, container, false);
        FrameLayout fr = view.findViewById(R.id.fragment1);
        return view;
    }



}
