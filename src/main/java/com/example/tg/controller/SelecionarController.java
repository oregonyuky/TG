package com.example.tg.controller;

import com.example.tg.LerArquivo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SelecionarController {
    @FXML
    private Button btnMA;
    private LerArquivo la;

    public void setLerArquivo(LerArquivo la) {
        this.la = la;
    }
    @FXML
    private void selecionarMA() {

        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/com/example/tg/ma.fxml") );
            Parent root = loader.load();
            MaController c = loader.getController();
            c.setModo("MA");
            Stage stage = (Stage) btnMA .getScene() .getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selecionarMI() {
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/com/example/tg/ma.fxml") );
            Parent root = loader.load();
            MaController c = loader.getController();
            c.setModo("MI");
            Stage stage = (Stage) btnMA .getScene() .getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selecionarLA() {
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/com/example/tg/ma.fxml") );
            Parent root = loader.load();
            MaController c = loader.getController();
            c.setModo("LA");
            Stage stage = (Stage) btnMA .getScene() .getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}