package com.example.listagemdetarefas.activity.activity.helper;

//uma classe que define a separação dos dados
//será responsável por salvar os dados no banco de dados

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.listagemdetarefas.activity.activity.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    //atributos
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    //Construtor para acessar o DBHelper

    public TarefaDAO(Context context) {

        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase(); //recupera os objetos que permite salvar no BD
        le = db.getReadableDatabase(); //permite ler os dados


    }



    @Override
    public boolean salvar(Tarefa tarefa) {

        //estamos criando um objeto que tem o nome e o valor que iremos salvar no banco de dados
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        //cv.put("status", "E");

       try { //ao colocar como null, só vamos inserir os dados se o nome da tarefa estiver preenchida
            escreve.insert(DBHelper.TABELA_TAREFAS, null, cv);
           Log.e("INFO", "Tarefa salva com sucesso!");

       }
       catch (Exception e){
           Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
       }

        return true; //por padrão a gente retorna True, false só se tiver erros.
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );

        try {
            String[] args = {
            tarefa.getId().toString()};
            //o ? é um coringa, será substituido pelo array de string (ARGS)
            escreve.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

       try {
            String[] args = {tarefa.getId().toString() };
            //no caso de deletar não preciso passar o CV, pois apenas o ID já consigo remover.
           //no Atualizar precisa do CV porque são os valores para serem alterados, não apenas ID.

            escreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args );
            Log.i("INFO", "Tarefa removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        //mova para o próximo item (moveToXext)
        while (c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndexOrThrow("id"));

            String nomeTarefa = c.getString(c.getColumnIndexOrThrow("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
            Log.i("TarefaDao", tarefa.getNomeTarefa() );


        }
        return tarefas;




    }
}
