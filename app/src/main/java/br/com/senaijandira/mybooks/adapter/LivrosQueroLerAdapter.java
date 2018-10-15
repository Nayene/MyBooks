package br.com.senaijandira.mybooks.adapter;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosQueroLerAdapter extends ArrayAdapter<Livro> {
    // escopo de classe
    MyBooksDatabase appDB;
    ImageView imgLivroLido;

    public LivrosQueroLerAdapter(Context context){
        // pegando o contexto de quero ler e criando uma lista de livro
        super(context, 0, new ArrayList<Livro>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        // instancia com o banco
        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_ler_layout, parent, false);
        }

        final Livro livro = getItem(position);

        // criando os elementos
        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);

        // para deletar os livros de quero ler
        ImageView imgDeletarLivro = view.findViewById(R.id.imgDeleteLivro);

        imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pegando os atributos do livro
                Livro livroLista = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 0);
                // atualizando no banco
                appDB.livroDao().atualizar(livroLista);
                // deletando
                clear();
                // atualizando livros quero ler
                Livro[] livros = appDB.livroDao().selecionarLivrosQueroLer();
                // adicionando livros
                addAll(livros);
                // mensagem
                Toast.makeText(getContext(), "Livro removido", Toast.LENGTH_SHORT).show();

            }
        });

        // comando para o lidos ( mesmas funcoes )
        imgLivroLido = view.findViewById(R.id.imgLivrosLidos);

        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);
                appDB.livroDao().atualizar(livroLido);
                clear();
                Livro[] livros = appDB.livroDao().selecionarLivrosQueroLer();
                addAll(livros);
                Toast.makeText(getContext(), "Livro adicionado a lidos", Toast.LENGTH_SHORT).show();

            }
        });

// pegando as informações de livros
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());

        return view;

    }
}
