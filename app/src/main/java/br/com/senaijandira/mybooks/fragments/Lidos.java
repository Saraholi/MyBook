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

public class Lidos extends Fragment {

    ListView lstViewLivros;


    public static Livro[] livros;

    //Variavel de acesso ao Banco
    private MyBooksDataBase myBooksDb;

    LivroAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lidos, container, false);

        myBooksDb = Room.databaseBuilder( getContext(),
                MyBooksDataBase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        //Criar o adapter
        adapter = new LivroAdapter(getContext(), myBooksDb);

        lstViewLivros.setAdapter(adapter);

        //Aqui faz um select no banco
        livros = myBooksDb.daoLivro().selecionarTodos();

        return v;
    }


}
