package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.senaijandira.mybooks.cadastro.CadastroActivity;


public class MainActivity extends AppCompatActivity {
    // criando as abas
    AbasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AbasAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.abasViewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);

    }

    // chamando o abir cadatro
    public void abrirCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));

    }

}
