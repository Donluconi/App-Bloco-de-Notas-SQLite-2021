package com.example.listagemdetarefas.activity.activity.helper;

import com.example.listagemdetarefas.activity.activity.model.Tarefa;

import java.util.List;

//interface: CRUD, definir os métodos que serão obrigatórios a serem utilizados no DAO
public interface ITarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
