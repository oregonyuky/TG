package com.example.tg.controller;

import com.example.tg.LerArquivo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MaController {

    public Label labelClassificacao;
    public Label labelDetalhes;

    @FXML
    private GridPane gridMatriz;
    private String modo;
    private LerArquivo la;

    public void setModo(String modo){
        la = new LerArquivo();
        la.construirMatriz(modo);
        montarMatriz();
    }



    private void montarMatriz() {
        String[] v = la.getVertices();
        int[][] m = la.getMatriz();
        int sz = la.getSize();
        gridMatriz.getChildren().clear();
        for (int i = 0; i < sz; i++) {
            Label label = criarCabecalho(v[i]);
            gridMatriz.add( label, i + 1, 0 );
        }
        for (int i = 0; i < sz; i++) {
            Label verticeLinha = criarCabecalho(v[i]);
            gridMatriz.add( verticeLinha, 0, i + 1 );
            for (int j = 0; j < sz; j++) {
                Label valor = criarCelula( String.valueOf(m[i][j]) );
                gridMatriz.add( valor, j + 1, i + 1 );
            }
        }
        labelClassificacao.setText(la.classificarMatriz());
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
}