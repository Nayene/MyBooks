package br.com.senaijandira.mybooks.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
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

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class EditarLivro extends AppCompatActivity {
    // declarando escopo de classe
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    AlertDialog alerta;

    int idLivro, status;

    InputStream input;

    private final int COD_REQ_GALERIA = 101;

    // banco
    private MyBooksDatabase myBooksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_livro);

        // Criando a conexao do banco de dados
        myBooksDatabase = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        idLivro = getIntent().getIntExtra("livro", 0);

        Livro livro = myBooksDatabase.livroDao().pegarLivro(idLivro);

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        livroCapa = BitmapFactory.decodeByteArray(livro.getCapa(), 0, livro.getCapa().length);
        txtTitulo.setText(livro.getTitulo());
        txtDescricao.setText(livro.getDescricao());

        status = livro.getStatus();

    }

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
                // Convertendo a img
                livroCapa = BitmapFactory.decodeStream(input);

                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // tratamento de erro
    public void editarLivro(View view) {
        if (livroCapa == null){
            alert("Erro", "Escolha uma imagem", 1);
        } else {
            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();

            if (titulo.equals("") || descricao.equals("")) {
                alert("Erro", "Preencha todos os campos", 1);
            }else{
                alert("Concluido", "Livro editado com sucesso!", 0);

                // criando o livro atualizado
                Livro livro = new Livro(idLivro, capa, titulo, descricao, status);

                myBooksDatabase.livroDao().atualizar(livro);
            }
        }
    }

    // criando o alert
    public void alert(String titulo, String mensagem, int tipoAlert) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        if(tipoAlert == 1){

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
