package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Arrays;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    AlertDialog alerta;
    private final int COD_REQ_GALERIA = 101;

    private MyBooksDatabase myBookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        myBookDB = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class,Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

       imgLivroCapa= findViewById(R.id.imgLivroCapa);
       txtTitulo = findViewById(R.id.txtTitulo);
       txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void abrirGaleria(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"),COD_REQ_GALERIA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK ){
            try{
                InputStream input = getContentResolver().openInputStream(data.getData());
                //convertendo para bitmap
                livroCapa = BitmapFactory.decodeStream(input);
                // exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);
            }catch (Exception ex){
                ex.printStackTrace();
                alert("ERRO", "coloque uma imagem", 1);
            }
        }
    }

    public void salvarLivro(View view) {
        byte[] capa ;
        String titulo = txtTitulo.getText().toString();
        String descricao = txtDescricao.getText().toString();
        //tratamento de erro
        if (titulo.equals("")) {
            alert("ERRO", "Preencha todos os campos", 1);
        } else if (descricao.equals("")) {
            alert("ERRO", "Preencha todos os campos", 1);
        }else if(livroCapa == null){
            alert("ERRO", "Preencha todos os campos", 1);
        }else {
            capa = Utils.toByteArray(livroCapa);
            alert("Cadastrado","Seu livro foi cadastrado com sucesso",0);

            Livro livro = new Livro(capa, titulo, descricao);

            //inserir no banco de dados
            myBookDB.daoLivro().inserir(livro);

        }


        // Inserir na variavl estatica da mainActivity
        /*int tamanhoArray = MainActivity.livros.length;
        MainActivity.livros = Arrays.copyOf(MainActivity.livros,tamanhoArray + 1);
        MainActivity.livros[tamanhoArray] = livro;
        */



    }

    public void alert(String titulo, String msg, int categoria){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(msg);
        builder.setCancelable(false);

        if (categoria == 1){
            // botão ok
            builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta.cancel();
                }
            });
        }else{
            builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }

        alerta = builder.create();
        alerta.show();
    }
}
