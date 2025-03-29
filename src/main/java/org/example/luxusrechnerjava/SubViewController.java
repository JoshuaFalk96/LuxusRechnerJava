package org.example.luxusrechnerjava;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class SubViewController {
    public Button returnButton;

    public void initialize() {
        //set default focus on return button
        Platform.runLater(() -> returnButton.requestFocus());
    }

    /**
     * On clicking return button on any view that is not MainView sets the view
     * to MainView replacing the current view.
     */
    public void onClickReturnButton() {
        try {
            Stage stage = (Stage) returnButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(SubViewController.class.getResource("MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(IOHandler.MAIN_TITLE);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
