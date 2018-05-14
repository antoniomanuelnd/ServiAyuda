package com.serviayuda.pc.serviayuda.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.serviayuda.pc.serviayuda.Fragments.PerfilFragmentAdapt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 13/05/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitulo = new ArrayList<>();
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitulo.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentListTitulo.get(position);
    }
    public void AddFragment(Fragment fragment, String titulo){
        fragmentList.add(fragment);
        fragmentListTitulo.add(titulo);
    }

}
