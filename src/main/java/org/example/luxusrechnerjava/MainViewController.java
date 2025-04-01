package org.example.luxusrechnerjava;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainViewController {
    @FXML
    private Label timeSpanEndErrorLabel;
    @FXML
    private Label timeSpanBeginingErrorLabel;
    @FXML
    private Button configButton;
    @FXML
    private Button fixedCostButton;
    @FXML
    private Button expensesButton;
    @FXML
    private Button confirmTimeSpanButton;
    @FXML
    private DatePicker timeSpanEndDatePicker;
    @FXML
    private Label timeSpanOutputLabel;
    @FXML
    private DatePicker timeSpanBeginingDatePicker;
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
    private TableColumn<CalculateTableEntry, String> amountColumn;
    @FXML
    private TableColumn<CalculateTableEntry, String> descriptionColumn;
    @FXML
    private TableView<CalculateTableEntry> calculationOutputTable;

    private record CalculateTableEntry(SimpleStringProperty description, SimpleStringProperty amount) {
    }


    private void resetErrorLabels() {
        balanceErrorLabel.setText("");
        balanceInputField.setStyle("");
        deductionsErrorLabel.setText("");
        deductionsInputField.setStyle("");
        timeSpanBeginingErrorLabel.setText("");
        timeSpanBeginingErrorLabel.setAlignment(Pos.CENTER);
        timeSpanBeginingDatePicker.setStyle("");
        timeSpanEndErrorLabel.setText("");
        timeSpanEndErrorLabel.setAlignment(Pos.CENTER);
        timeSpanEndDatePicker.setStyle("");
    }

    private void outputCalculationToTable(int balance, int deductions, int expenses, int fixCost, int result) {
        //TODO centralize Strings
        int currentWeekBudget = App.dataManager.getBudgetConfig() - expenses;
        String currentBudgetOutput;
        if (currentWeekBudget < 0) {
            currentBudgetOutput = "0€ (" + IOHandler.buildMoneyOutput(Math.abs(currentWeekBudget)) + " überzogen)";
        } else {
            currentBudgetOutput =IOHandler.buildMoneyOutput(currentWeekBudget);
        }
        ObservableList<CalculateTableEntry> tableEntries = FXCollections.observableArrayList(
                new CalculateTableEntry(new SimpleStringProperty("Kontostand"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(balance))),
                new CalculateTableEntry(new SimpleStringProperty("Abzüge"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(deductions))),
                new CalculateTableEntry(new SimpleStringProperty("Fixkosten"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(fixCost))),
                new CalculateTableEntry(new SimpleStringProperty("Budget diese Woche"),
                        new SimpleStringProperty(currentBudgetOutput)),
                //TODO get future full budgets and number of weeks
                new CalculateTableEntry(new SimpleStringProperty("Budget für 3 Wochen"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(30000))),
                //TODO get budget für last week and number of days
                new CalculateTableEntry(new SimpleStringProperty("Budget für 5 Tage"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(7000))),
                new CalculateTableEntry(new SimpleStringProperty("Luxus Geld"),
                        new SimpleStringProperty(IOHandler.buildMoneyOutput(result)))
        );
        calculationOutputTable.setItems(tableEntries);
    }

    public void initialize() {
        Platform.runLater(() -> calculateButton.requestFocus());
        resetErrorLabels();
        //read begin and end date and display in view
        timeSpanOutputLabel.setText(IOHandler.buildTimeSpanOutput(
                App.dataManager.getBeginDate(), App.dataManager.getEndDate()));
        //setup table columns
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amount());
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
        resetErrorLabels();
        //read balance input
        int balance;
        try {
            balance = IOHandler.parseMoneyInput(balanceInputField.getText());
        } catch (IOHandler.NotIntInputException e) {
            //print error and abort
            balanceErrorLabel.setText(e.getErrorText());
            balanceInputField.setStyle(IOHandler.RED_BORDER_STYLE);
            return;
        }
        //read deductions input
        String deductionsInput = deductionsInputField.getText();
        int deductions = 0;
        if (!deductionsInput.isBlank()) {
            //as deductions are optional only parse if field is set
            try {
                deductions = IOHandler.parseMoneyInput(deductionsInput);
            } catch (IOHandler.NotIntInputException e) {
                //deductions are not empty but not parseable
                deductionsErrorLabel.setText(e.getErrorText());
                deductionsInputField.setStyle(IOHandler.RED_BORDER_STYLE);
                return;
            }
        }
        //read and accumulate expenses
        int totalExpenses = 0;
        for (DataManager.TimedExpense expense : App.dataManager.getSavedExpenses().values()) {
            //TODO check date of expense before adding to total
            totalExpenses += expense.amount();
        }
        //read and accumulate fix cost
        int totalFixCost = 0;
        for (DataManager.TimedExpense fixCost : App.dataManager.getSavedFixCost().values()) {
            //TODO check date of fix cost before adding to total
            totalFixCost += fixCost.amount();
        }
        //calculate remaining luxury money
        //TODO rework LuxuryCalculator to new system
        int result = LuxuryCalculator.calculateLuxuryMoney(balance, totalExpenses);
        result = result - deductions - totalFixCost;
        //output calculation to view
        outputCalculationToTable(balance, deductions, totalExpenses, totalFixCost, result);
    }

    @FXML
    private void onClickConfirmTimeSpanButton() {
        resetErrorLabels();
        //read begin date for time span from input
        LocalDate beginDate = timeSpanBeginingDatePicker.getValue();
        if (beginDate == null) {
            //begin date input could not be parsed as date
            //print error message and abort
            timeSpanBeginingErrorLabel.setText(IOHandler.DATE_INPUT_ERROR);
            timeSpanBeginingDatePicker.setStyle(IOHandler.RED_BORDER_STYLE);
            return;
        }
        //read end date for time span from input
        LocalDate endDate = timeSpanEndDatePicker.getValue();
        if (endDate == null) {
            //end date input could not be parsed as date
            //print error message and abort
            timeSpanEndErrorLabel.setText(IOHandler.DATE_INPUT_ERROR);
            timeSpanEndDatePicker.setStyle(IOHandler.RED_BORDER_STYLE);
            return;
        }
        //at this point both inputs are dates
        if (beginDate.isAfter(endDate)) {
            //begin of time span can not be after end of time span
            //add part of error message to both labels
            timeSpanBeginingErrorLabel.setText(IOHandler.WRONG_TIME_SPAN_ORDER1);
            timeSpanEndErrorLabel.setText(IOHandler.WRONG_TIME_SPAN_ORDER2);
            //set alignment so text joins together
            timeSpanBeginingErrorLabel.setAlignment(Pos.CENTER_RIGHT);
            timeSpanEndErrorLabel.setAlignment(Pos.CENTER_LEFT);
            //set border of both fields red
            timeSpanBeginingDatePicker.setStyle(IOHandler.RED_BORDER_STYLE);
            timeSpanEndDatePicker.setStyle(IOHandler.RED_BORDER_STYLE);
            return;
        }
        //at this point inputs are correct
        //save new time span
        App.dataManager.setBeginDate(beginDate);
        App.dataManager.setEndDate(endDate);
        //print new time span to view
        timeSpanOutputLabel.setText(IOHandler.buildTimeSpanOutput(beginDate, endDate));
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
