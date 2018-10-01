package br.com.senaijandira.mybooks.tab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;

/**
 * Created by 17259224 on 01/10/2018.
 */

public class livrosqueroler_fragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.queroler_fragment, container, false);

        TextView tv = view.findViewById(R.id.text2);
        tv.setText("Você está na segunda aba");

        return view;
    }
}

