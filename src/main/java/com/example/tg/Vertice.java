package com.example.tg;

public class Vertice {
    private String nomeId;
    private boolean isVisitado;

    public Vertice(String nomeId, boolean isVisitado) {
        this.nomeId = nomeId;
        this.isVisitado = isVisitado;
    }

    public Vertice(){
        this(null, false);
    }
    public String getNomeId() {
        return nomeId;
    }

    public void setNomeId(String nomeId) {
        this.nomeId = nomeId;
    }

    public boolean isVisitado() {
        return isVisitado;
    }

    public void setVisitado(boolean visitado) {
        isVisitado = visitado;
    }
}
