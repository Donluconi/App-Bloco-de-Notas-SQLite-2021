package com.example.listagemdetarefas.activity.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listagemdetarefas.R;
import com.example.listagemdetarefas.activity.activity.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    @NonNull
    @Override


    //criar nossas visualizações
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new MyViewHolder(itemLista);

    }

    //transmitir os dados
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
    }

    @Override //contando os itens
    public int getItemCount() {

        return this.listaTarefas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tarefa;

        public MyViewHolder(View itemView){
            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefa);
        }


    }

}
