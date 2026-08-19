package com.example.tg;

import java.util.List;

public class Grafo {
    private List<Vertice> listaV;
    private List<Aresta> listaA;

    public Grafo(List<Vertice> listaV, List<Aresta> listaA) {
        this.listaV = listaV;
        this.listaA = listaA;
    }
    public Grafo(){
        this.listaV = null;
        this.listaA = null;
    }

    public void addVertice(String nomeId){
        listaV.add(new Vertice(nomeId, false));
    }
    public void addAresta(Vertice origem, Vertice destino, int peso, boolean isDirecionado){
        listaA.add(new Aresta(origem, destino, peso, isDirecionado));
    }

}
