package br.com.senaijandira.mybooks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosQueroLerFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;

public class AbasAdapter  extends FragmentStatePagerAdapter{

    // numero de abas
    private static int NUM_ITEMS = 3;

    public AbasAdapter(FragmentManager manager){
        super(manager);
    }

    // pegando a quantidade de pagina
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // o conteudo de cada pagina de acordo com a posição
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LivrosFragment();
            case 1:
                return new LivrosQueroLerFragment();
            case 2:
                return new LivrosLidosFragment();
            default:
                return null;
        }
    }

    // titulos das abass
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Livros";
            case 1:
                return "Quero Ler";
            case 2:
                return "Lidos";
            default:
                return null;
        }
    }
}
