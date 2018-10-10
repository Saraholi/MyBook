package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;



public class Inicio extends Fragment {


    //Vai carregar os livros
    ListView lstViewLivros;


    public static Livro[] livros;

    //acesso ao Banco
    private MyBooksDataBase myBooksDb;

    //Adapter para criar a lista de livros
    LivroAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        //Criando a instancia do banco de dados
        myBooksDb = Room.databaseBuilder( getContext(),
                MyBooksDataBase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        //Criação do adapter
        adapter = new LivroAdapter(getContext(), myBooksDb);

        lstViewLivros.setAdapter(adapter);

        //select no banco
        livros = myBooksDb.daoLivro().selecionarTodos();

        //Limpando a listView
        adapter.clear();


        //Adicionando os livros a lista
        adapter.addAll(livros);

        return v;

    }

}

