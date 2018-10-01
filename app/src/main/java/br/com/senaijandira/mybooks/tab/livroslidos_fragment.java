package br.com.senaijandira.mybooks.tab;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;

/**
 * Created by 17259224 on 01/10/2018.
 */

public class livroslidos_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.livroslidos_fragment, container, false);

        TextView tv = view.findViewById(R.id.text3);
        tv.setText("Você está na terceira aba");

        return view;
    }
}
