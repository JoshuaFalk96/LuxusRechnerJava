package org.example.luxusrechnerjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LuxuryCalculatorMain extends Application {
    static DataManager dataManager;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LuxuryCalculatorMain.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(IOHandler.MAIN_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        dataManager = new RAMManager();
        launch();
    }
}
