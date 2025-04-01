package org.example.luxusrechnerjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
        timespanOutputLabel.setText(IOHandler.buildTimeSpanOutput(App.dataManager.getBeginDate(), App.dataManager.getEndDate()));
    }

    /**
     * Opens another FXML View in the same window, replacing MainView.
     *
     * @param fxml  Name of the FXML file
     * @param title The new window title to use
     */
    private void openView(String fxml, String title) {
        try {
            Stage stage = (Stage) calculationOutputTable.getScene().getWindow();
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
    private void onClickCalculateButton() {
    }

    @FXML
    private void onClickConfirmTimeSpanButton() {
    }

    @FXML
    private void onClickExpensesButton() {
        //attach view type expenses to window so correct data is manipulated
        Stage stage = (Stage) expensesButton.getScene().getWindow();
        stage.setUserData(DataManager.ExpensesViewType.EXPENSES);
        openView("ExpensesView.fxml", IOHandler.EXPENSES_TITLE);
    }

    @FXML
    private void onClickFixedCostButton() {
        //attach view type fix cost to window so correct data is manipulated
        Stage stage = (Stage) fixedCostButton.getScene().getWindow();
        stage.setUserData(DataManager.ExpensesViewType.FIX_COST);
        openView("ExpensesView.fxml", IOHandler.FIX_COST_TITLE);
    }

    @FXML
    private void onClickConfigButton() {
        openView("ConfigView.fxml", IOHandler.CONFIG_TITLE);
    }

}
