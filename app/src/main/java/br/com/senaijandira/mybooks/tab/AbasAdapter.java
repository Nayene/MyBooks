package br.com.senaijandira.mybooks.tab;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17259224 on 01/10/2018.
 */

public class AbasAdapter extends FragmentPagerAdapter {
    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
    private List<String> titulos = new ArrayList<>();

    public AbasAdapter(FragmentManager fm) {
        super(fm);
    }

    public void adicionar(android.support.v4.app.Fragment fragment, String tituloAba){
        this.fragments.add(fragment);
        this.titulos.add(tituloAba);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return this.titulos.get(position);
    }
}
