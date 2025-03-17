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
    public Label expensesInfoLabel2;
    public Label expensesInfoLabel1;

    public void initialize() {
        //set the date picker to have today's date as default
        datePicker.setValue(LocalDate.now());

        //TODO read saveExpenses
        boolean saveExpenses = Math.random() > 0.25; //placeholder


        if (!saveExpenses) {
            //disable accesses to expensesView if not saving expenses
            expensesButton.setDisable(true);
            //display reminder text that expenses not saved visible
            expensesInfoLabel1.setVisible(true);
            expensesInfoLabel2.setVisible(true);
        }
    }

    /**
     * Opens another FXML View in the same window, replacing MainView.
     * @param fxml Name of the FXML file
     * @param title The new window title to use
     */
    private void openView(String fxml, String title) {
        try {
            Stage stage = (Stage) calculatorButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.sizeToScene();
            //unused view controllers are handled by garbage collection
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * On clicking the confirm button sets the internally saved reset date
     * to the date currently in the dae picker and prints the saved date as
     * info in the view.
     * @param actionEvent The Event triggering this function, unused
     */
    public void onClickConfirmButton(ActionEvent actionEvent) {
        LocalDate date = datePicker.getValue();
        //TODO save the new date as reset date in memory

        //output information text to confirm action
        VBox.setMargin(dateResetVbox, new Insets(0, 0, -10, 0));
        dateResetLabel.setText(IOHandler.buildNewDateOutput(date));
    }

    public void onClickCalculatorButton(ActionEvent actionEvent) {
        openView("CalculatorView.fxml", IOHandler.CALCULATOR_TITLE);
    }

    public void onClickExpensesButton(ActionEvent actionEvent) {
        openView("ExpensesView.fxml", IOHandler.EXPENSES_TITLE);
    }

    public void onClickConfigButton(ActionEvent actionEvent) {
        openView("ConfigView.fxml", IOHandler.CONFIG_TITLE);
    }
}
