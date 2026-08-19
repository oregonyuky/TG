package com.example.tg;

public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private int peso;
    private boolean isDirecionado;

    public Aresta(Vertice origem, Vertice destino, int peso, boolean isDirecionado) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.isDirecionado = isDirecionado;
    }
    public Aresta(){
        this.origem = null;
        this.destino = null;
        this.peso = 0;
        this.isDirecionado = false;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
