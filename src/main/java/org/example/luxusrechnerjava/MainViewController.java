package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainViewController {

    @FXML
    private Button configButton;
    @FXML
    private Button fixedCostButton;
    @FXML
    private Button expensesButton;
    @FXML
    private Button confirmTimespanButton;
    @FXML
    private DatePicker timespanEndDatePicker;
    @FXML
    private Label timespanOutputLabel;
    @FXML
    private DatePicker timespanBeginingDatePicker;
    @FXML
    private Button calculateButton;
    @FXML
    private Label deductionsErrorLabel;
    @FXML
    private TextField deductionsInputField;
    @FXML
    private Label balanceErrorLabel;
    @FXML
    private TextField balanceInputField;
    @FXML
    private TableColumn amountColumn;
    @FXML
    private TableColumn descriptionColumn;
    @FXML
    private TableView calculationOutputTable;

    public void initialize() {
    }

    /**
     * Opens another FXML View in the same window, replacing MainView.
     *
     * @param fxml  Name of the FXML file
     * @param title The new window title to use
     */
    private void openView(String fxml, String title) {
        try {
            Stage stage = (Stage) configButton.getScene().getWindow();
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

    @FXML
    private void onClickExpensesButton() {
        openView("ExpensesView.fxml", IOHandler.EXPENSES_TITLE);
    }

    @FXML
    private void onClickConfigButton() {
        openView("ConfigView.fxml", IOHandler.CONFIG_TITLE);
    }

    @FXML
    private void onClickCalculateButton() {
    }

    @FXML
    private void onClickConfirmTimespanButton() {
    }

    @FXML
    private void onClickFixedCostButton() {
    }
}
