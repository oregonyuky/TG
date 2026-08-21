package com.example.tg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LerArquivo {

    private final String arquivo = "/com/example/tg/input.txt";
    private int[][] matriz = new int[11][11];
    private String[] vertices = new String[11];
    private String[][] vMI = new String[11][2];
    private Grafo grafo = new Grafo();
    private int size = 0;

    public void construirMatriz(String modo){
        InputStream input = getClass().getResourceAsStream(arquivo);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))){
            String linha;
            int linhaMatriz = 0;
            boolean encontrouModo = false;
            while((linha = br.readLine()) != null) {
                linha = linha.trim();
                if(linha.isEmpty()){
                    continue;
                }
                if (linha.equalsIgnoreCase("MA") || linha.equalsIgnoreCase("MI") || linha.equalsIgnoreCase("LA")) {
                    if (encontrouModo) {
                        break;
                    }
                    if (linha.equalsIgnoreCase(modo)) {
                        encontrouModo = true;
                    }
                    continue;
                }
                if (!encontrouModo) {
                    continue;
                }
                switch(modo){
                    case "MA":
                        linhaMatriz = processarMA(linha, linhaMatriz);
                        break;
                    case "MI":
                        break;

                    case "LA":
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private int processarMA(String linha, int linhaMatriz){
        if(linha.contains("/")){
            String[] nomeV = linha.split("/");
            int i=0;
            for(String nv : nomeV) {
                grafo.addVertice(nv);
                vertices[i++] = nv;
                size++;
            }
            return linhaMatriz;
        }
        String[] val = linha.split("\\s+");
        for(int i = 0; i < val.length; i++) {
            Vertice origem = grafo.getListaV().get(linhaMatriz);
            Vertice destino = grafo.getListaV().get(i);
            if(Integer.parseInt(val[i])!=0)
                grafo.addAresta(origem, destino, Integer.parseInt(val[i]), false);
            matriz[linhaMatriz][i] = Integer.parseInt(val[i]);
        }
        return linhaMatriz+1;
    }
    private int processarMI(String linha, int linhaMatriz, boolean direcionado){
        if(!linha.contains(",") && linha.contains("/")){
            String[] nomeV = linha.split("/");
            for(String nv : nomeV) {
                String nome = nv.trim();
                grafo.addVertice(nome);
                vertices[size] = nome;
                size++;
            }
            return linhaMatriz;
        }
        if(linha.contains(",")){
            String[] pares = linha.split("/");
            for(String par : pares){
                String[] partes = par.split(",");
                String nomeOrigem = partes[0].trim();
                String nomeDestino = partes[1].trim();

                Vertice origem = buscarVertice(nomeOrigem);
                Vertice destino = buscarVertice(nomeDestino);

                if(origem == null || destino == null){
                    System.out.println("Vértice não encontrado: " + nomeOrigem + " ou " + nomeDestino);
                    continue;
                }

                grafo.addAresta(origem, destino, 1, direcionado);
                vMI[linhaMatriz][0] = nomeOrigem;
                vMI[linhaMatriz][1] = nomeDestino;
                linhaMatriz++;
            }
            return linhaMatriz;
        }

        return linhaMatriz;
    }

    private Vertice buscarVertice(String nome){
        for(Vertice v : grafo.getListaV())
            if(v.getNomeId().equals(nome)) return v;
        return null;
    }
    public String classificarMatriz(){
        String s="";
        if(grafo.isNaoOrientado()){
            s += (grafo.isNaoOrientado() ? "✅ grafo não orientado" : "❌ é orientado\n");
        } else {
            if(grafo.isDigrafo())
                s += (grafo.isDigrafo() ? "✅ dígrafo\n" : "❌ não é dígrafo\n");
            else
                s += (grafo.isGrafoMisto() ? "✅ grafo misto\n" : "❌ não é grafo misto\n");
        }
        s += (grafo.isRegular() ? "✅ regular" : "❌ não é regular\n");
        if(grafo.isRegular()) s += "   (" + grafo.grau(grafo.getListaV().get(0)) + " - regular)\n";
        s += (grafo.isCompleto() ? "✅ completo" : "❌ não é completo") + "\n";
        s += (grafo.isSimples() ? "✅ simples" : "❌ não é simples") + "\n";
        s += (grafo.isCompletoOrientado() ? "✅ completo e orintado" : "❌ não é completo orientado") + "\n";
        s += (grafo.isDigrafo() ? "✅ dígrafo" : "❌ não é dígrafo") + "\n";
        return s;
    }

    public String mostrarDetalhes(){
        String s="\n";
        s += "Vértices: " + grafo.getListaV().size();
        s += "\nArestas: " + grafo.getListaA().size() + "\n";
        for(Vertice v : grafo.getListaV()){
            s += v.getNomeId() + " - " + grafo.grau(v) + " grau(s)\n";
        }
        return s;
    }

    public String getArquivo() {
        return arquivo;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public String[] getVertices() {
        return vertices;
    }

    public int getSize() {
        return size;
    }
}