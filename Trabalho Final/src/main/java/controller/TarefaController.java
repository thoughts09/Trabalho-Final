package controller;

import model.Tarefa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TarefaController {
    private List<Tarefa> tarefas = new ArrayList<>();

    public void addTarefa(Tarefa t) { tarefas.add(t); }
    public void editarTarefa(int index, Tarefa t) { tarefas.set(index, t); }
    public Tarefa getTarefa(int index) { return tarefas.get(index); }
    public void excluirTarefa(int index) { tarefas.remove(index); }
    public List<Tarefa> getListaDeTarefas() { return tarefas; }

    public void atualizarTabela(DefaultTableModel tm) {
        tm.setRowCount(0);
        for (Tarefa t : tarefas) {
            tm.addRow(new Object[]{t.getTitulo(), t.getDescricao(), t.isConcluida(), t.getData()});
        }
    }
}