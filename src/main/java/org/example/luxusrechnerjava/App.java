package org.example.luxusrechnerjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    static DataManager dataManager;

    @Override
    public void start(Stage stage) throws IOException {
        dataManager = new FileDataManager();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(IOHandler.MAIN_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        //ensures all changes are saved before program terminates
        dataManager.saveChanges();
    }

    public static void main(String[] args) {
        launch();
    }
}
