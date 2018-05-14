package com.serviayuda.pc.serviayuda.Actividades;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.serviayuda.pc.serviayuda.Adapters.FragmentAdapter;
import com.serviayuda.pc.serviayuda.Fragments.PerfilFragmentAdapt;
import com.serviayuda.pc.serviayuda.Fragments.ServiciosFragmentAdapt;
import com.serviayuda.pc.serviayuda.Fragments.SolicitudesFragmentAdapt;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 13/05/2018.
 */

public class MenuInterfazAdaptada extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_interfaz_adaptada);
        tabLayout = findViewById(R.id.barra_navegacion);
        viewPager = findViewById(R.id.viewpager_adapt);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.AddFragment(new PerfilFragmentAdapt(), "PERFIL");
        adapter.AddFragment(new ServiciosFragmentAdapt(), "SERVICIOS");
        adapter.AddFragment(new SolicitudesFragmentAdapt(), "SOLICITUDES");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_align_justify_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_assignment_late_white24dp);
    }
}
