package com.example.tg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LerArquivo {

    private final String arquivo = "/com/example/tg/input.txt";
    private int[][] matriz = new int[11][11];
    private String[] vertices = new String[11];
    private String[][] vMI = new String[11][2];
    private Grafo grafo = new Grafo();
    private int size = 0;
    private int quantidadeArestasMI = 0;
    private String[] pares;

    public String[] getPares() {
        return pares;
    }

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
                        linhaMatriz = processarMI(linha, linhaMatriz, true);
                        break;

                    case "LA":
                        processarLA(linha);
                        break;
                }
            }
            if(modo.equalsIgnoreCase("MI")){
                criarArestaMI(true);
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
            pares = linha.split("/");
            for(int i = 0; i < pares.length; i++){
                String[] partes = pares[i].split(",");
                String nome1 = partes[0].trim();
                String nome2 = partes[1].trim();
                vMI[i][0] = nome1;
                vMI[i][1] = nome2;
            }
            return linhaMatriz;
        }

        String[] valores = linha.split("\\s+");
        quantidadeArestasMI = valores.length;
        for (int coluna = 0; coluna < valores.length; coluna++) {
            matriz[linhaMatriz][coluna] = Integer.parseInt(valores[coluna]);
        }
        return linhaMatriz + 1;
    }
    private void criarArestaMI(boolean direcionado){

        for(int coluna = 0; coluna < quantidadeArestasMI; coluna++){
            if(direcionado){
                Vertice origem = null;
                Vertice destino = null;
                for(int linha = 0; linha < size; linha++){
                    if(matriz[linha][coluna] < 0){
                        origem = buscarVertice(vertices[linha]);
                    }
                    if(matriz[linha][coluna] > 0){
                        destino = buscarVertice(vertices[linha]);
                    }
                }
                if(origem != null && destino != null){
                    grafo.addAresta( origem, destino, 1, true);
                    System.out.println("Aresta adicionada: " + origem.getNomeId()
                            + " -> " + destino.getNomeId());
                }
            }
            else{
                Vertice vertice1 = null;
                Vertice vertice2 = null;
                for(int linha = 0; linha < size; linha++){
                    if(matriz[linha][coluna] > 0){
                        if(vertice1 == null){
                            vertice1 = buscarVertice(vertices[linha]);
                        }
                        else{
                            vertice2 = buscarVertice(vertices[linha]);
                        }
                    }
                }
                if(vertice1 != null && vertice2 != null){
                    grafo.addAresta( vertice1, vertice2, 1, false);
                    System.out.println("Aresta adicionada: " + vertice1.getNomeId()
                            + " -- " + vertice2.getNomeId());
                }
            }
        }
    }

    private void processarLA(String linha) {
        String[] partes = linha.split("\\s+");
        String nomeOrigem = partes[0];
        String[] destinoPeso = partes[1].split(",");
        String nomeDestino = destinoPeso[0];
        int peso = Integer.parseInt(destinoPeso[1]);
        Vertice origem = buscarVertice(nomeOrigem);
        if (origem == null) {
            grafo.addVertice(nomeOrigem);
            vertices[size] = nomeOrigem;
            size++;
            origem = buscarVertice(nomeOrigem);
        }
        Vertice destino = buscarVertice(nomeDestino);
        if (destino == null) {
            grafo.addVertice(nomeDestino);
            vertices[size] = nomeDestino;
            size++;
            destino = buscarVertice(nomeDestino);
        }
        grafo.addAresta(origem, destino, peso, true);
    }

    private Vertice buscarVertice(String nome){
        for(Vertice v : grafo.getListaV())
            if(v.getNomeId().equals(nome)) return v;
        return null;
    }
    public String classificarMatriz(){
        String s="";
        if(grafo.isNaoOrientado()){
            s += (grafo.isNaoOrientado() ? "✅ grafo não orientado\n" : "❌ é orientado\n");
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
    public boolean isOrientadoMA(){
        for(int i=0;i<size;i++){
            for(int j=size-i-1;j<size;j++){
                if(matriz[i][j] != matriz[j][i])return true;
            }
        }
        return false;
    }

    public boolean isOrientadoMI(){
        for(int i=0;i< pares.length;i++){
            for(int j=0;j<size;j++){
                if(matriz[j][i] < 0)return true;
            }
        }
        return false;
    }
    public boolean isOrientadoLA(){

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
