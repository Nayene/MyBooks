package br.com.senaijandira.mybooks.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {
    //criando o escopo de classe
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    AlertDialog alerta;

    InputStream input;

    private final int COD_REQ_GALERIA = 101;

    private MyBooksDatabase myBooksDatabase;

    // criando os elementos da tela
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // banco
        myBooksDatabase = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    // comando para abrir a galeria
    public void AbrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){

            try {
                input = getContentResolver().openInputStream(data.getData());
                // Convertendo a img para bitmap
                livroCapa = BitmapFactory.decodeStream(input);
                imgLivroCapa.setImageBitmap(livroCapa);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // tratamento de erro para campos vazios e salvando
    public void salvarLivro(View view) {

        if (livroCapa == null){
            alert("Erro", "Selecione uma imagem de capa", 1);
        } else {
            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();

            int status = 0;

            //tratmento
            if (titulo.equals("") || descricao.equals("")) {
                alert("Erro", "Preencha todos os campos", 1);

                // caso esteja correto o livro é salvo no banco
            } else {
                alert("Sucesso", "Livro cadastrado com sucesso!", 0);
                Livro livro = new Livro(0, capa, titulo, descricao, status);

                myBooksDatabase.livroDao().inserir(livro);
            }
        }
    }

    // criando o alert
    public void alert(String titulo, String mensagem, int tipoAlert) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        if (tipoAlert == 1){
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta.cancel();
                }
            });
        }else{
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
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
