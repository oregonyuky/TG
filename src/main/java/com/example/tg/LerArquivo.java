package com.example.tg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LerArquivo {

    private final String arquivo = "/com/example/tg/entradas/1-regular.txt";
    private int[][] matriz = new int[11][11];
    private String[][] matrizLA = new String[11][11];
    private String[] vertices = new String[11];
    private String[][] vMI = new String[11][2];
    private Grafo grafo = new Grafo();
    private int size = 0;
    private int quantidadeArestasMI = 0;
    private String[] pares;
    private String[] paresLA;
    private int z=0;

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
                        linhaMatriz = processarMI(linha, linhaMatriz);
                        break;

                    case "LA":
                        processarLA(linha);
                        break;
                }
            }
            if(modo.equalsIgnoreCase("MA")) criarArestasMA();
            if(modo.equalsIgnoreCase("MI")) criarArestasMI();
            if(modo.equalsIgnoreCase("LA")) criarArestasLA();
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
            matriz[linhaMatriz][i] = Integer.parseInt(val[i]);
        }
        return linhaMatriz+1;
    }
    private int processarMI(String linha, int linhaMatriz){
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
    private void criarArestasMA(){
        boolean direcionado = isOrientadoMA();
        for(int i = 0; i < size; i++){
            int inicioColuna = direcionado ? 0 : i;
            for(int j = inicioColuna; j < size; j++){
                if(matriz[i][j] != 0){
                    grafo.addAresta( grafo.getListaV().get(i), grafo.getListaV().get(j), matriz[i][j], direcionado);
                }
            }
        }
    }

    private void criarArestasMI(){
        boolean direcionado = isOrientadoMI();
        for(int coluna = 0; coluna < quantidadeArestasMI; coluna++){
            Vertice origem = null;
            Vertice destino = null;
            int peso = 1;

            if(direcionado){
                for(int linha = 0; linha < size; linha++){
                    if(matriz[linha][coluna] < 0){
                        origem = buscarVertice(vertices[linha]);
                    }
                    if(matriz[linha][coluna] > 0){
                        destino = buscarVertice(vertices[linha]);
                    }
                    peso = Math.max(peso, Math.abs(matriz[linha][coluna]));
                }
            }
            else{
                for(int linha = 0; linha < size; linha++){
                    if(matriz[linha][coluna] != 0){
                        if(origem == null){
                            origem = buscarVertice(vertices[linha]);
                        }
                        else{
                            destino = buscarVertice(vertices[linha]);
                        }
                    }
                    peso = Math.max(peso, Math.abs(matriz[linha][coluna]));
                }
            }
            if((origem == null || destino == null) && vMI[coluna][0] != null){
                origem = buscarVertice(vMI[coluna][0]);
                destino = buscarVertice(vMI[coluna][1]);
            }
            if(!direcionado && origem != null && destino == null) destino = origem;
            if(origem != null && destino != null)
                grafo.addAresta(origem, destino, peso, direcionado);
        }
    }

    private void processarLA(String linha) {
        String[] partes = linha.split("\\s+");
        paresLA = linha.split(",|;|\\s");
        matrizLA[z++] = paresLA;
        String nomeOrigem = partes[0];
        String[] destinoPeso = partes[1].split(",");
        String nomeDestino = destinoPeso[0];
        Vertice origem = buscarVertice(nomeOrigem);
        if (origem == null) {
            grafo.addVertice(nomeOrigem);
            vertices[size] = nomeOrigem;
            size++;
        }
        Vertice destino = buscarVertice(nomeDestino);
        if (destino == null) {
            grafo.addVertice(nomeDestino);
            vertices[size] = nomeDestino;
            size++;
        }
    }

    private void criarArestasLA(){
        boolean direcionado = isOrientadoDadosLA();
        boolean[] processada = new boolean[z];
        for(int i = 0; i < z; i++){
            if(processada[i]) continue;
            String origemNome = matrizLA[i][0];
            String destinoNome = matrizLA[i][1];
            int peso = Integer.parseInt(matrizLA[i][2]);
            grafo.addAresta(buscarVertice(origemNome), buscarVertice(destinoNome), peso, direcionado);
            processada[i] = true;

            if(!direcionado && !origemNome.equals(destinoNome)){
                for(int j = i + 1; j < z; j++){
                    if (!processada[j] && matrizLA[j][0].equals(destinoNome) && matrizLA[j][1].equals(origemNome)) {
                        processada[j] = true; break;
                    }
                }
            }
        }
    }

    private boolean isOrientadoDadosLA(){
        for(int i = 0; i < z; i++){
            String origem = matrizLA[i][0];
            String destino = matrizLA[i][1];
            if(origem.equals(destino)) continue;
            int quantidade = 0;
            int quantidadeInversa = 0;
            for(int j = 0; j < z; j++){
                if(matrizLA[j][0].equals(origem) && matrizLA[j][1].equals(destino)) quantidade++;
                if(matrizLA[j][0].equals(destino) && matrizLA[j][1].equals(origem)) quantidadeInversa++;
            }
            if(quantidade != quantidadeInversa) return true;
        }
        return false;
    }

    private Vertice buscarVertice(String nome){
        for(Vertice v : grafo.getListaV())
            if(v.getNomeId().equals(nome)) return v;
        return null;
    }
    public String classificarMatriz(String modo){
        String s="";
        if(modo.equals("MA")) {
            s += (isOrientadoMA() ? "✅ grafo orientado\n" : "❌ não é orientado\n");
        }
        if(modo.equals("MI")) {
            s += (isOrientadoMI() ? "✅ grafo orientado\n" : "❌ não é orientado\n");
        }
        if(modo.equals("LA")){
            s += (grafo.isOrientadoLA() ? "✅ grafo orientado\n" : "❌ não é orientado\n");
        } else {
            if(grafo.isDigrafo())
                s += "✅ dígrafo\n";
            else
                s += (grafo.isGrafoMisto() ? "✅ grafo misto\n" : "❌ não é grafo misto\n");
        }
        int grauRegularidade = grafo.grauRegularidade();
        s += (grauRegularidade >= 0 ? "✅ regular   (" + grauRegularidade + "-regular)\n" : "❌ não é regular\n");
        boolean completo = grafo.isDigrafo() ? grafo.isCompletoOrientado() : grafo.isCompleto();
        s += (completo ? "✅ completo" : "❌ não é completo") + "\n";
        s += (grafo.isSimples() ? "✅ simples" : "❌ não é simples") + "\n";
        return s;
    }

    public String mostrarDetalhes(){
        String s="\n";
        s += "Vértices: " + grafo.getListaV().size();
        s += "\nArestas: " + grafo.getListaA().size() + "\n";
        for(Vertice v : grafo.getListaV()){
            if(grafo.isDigrafo()){
                s += v.getNomeId() + " - entrada: " + grafo.grauEntrada(v) + ", saída: " + grafo.grauSaida(v) + "\n";
            } else {
                s += v.getNomeId() + " - grau: " + grafo.grau(v) + "\n";
            }
        }
        return s;
    }
    public boolean isOrientadoMA(){
        for(int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                if(matriz[i][j] != matriz[j][i])return true;
            }
        }
        return false;
    }

    public boolean isOrientadoMI(){
        for(int i=0;i<quantidadeArestasMI;i++){
            for(int j=0;j<size;j++){
                if(matriz[j][i] < 0)return true;
            }
        }
        return false;
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

    public String[] getParesLA() {
        return paresLA;
    }

    public String[][] getMatrizLA() {
        return matrizLA;
    }
}
