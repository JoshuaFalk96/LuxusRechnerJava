package org.example.luxusrechnerjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {
    static DataManager dataManager;

    @Override
    public void start(Stage stage) throws IOException {
        //Path configPath = Paths.get(System.getProperty("user.home"),"LuxuryCalculator/config.properties");
        dataManager = new RAMDataManager();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(IOHandler.MAIN_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
