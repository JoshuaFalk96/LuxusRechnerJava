package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SubViewController {
    public Button returnButton;


    public void onClickReturnButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) returnButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(SubViewController.class.getResource("MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Luxusrechner");
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
