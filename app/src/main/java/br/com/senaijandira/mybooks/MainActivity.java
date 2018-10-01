package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.tab.AbasAdapter;
import br.com.senaijandira.mybooks.tab.livros_fragment;
import br.com.senaijandira.mybooks.tab.livroslidos_fragment;
import br.com.senaijandira.mybooks.tab.livrosqueroler_fragment;

public class MainActivity extends AppCompatActivity {
    //lista onde aparece todos os livos
    ListView lstViewLivros;
    Button btnqQueroLer;
    AdapterLivros adapter;

    public static Livro[] livros;

    private MyBooksDatabase myBooksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AbasAdapter adapterAba = new AbasAdapter(getSupportFragmentManager());
        adapterAba.adicionar(new livros_fragment(), "Livros");
        adapterAba.adicionar(new livrosqueroler_fragment(), "quero ler ");
        adapterAba.adicionar(new livroslidos_fragment(),"lidos");




        //instancia do banco de dados
        myBooksDB = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        lstViewLivros = findViewById(R.id.lstViewLivros);
        //criar o adapter

        adapter = new AdapterLivros(this, myBooksDB);
        lstViewLivros.setAdapter(adapter);


        ViewPager viewPager = (ViewPager) findViewById(R.id.abas_view_pager);
        viewPager.setAdapter(adapterAba);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);



    @Override
    protected void onResume(){
        super.onResume();
        //fazer select no banco
        livros = myBooksDB.daoLivro().selecionarTodos();

        adapter.clear();
        adapter.addAll(livros);

    }

    public void abrirCadastro(View v){
        startActivity(new Intent(this,CadastroActivity.class));
    }

    }
