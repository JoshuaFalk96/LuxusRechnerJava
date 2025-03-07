package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class MainViewController {
    public Button calculatorButton;
    public Button expensesButton;
    public DatePicker datePicker;
    public Button confirmButton;
    public Button configButton;

    public void initialize(){
        datePicker.setValue(LocalDate.now());
    }

    public void onClickCalculatorButton(ActionEvent actionEvent) {
    }

    public void onClickExpensesButton(ActionEvent actionEvent) {
    }

    public void onClickConfirmButton(ActionEvent actionEvent) {
    }

    public void onClickConfigButton(ActionEvent actionEvent) {
    }
}
