package com.example.listagemdetarefas.activity.activity.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public static int VERSION = 3; //versao do app
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    //a versao é passada para o DBHelper
    // qnd lançar as próximas versões o cliente deve atualizar
    public DBHelper(@Nullable Context context) {


        super(context, NOME_DB, null, VERSION);
    }

    @Override //é chamado uma vez para a criação do banco
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                     + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                      " nome TEXT NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");

        }
        catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }

    }

    @Override //ele vai ser chamado qnd ele estiver atualizando o app //versao 1 pra versao 2.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Deletando tabela inteira

        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";

        //alterar tabela
       // String sql = " ALTER TABLE " + TABELA_TAREFAS + " ADD COLUMN status VARCHAR(2)";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao atualizar APP");

        }
        catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar o APP" + e.getMessage());
        }

    }
}
