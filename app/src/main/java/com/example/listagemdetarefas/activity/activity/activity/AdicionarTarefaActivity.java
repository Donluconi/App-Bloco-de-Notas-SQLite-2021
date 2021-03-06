package com.example.listagemdetarefas.activity.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listagemdetarefas.R;
import com.example.listagemdetarefas.activity.activity.helper.TarefaDAO;
import com.example.listagemdetarefas.activity.activity.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textoTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //tratar o click em cima do item


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:
                //Executa ação para o item salvar
                //vamos utilizar a classe DAO para salvar os dados
                //eu passo o getApp Context pois no construtor do DBHelper eu passo o Contexto

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null) { //se for diferente de nulo, estamos fazendo edição

                    String nomeTarefa = editTarefa.getText().toString();

                    if (!nomeTarefa.isEmpty()) {

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados

                        if (tarefaDAO.atualizar(tarefa)) {

                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        else{
                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else { //salvar

                        //criando tarefa

                        String nomeTarefa = editTarefa.getText().toString();


                        if (!nomeTarefa.isEmpty()) {
                            Tarefa tarefa = new Tarefa();
                            tarefa.setNomeTarefa(nomeTarefa);

                            if (tarefaDAO.salvar(tarefa)) {
                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            finish();
                        }
                    }
                    break;
                }

                return super.onOptionsItemSelected(item);
        }
    }

