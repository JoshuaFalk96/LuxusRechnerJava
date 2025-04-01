package org.example.luxusrechnerjava;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.luxusrechnerjava.DataManager.TimedExpense;
import org.example.luxusrechnerjava.IOHandler.ExpensesTableObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpensesViewController extends SubViewController {
    @FXML
    private Label newExpenseLabel;
    @FXML
    private TableColumn<ExpensesTableObject, String> dateColumn;
    @FXML
    private TableColumn<ExpensesTableObject, String> amountColumn;
    @FXML
    private TableColumn<ExpensesTableObject, String> descriptionColumn;
    @FXML
    private TableView<ExpensesTableObject> expensesOutputTable;
    @FXML
    private DatePicker newExpenseDatePicker;
    @FXML
    private Label newExpenseDateErrorLabel;
    @FXML
    private TextField newExpenseAmountInputField;
    @FXML
    private Label newExpenseAmountErrorLabel;
    @FXML
    private TextField newExpenseDescriptionInputField;
    @FXML
    private Label newExpenseDescriptionErrorLabel;
    private ObservableList<ExpensesTableObject> expensesTableRows;
    private DataManager.ExpensesViewType viewType;

    @Override
    public void initialize() {
        super.initialize();
        resetErrorLabels();
        Platform.runLater(() -> {
            //read if view is for expenses or for fix cost
            viewType = (DataManager.ExpensesViewType) expensesOutputTable.getScene().getWindow().getUserData();
            //holds saved expenses or fix cost for table
            Map<Integer, TimedExpense> savedExpensesMap;
            //setup view either for expenses or fix cost
            switch (viewType) {
                case EXPENSES -> {
                    //load saved expenses
                    savedExpensesMap = App.dataManager.getSavedExpenses();
                    //set label text
                    newExpenseLabel.setText(IOHandler.NEW_EXPENSE);
                    expensesOutputTable.setPlaceholder(new Label(IOHandler.EXPENSES_PLACEHOLDER));
                }
                case FIX_COST -> {
                    //load saved fix cost
                    savedExpensesMap = App.dataManager.getSavedFixCost();
                    //set label text
                    newExpenseLabel.setText(IOHandler.NEW_FIX_COST);
                    expensesOutputTable.setPlaceholder(new Label(IOHandler.FIX_COST_PLACEHOLDER));
                }
                default -> savedExpensesMap = new HashMap<>();
            }
            //define cells tor expenses table
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().date());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().amount());
            descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description());
            //add saved expenses/fix cost to table
            expensesTableRows = FXCollections.observableArrayList();
            savedExpensesMap.forEach((id, savedExpense) ->
                    expensesTableRows.add(IOHandler.buildExpensesTableObject(savedExpense)));
            expensesOutputTable.setItems(expensesTableRows);
        });
    }

    /**
     * resets all info labels in the view
     */
    private void resetErrorLabels() {
        newExpenseDateErrorLabel.setText("");
        newExpenseDatePicker.setStyle("");
        newExpenseAmountErrorLabel.setText("");
        newExpenseAmountInputField.setStyle("");
        newExpenseDescriptionErrorLabel.setText("");
    }

    /**
     * reads date, amount and description fields and creates new timedExpense object
     * from them. Adds new object ot saved expenses or saves fix cost based on
     * view type. adds new object to table in view.
     * If amount fails to parse, outputs error message and aborts.
     */
    @FXML
    private void onClickNewExpenseAddButton() {
        resetErrorLabels();
        //read date input
        LocalDate date = newExpenseDatePicker.getValue();
        //check if input was successful parsed as date
        if (date == null) {
            //date input could not be parsed, print error and abort
            newExpenseDateErrorLabel.setText(IOHandler.DATE_INPUT_ERROR);
            newExpenseDatePicker.setStyle("-fx-border-color: red");
            return;
        }
        //read input from amount field
        int amount;
        try {
            amount = IOHandler.parseMoneyInput(newExpenseAmountInputField.getText());
        } catch (IOHandler.NotIntInputException e) {
            //print error as amount field is not correctly set and abort
            newExpenseAmountErrorLabel.setText(IOHandler.ERROR_AT_AMOUNT + e.getErrorText());
            newExpenseAmountInputField.setStyle("-fx-border-color: red");
            return;
        }
        //read input from description field
        //description is optional so no input validation
        String description = newExpenseDescriptionInputField.getText();
        //create new internal expense/fix cost object
        TimedExpense newSavedValue = new TimedExpense(date, amount, description);
        //save new object to correct list based on view type
        switch (viewType) {
            case EXPENSES -> App.dataManager.addSavedExpense(newSavedValue);
            case FIX_COST -> App.dataManager.addSavedFixCost(newSavedValue);
            default -> {
                //view not set up correctly, abort operation
                return;
            }
        }
        //add new object to view table
        expensesTableRows.add(IOHandler.buildExpensesTableObject(newSavedValue));
    }

    /**
     * reads the ids of the highlighted rows and removes the corresponding entries
     * from memory and the view table
     */
    @FXML
    private void onClickRemoveButton() {
        //get highlighted rows from table, create copy to avoid errors when removing from ObservableList
        List<ExpensesTableObject> expensesToRemove =
                List.copyOf(expensesOutputTable.getSelectionModel().getSelectedItems());
        //remove selected objects from saved expenses/fix cost
        switch (viewType) {
            case EXPENSES -> expensesToRemove.forEach(expenseToRemove -> {
                App.dataManager.removeSavedExpense(expenseToRemove.id());
            });
            case FIX_COST -> expensesToRemove.forEach(expenseToRemove -> {
                App.dataManager.removeSavedFixCost(expenseToRemove.id());
            });
            default -> {
                //view not set up correctly, abort operation
                return;
            }
        }
        //remove expenses from view table
        expensesTableRows.removeAll(expensesToRemove);
    }
}
