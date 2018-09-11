package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaLivros;

    public static Livro[] livros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLivros=findViewById(R.id.listaLivros);

        //criando cadastros fake

        livros = new Livro[]{
                new Livro(1,Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza),"50 Tons de Cinza", getString(R.string.cinquenta_tons_de_cinza)),
                new Livro(2,Utils.toByteArray(getResources(),R.drawable.pequeno_principe),"O Pequeno Principe", getString(R.string.o_pequeno_principe)),
                new Livro(3,Utils.toByteArray(getResources(),R.drawable.kotlin_android),"Kotlin", getString(R.string.kotlin_android)),
                new Livro(4,Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza),"50 Tons de Cinza", getString(R.string.cinquenta_tons_de_cinza))

        };



        byte[] capa = Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza);
        Livro livro = new Livro(1,capa,"50 Tons de Cinza", getString(R.string.cinquenta_tons_de_cinza));


    }

    @Override
    protected void onResume() {
        super.onResume();

        listaLivros.removeAllViews();
        for(Livro l:livros){
            criarLivro(l, listaLivros);
        }
    }

    public void abrirCadastro(View v){
        startActivity(new Intent(this,CadastroActivity.class));
    }

    // método para criar livro
    public void criarLivro(Livro livro , ViewGroup root){
        View v = LayoutInflater.from(this).inflate(R.layout.livro_layout,root,false);

        ImageView imgLivroCapa =  v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo =  v.findViewById(R.id.txtlivroTitulo);
        TextView txtLivroDescricao =  v.findViewById(R.id.txtlivroDescricao);

        //setando a imagem do livro
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        // setando o titulo d livro
        txtLivroTitulo.setText(livro.getTitulo());

        //setando a descriçao do livro
        txtLivroDescricao.setText(livro.getDescricao());

        //Exibindo na tela
        root.addView(v);
    }
}
