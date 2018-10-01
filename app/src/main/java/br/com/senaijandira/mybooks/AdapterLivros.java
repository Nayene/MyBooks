package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;



    public class AdapterLivros extends ArrayAdapter<Livro> {
         private MyBooksDatabase myBooksDB;

        public AdapterLivros(Context ctx){
            super(ctx,0,new ArrayList<Livro>());

            this.myBooksDB = myBooksDB;
        }

        private void deletarLivro(Livro livro){
            myBooksDB.daoLivro().deletar(livro);

            remove(livro);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
            }

            final Livro livro = getItem(position);

            ImageView imgLivroCapa =  v.findViewById(R.id.imgLivroCapa);
            TextView txtLivroTitulo =  v.findViewById(R.id.txtlivroTitulo);
            TextView txtLivroDescricao =  v.findViewById(R.id.txtlivroDescricao);


            ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);

            imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletarLivro(livro);
                }
            });


            //setando a imagem do livro
            imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

            // setando o titulo d livro
            txtLivroTitulo.setText(livro.getTitulo());

            //setando a descriçao do livro
            txtLivroDescricao.setText(livro.getDescricao());

            return v;
        }
    }

