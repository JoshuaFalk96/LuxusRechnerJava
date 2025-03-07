package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    }

    public void onClickCalculatorButton(ActionEvent actionEvent) {
    }

    public void onClickExpensesButton(ActionEvent actionEvent) {
    }

    public void onClickConfirmButton(ActionEvent actionEvent) {
        VBox.setMargin(dateResetVbox, new Insets(0, 0, -10, 0));
        LocalDate date = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dateResetLabel.setText("Anfang des Zeitraums gesetzt auf den " + date.format(formatter));
    }

    public void onClickConfigButton(ActionEvent actionEvent) {
    }
}
