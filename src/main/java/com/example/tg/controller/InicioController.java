package com.example.tg.controller;

import com.example.tg.LerArquivo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnComecar;

    @FXML
    private void onComecar() {
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/com/example/tg/selecionar.fxml") );
            Parent root = loader.load();
            Stage stage = (Stage) btnComecar.getScene() .getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}