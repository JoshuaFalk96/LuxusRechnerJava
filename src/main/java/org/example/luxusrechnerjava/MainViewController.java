package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainViewController {
    public Button calculatorButton;
    public Button expensesButton;
    public DatePicker datePicker;
    public Button confirmButton;
    public Button configButton;
    public Label dateResetLabel;
    public VBox dateResetVbox;

    public void initialize() {
        datePicker.setValue(LocalDate.now());
        //TODO read saveExpenses
        boolean saveExpenses = Math.random() > 0.75;
        //disable accesses to expensesView if not saving expenses
        if (!saveExpenses) {
            expensesButton.setDisable(true);
        }
    }

    public void onClickCalculatorButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) calculatorButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("CalculatorView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Luxusrechner - Berechnen");
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickExpensesButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) expensesButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("ExpensesView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Luxusrechner - Ausgaben");
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onClickConfirmButton(ActionEvent actionEvent) {
        VBox.setMargin(dateResetVbox, new Insets(0, 0, -10, 0));
        LocalDate date = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dateResetLabel.setText("Anfang des Zeitraums gesetzt auf den " + date.format(formatter));
    }

    public void onClickConfigButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) configButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("ConfigView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Luxusrechner - Einstellungen");
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
