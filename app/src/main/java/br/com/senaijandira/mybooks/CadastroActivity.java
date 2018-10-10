package br.com.senaijandira.mybooks;

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

import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;

    private final int COD_REQ_GALERIA = 101;

    private MyBooksDataBase myBooksDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Criando a instancia do banco de dados
        myBooksDb = Room.databaseBuilder(getApplicationContext(),
                MyBooksDataBase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void abrirGaleria(View view) {

        Intent intent =
                new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(
                Intent.createChooser(intent,
                        "Selecione uma imagem"),
                    COD_REQ_GALERIA
                );
    }

    public void alert(String titulo, String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage("Ok seu Livro foi salvo com sucesso");

        alert.setCancelable(false);
        alert.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COD_REQ_GALERIA
                && resultCode == Activity.RESULT_OK){

            try{

                InputStream input =
                        getContentResolver()
                        .openInputStream(data.getData());

                //Converteu para bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                //Exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception ex){
                ex.printStackTrace();
            }


        }

    }

    public void salvarLivro(View view) {

        byte[] capa = Utils.toByteArray(livroCapa);

        String titulo = txtTitulo.getText().toString();

        String descricao = txtDescricao.getText().toString();

        Livro livro = new Livro(0, capa, titulo, descricao);

        myBooksDb.daoLivro().inserir(livro);


        //Alert
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sucesso");

        alert.setMessage("Parabéns Livro salvo com sucesso");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.create().show();
    }

}

