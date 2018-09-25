package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {
    //lista onde aparece todos os livos
    ListView lstViewLivros;
    Button btnqQueroLer;
    livrosAdapter adapter;

    public static Livro[] livros;

    private MyBooksDatabase myBooksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnqQueroLer= findViewById(R.id.btnqQueroLer);
        lstViewLivros=findViewById(R.id.lstViewLivros);

        adapter = new livrosAdapter(this);
        lstViewLivros.setAdapter(adapter);



        //instancia do banco de dados
        myBooksDB = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();





        //criando cadastros(livros) fake
        /*livros = new Livro[]{/*
                new Livro(1,Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza),"50 Tons de Cinza", getString(R.string.cinquenta_tons_de_cinza)),
                new Livro(2,Utils.toByteArray(getResources(),R.drawable.pequeno_principe),"O Pequeno Principe", getString(R.string.o_pequeno_principe)),
                new Livro(3,Utils.toByteArray(getResources(),R.drawable.kotlin_android),"Kotlin", getString(R.string.kotlin_android)),

        };



        byte[] capa = Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza);
        Livro livro = new Livro(1,capa,"50 Tons de Cinza", getString(R.string.cinquenta_tons_de_cinza));
*/
    }

    public void adicionarLivroQueroler(){

    }
    @Override
    protected void onResume() {
        super.onResume();
        //fazer select no banco
        livros = myBooksDB.daoLivro().selecionarTodos();

        adapter.clear();
        adapter.addAll(livros);

    }



    public void deletarLivro(final Livro livro){

        AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
        alertDelete.setTitle("Deletar");
        alertDelete.setMessage("Tem certeza que deseja deletar ?");
        alertDelete.setNegativeButton("NÂO",null);

        alertDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //deletar livro no banco
                myBooksDB.daoLivro().deletar(livro);

                //deletar livro da tela
                adapter.remove(livro);
            }
        });
        alertDelete.show();

        //Toast.makeText(this, livro.getTitulo(),Toast.LENGTH_SHORT).show();   // teste para ver se iria excluir o item certo
    }


    public class livrosAdapter extends ArrayAdapter<Livro>{
        public livrosAdapter(Context ctx){
            super(ctx,0,new ArrayList<Livro>());
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

            //lixeira
            ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);

            imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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




    public void abrirCadastro(View v){
        startActivity(new Intent(this,CadastroActivity.class));
    }


    public void abrirQueroLer(View v){
        startActivity(new Intent(this,QueroLerActivity.class));
    }
    }
