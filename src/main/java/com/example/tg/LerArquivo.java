package com.example.tg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivo {

    private final String arquivo = "input.txt";
    private final int[][] matriz = new int[11][11];

    public void construirMatriz(String modo){
        Grafo grafo = new Grafo();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String linha;
            int linhaMatriz = 0;
            while((linha = br.readLine()) != null) {
                if(linha.isEmpty()){
                    continue;
                }
                if(linha.equalsIgnoreCase(modo)){
                    modo = linha;
                    continue;
                }
                switch(modo){
                    case "MA":
                        linhaMatriz = processarMA(linha, grafo, linhaMatriz);
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

    private int processarMA(String linha, Grafo grafo, int linhaMatriz){
        if(linha.contains("/")){
            String[] arestas = linha.split("/");
            for(String a : arestas){
                grafo.addVertice(a);
            }
            return linhaMatriz;
        }
        String[] val = linha.split("\\s+");
        for(int i = 0; i < val.length; i++){
            matriz[linhaMatriz][i] = Integer.parseInt(val[i]);
        }
        return linhaMatriz + 1;
    }

    private void classificarMA(){

    }
}