package com.serviayuda.pc.serviayuda.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 25/03/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitulo = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
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
