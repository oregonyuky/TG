package com.example.tg.controller;

import com.example.tg.LerArquivo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MaController {

    public Label labelTitulo;
    public Label labelClassificacao;
    public Label labelDetalhes;

    @FXML
    private Button btnVoltar;

    @FXML
    private GridPane gridMatriz;
    private String modo;
    private LerArquivo la;

    public void setModo(String modo){
        la = new LerArquivo();
        if(modo.equals("MA"))labelTitulo.setText("Matriz de Adjacência");
        else if(modo.equals("MI"))labelTitulo.setText("Matriz de Incidência");
        else labelTitulo.setText("Lista de Adjacencia");
        la.construirMatriz(modo);
        montarMatriz(modo);
    }



    private void montarMatriz(String modo) {

        String[] v = la.getVertices();
        String[] c;
        int qtdL;
        int qtdC;
        if(modo.equals("MA")) {
            c = v;
            qtdL = la.getSize();
            qtdC = la.getSize();
        }
        else if(modo.equals("MI")) {
            c = la.getPares();
            qtdL = la.getSize();
            qtdC = c.length;
        } else {
            c = la.getParesLA();
            qtdL = la.getSize();
            qtdC = c.length;
        }
        if(!modo.equals("LA")) {
            int[][] m = la.getMatriz();
            gridMatriz.getChildren().clear();
            for (int i = 0; i < qtdL; i++) {
                Label label = criarCabecalho(c[i]);
                gridMatriz.add(label, i + 1, 0);
            }
            for (int i = 0; i < qtdL; i++) {
                Label verticeLinha = criarCabecalho(v[i]);
                gridMatriz.add(verticeLinha, 0, i + 1);
                for (int j = 0; j < qtdC; j++) {
                    Label valor = criarCelula(String.valueOf(m[i][j]));
                    gridMatriz.add(valor, j + 1, i + 1);
                }
            }
        } else {
            String[][] m = la.getMatrizLA();
            gridMatriz.getChildren().clear();
            String[] lab = {"origem", "destino", "peso"};
            for (int i = 0; i < qtdC; i++) {
                Label label = criarCabecalho(lab[i]);
                gridMatriz.add(label, i + 1, 0);
            }
            for (int i = 0; i < qtdL; i++) {
                Label verticeLinha = criarCabecalho(v[i]);
                gridMatriz.add(verticeLinha, 0, i + 1);
                for (int j = 0; j < qtdC; j++) {
                    Label valor = criarCelula(m[i][j]);
                    gridMatriz.add(valor, j + 1, i + 1);
                }
            }
        }
        labelClassificacao.setText(la.classificarMatriz(modo));
        labelDetalhes.setText(la.mostrarDetalhes());
    }

    private Label criarCabecalho(String texto) {
        Label label = new Label(texto);
        label.setPrefSize(80, 60);
        label.setStyle(
                "-fx-background-color: #d9d9d9;"+
                "-fx-border-color: #888888;"+
                "-fx-border-width: 1px;"+
                "-fx-alignment: center;"+
                "-fx-font-size: 18px;"+
                "-fx-font-weight: bold;");
        return label;
    }

    private Label criarCelula(String texto) {
        Label label = new Label(texto);
        label.setPrefSize(80, 60);
        label.setStyle(
                "-fx-background-color: white;"+
                "-fx-border-color: #aaaaaa;"+
                "-fx-border-width: 1px;"+
                "-fx-alignment: center;"+
                "-fx-font-size: 17px;");

        return label;
    }

    @FXML
    private void voltarParaSelecao() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/tg/selecionar.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
