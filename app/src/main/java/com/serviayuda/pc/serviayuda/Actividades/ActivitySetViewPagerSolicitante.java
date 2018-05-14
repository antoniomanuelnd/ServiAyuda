package com.serviayuda.pc.serviayuda.Actividades;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.serviayuda.pc.serviayuda.Adapters.ViewPagerAdapter;
import com.serviayuda.pc.serviayuda.Fragments.EnviarAnuncioFragment;
import com.serviayuda.pc.serviayuda.Fragments.PerfilFragment;
import com.serviayuda.pc.serviayuda.Fragments.ServicioEnCursoFragment;
import com.serviayuda.pc.serviayuda.Fragments.VerSolicitudesFragment;
import com.serviayuda.pc.serviayuda.R;

/**
 * Created by PC on 25/03/2018.
 */

public class ActivitySetViewPagerSolicitante extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpagerlayout);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new PerfilFragment(), "");
        adapter.AddFragment(new EnviarAnuncioFragment(), "");
        adapter.AddFragment(new VerSolicitudesFragment(), "");
        adapter.AddFragment(new ServicioEnCursoFragment(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_chat_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_assignment_late_white24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_group_white_24dp);

    }
}
