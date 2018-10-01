package br.com.senaijandira.mybooks.tab;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.AdapterLivros;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

/**
 * Created by 17259224 on 01/10/2018.
 */

public class livros_fragment extends Fragment {
    ListView lstViewLivros;
    AdapterLivros adapter;

    MyBooksDatabase appDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.livros_fragments, container, false);

        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();



        lstViewLivros = view.findViewById(R.id.lstViewLivros);

        adapter = new AdapterLivros(getContext());

        lstViewLivros.setAdapter(adapter);

        return view;

    }

}
