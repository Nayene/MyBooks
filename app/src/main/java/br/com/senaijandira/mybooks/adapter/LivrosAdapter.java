package br.com.senaijandira.mybooks.adapter;


import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.Drawable;
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

import br.com.senaijandira.mybooks.cadastro.EditarLivro;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends ArrayAdapter<Livro> {
    // escopo de classe
    MyBooksDatabase appDB;
    ImageView imgLivroLer;
    ImageView imgLivroLido;
    Drawable drawableLer;
    Drawable drawableLido;

    AlertDialog alerta;

    public LivrosAdapter(Context context){
        // pegando o contexto e criando uma lista de livro
        super(context, 0, new ArrayList<Livro>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
// recebendo os elementos na tela
        View view = convertView;

        // instancia com o banco de dados
        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false);
        }
        //pegando a posicao do livro para criar novos
        final Livro livro = getItem(position);

        // elementos
        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);
        ImageView imgDeletarLivro = view.findViewById(R.id.imgDeleteLivro);

        // tratamento de erro para excluir livro
        imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (livro.getStatus() == 1 || livro.getStatus() == 2){
                    alert("Erro", "Não é possível excluir o livro enquanto estiver em outra lista, tente remove-lo das outras listas");
                } else {
                    // se ele nao estive em outra lista é deletado do banco de dados
                    appDB.livroDao().deletar(livro);
                    remove(livro);
                    alert("EXCLUIDO", "Livro excluído");
                }
            }
        });

        // elemento editar
        ImageView imgEditarLivro = view.findViewById(R.id.imgEditarLivro);

        // clicando no editar ele vai pegar as informações do livro pelo id
        imgEditarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarLivro.class);
                intent.putExtra("livro", livro.getId());
                getContext().startActivity(intent);
            }
        });

        // elemento coracao para quero ler
        imgLivroLer = view.findViewById(R.id.imgLivrosLer);

        // ele verifica os status e muda a img
        if (livro.getStatus() == 1){
            drawableLer = getContext().getResources().getDrawable(R.drawable.queroler);
            imgLivroLer.setImageDrawable(drawableLer);

        }else {
            drawableLer = getContext().getResources().getDrawable(R.drawable.querolerclicado);
            imgLivroLer.setImageDrawable(drawableLer);
        }


        // funcao para verificar se clicado e envia para a outra pagina
        imgLivroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // criando os elementos de livro
                Livro livroLer = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 1);

                // atualizando com o bando
                appDB.livroDao().atualizar(livroLer);

                // limpa e atualiza
                clear();
                // pegando as informações do livro
                Livro[] livros = appDB.livroDao().selecionarTodos();
                // adicionando os livros
                addAll(livros);

                // mensagem
                if (livro.getStatus() != 1)
                    Toast.makeText(getContext(), "Livro adicionado a Quero ler", Toast.LENGTH_SHORT).show();
            }
        });


        // elemnto livro lido
        imgLivroLido = view.findViewById(R.id.imgLivrosLidos);
        // ele verifica o status e muda a img
        if (livro.getStatus() == 2){
            drawableLido = getContext().getResources().getDrawable(R.drawable.lidos);
            imgLivroLido.setImageDrawable(drawableLido);
        } else {
            drawableLido = getContext().getResources().getDrawable(R.drawable.lidosclicado);
            imgLivroLido.setImageDrawable(drawableLido);
        }

        // verifca se fo clicado e envia para a outra pag
        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // criando o livro
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);
                // atualizando com o banco
                appDB.livroDao().atualizar(livroLido);
                // limpando e atuaizando
                clear();
                // pegando as informações do livro
                Livro[] livros = appDB.livroDao().selecionarTodos();
                // adicionado os livros
                addAll(livros);
                // mensagem
                if (livro.getStatus() != 2)
                    Toast.makeText(getContext(), "Livro adicionado a Lidos", Toast.LENGTH_SHORT).show();
            }
        });

        // pegando elementos do livro
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());

        return view;

    }
    // criando o alert
    public void alert(String titulo, String mensagem) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerta.cancel();
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}
