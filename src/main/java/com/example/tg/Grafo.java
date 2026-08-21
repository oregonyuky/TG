package com.example.tg;

import java.util.List;
import java.util.ArrayList;

public class Grafo {
    private List<Vertice> listaV;
    private List<Aresta> listaA;

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
    public boolean possuiLaco(){
        for(Aresta a : listaA)
            if(a.getOrigem() == a.getDestino()) return true;
        return false;
    }
    public boolean possuiArestasParalelas1(){
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
    public boolean possuiArestasParalelas2(){
        for(Aresta a : listaA){
            if(contar(a.getOrigem(), a.getDestino()) >= 2)return true;
        }
        return false;
    }
    public int contar(Vertice a, Vertice b){
        int c=0;
        for(Aresta ar : listaA)
            if(ar.getOrigem().equals(a) && ar.getDestino().equals(b))c++;
        return c;
    }
    public boolean isSimples(){
        if(possuiLaco())return false;
        if(isDigrafo())return !possuiArestasParalelas1();
        else return !possuiArestasParalelas2();
    }

    public int grau(Vertice v){
        int grau=0;
        for(Aresta a : listaA)
            if(a.getOrigem().equals(v) || a.getDestino().equals(v))grau++;
        return grau;
    }
    public boolean isRegular(){
        if (listaV.isEmpty()) return false;
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

    public boolean isDigrafo() {
        for (Aresta a : listaA)
            if(existeAresta(a.getDestino(), a.getOrigem()))return false;
        return true;
    }

    public boolean isNaoOrientado(){
        for(Aresta a : listaA)
            if(!existeAresta(a.getDestino(), a.getOrigem()))return false;
        return true;
    }
    public boolean isGrafoMisto(){
        return !isDigrafo() && !isNaoOrientado();
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
