package model;

import java.time.LocalDate;

public class Tarefa {
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate data;

    public Tarefa(String titulo, String descricao, boolean concluida, LocalDate data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.data = data;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}