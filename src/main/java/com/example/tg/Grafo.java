package com.example.tg;

import java.util.List;
import java.util.ArrayList;

public class Grafo {
    private List<Vertice> listaV;
    private List<Aresta> listaA;
    private boolean orientado;

    public Grafo(List<Vertice> listaV, List<Aresta> listaA) {
        this.listaV = listaV;
        this.listaA = listaA;
    }
    public Grafo(){
        this.listaV = new ArrayList<>();
        this.listaA = new ArrayList<>();
    }

    public void addVertice(String nomeId){
        listaV.add(new Vertice(nomeId, false));
    }
    public void addAresta(Vertice origem, Vertice destino, int peso, boolean isDirecionado){
        listaA.add(new Aresta(origem, destino, peso, isDirecionado));
    }
    public boolean isOrientado(){
        return orientado;
    }
    public boolean possuiLaco(){
        for(Aresta a : listaA)
            if(a.getOrigem() == a.getDestino()) return true;
        return false;
    }
    public boolean possuiArestasParalelas(){
        for (int i = 0; i < listaA.size(); i++) {
            for (int j = i + 1; j < listaA.size(); j++) {
                Aresta a1 = listaA.get(i);
                Aresta a2 = listaA.get(j);
                if (a1.getOrigem().equals(a2.getOrigem()) && a1.getDestino().equals(a2.getDestino())) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSimples(){
        return !possuiLaco() && !possuiArestasParalelas();
    }

    public int grau(Vertice v){
        int grau=0;
        for(Aresta a : listaA)
            if(a.getOrigem().equals(v) || a.getDestino().equals(v))grau++;
        return grau;
    }
    public boolean isRegular(){
        int g = grau(listaV.get(0));
        for(Vertice v : listaV)
            if(grau(v)!=g)return false;
        return true;
    }
    public boolean isCompleto(){
        for(Vertice v : listaV)
            if(grau(v) != listaV.size()-1)return false;
        return true;
    }
    public boolean isCompletoOrientado(){
        for(Vertice ov : listaV){
            for(Vertice dv : listaV){
                if(ov.equals(dv))continue;
                if(!existeAresta(ov, dv))return false;
            }
        }
        return true;
    }

    public boolean isDigrafo(){
        for(Aresta a : listaA)
            if(a.getOrigem() && a.getDestino())
        return false;
    }
    public boolean existeAresta(Vertice origem, Vertice destino){
        for(Aresta a : listaA)
            if(a.getOrigem().equals(origem) && a.getDestino().equals(destino))return true;
        return false;
    }


    public List<Vertice> getListaV() {
        return listaV;
    }

    public List<Aresta> getListaA() {
        return listaA;
    }
}
