package com.example.listagemdetarefas.activity.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.listagemdetarefas.R;
import com.example.listagemdetarefas.activity.activity.adapter.TarefaAdapter;
import com.example.listagemdetarefas.activity.activity.helper.DBHelper;
import com.example.listagemdetarefas.activity.activity.helper.RecyclerItemClickListener;
import com.example.listagemdetarefas.activity.activity.helper.TarefaDAO;
import com.example.listagemdetarefas.activity.activity.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView = findViewById((R.id.recyclerView));

       /* DBHelper db = new DBHelper(getApplicationContext());

        //Classe que permite que consigamos definir itens como se fosse array
        ContentValues cv = new ContentValues();
        cv.put("nome", "Teste" );

        db.getWritableDatabase().insert("tarefas",null, cv);
*/


        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               // Log.i("clique", "onItemClick");

                                //Recuperar tarefa para edicao
                                Tarefa tarefaSelecionada = listaTarefas.get( position );

                                //Envia tarefa para tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada );

                                startActivity( intent );
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // Log.i("clique", "onItemClick");

                                //recuperar tarefa para deletar
                                tarefaSelecionada = listaTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //configurar título e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                        if (tarefaDAO.deletar(tarefaSelecionada))
                                        {
                                            carregarListaTarefas();
                                            Toast.makeText(getApplicationContext(),
                                                    "Sucesso ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity (intent);

            }
        });

    }

    //criando Menu superior
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //converter XML para view
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //tratar o evento de clique

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            //getItem vai recuperar o id do item selecionado pelo usuário
        switch(item.getItemId()){
            case R.id.itemSalvar:
                Toast.makeText(MainActivity.this, "Item salvar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.itemEditar:
                Toast.makeText(MainActivity.this, "Item editar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.itemConfigurar:
                Toast.makeText(MainActivity.this, "Item configurar", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void carregarListaTarefas(){

        //Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();





       /* Tarefa tarefa1 = new Tarefa();
        tarefa1.setNomeTarefa("Ir ao mercado");
        listaTarefas.add(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNomeTarefa("Ir a feira");
        listaTarefas.add(tarefa2);
*/


        //EXIBE LISTA DE TAREFAS NO RECYCLERVIEW



        //CONFIGURAR UM ADAPTER
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        //CONFIGURAR RECYCLERVIEW
      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()));
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.setHasFixedSize(true);
      recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
      recyclerView.setAdapter(tarefaAdapter);
    }

    @Override //recarregando a lista a cada vez que o usuário saia e volte para a tela
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }
}
