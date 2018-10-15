package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;

@Dao
public interface LivroDao {


    @Insert
    void inserir(Livro livro);

    @Update
    void atualizar(Livro livro);

    @Delete
    void deletar(Livro livro);

    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();

    @Query("SELECT * FROM livro WHERE status = 1")
    Livro[] selecionarLivrosQueroLer();

    @Query("SELECT * FROM livro WHERE status = 2")
    Livro[] selecionarLivrosLidos();

    @Query("SELECT * FROM livro WHERE id = :idLivro")
    Livro pegarLivro(int idLivro);

}
