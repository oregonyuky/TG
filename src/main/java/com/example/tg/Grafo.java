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
        for (int i = 0; i < listaA.size(); i++) {
            for (int j = i + 1; j < listaA.size(); j++) {
                Aresta a1 = listaA.get(i);
                Aresta a2 = listaA.get(j);
                boolean mesmaOrdem = a1.getOrigem().equals(a2.getOrigem())
                        && a1.getDestino().equals(a2.getDestino());
                boolean ordemInversa = a1.getOrigem().equals(a2.getDestino())
                        && a1.getDestino().equals(a2.getOrigem());
                if(mesmaOrdem || ordemInversa) return true;
            }
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
        for(Aresta a : listaA) {
            if(a.getOrigem().equals(v)) grau++;
            if(a.getDestino().equals(v)) grau++;
        }
        return grau;
    }

    public int grauEntrada(Vertice v){
        int grau=0;
        for(Aresta a : listaA)
            if(a.getDestino().equals(v)) grau++;
        return grau;
    }

    public int grauSaida(Vertice v){
        int grau=0;
        for(Aresta a : listaA)
            if(a.getOrigem().equals(v)) grau++;
        return grau;
    }

    public boolean isRegular(){
        return grauRegularidade() >= 0;
    }

    public int grauRegularidade(){
        if (listaV.isEmpty() || isGrafoMisto()) return -1;

        Vertice primeiro = listaV.get(0);
        if(isDigrafo()){
            int grau = grauEntrada(primeiro);
            if(grauSaida(primeiro) != grau) return -1;

            for(Vertice v : listaV)
                if(grauEntrada(v) != grau || grauSaida(v) != grau) return -1;
            return grau;
        }

        int grau = grau(primeiro);
        for(Vertice v : listaV)
            if(grau(v) != grau) return -1;
        return grau;
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
            if(!a.isDirecionado()) return false;
        return true;
    }

    public boolean isNaoOrientado(){
        for(Aresta a : listaA)
            if(a.isDirecionado()) return false;
        return true;
    }

    public boolean isOrientadoLA(){
        return isDigrafo();
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
