package org.example.luxusrechnerjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.luxusrechnerjava.DataManager.TimedExpense;
import org.example.luxusrechnerjava.IOHandler.ExpensesTableObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExpensesViewController extends SubViewController {
    @FXML
    private Button removeButton;
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
    @FXML
    private Button newExpenseAddButton;
    private ObservableList<ExpensesTableObject> expensesTableRows;

    @Override
    public void initialize() {
        super.initialize();
        resetErrorLabels();
        //read currently saved expenses
        Map<Integer, TimedExpense> savedExpenses = App.dataManager.getSavedExpenses();
        //define cells tor expenses table
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amount());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description());
        //add saved expenses to table
        expensesTableRows = FXCollections.observableArrayList();
        savedExpenses.forEach((id, savedExpense) ->
                expensesTableRows.add(IOHandler.buildExpensesTableObject(savedExpense)));
        expensesOutputTable.setItems(expensesTableRows);
    }

    /**
     * resets all info labels in the view
     */
    private void resetErrorLabels() {
        newExpenseDateErrorLabel.setText("");
        newExpenseAmountErrorLabel.setText("");
        newExpenseDescriptionErrorLabel.setText("");
    }

    @FXML
    private void onClickNewExpenseAddButton() {
        int amount;
        try {
            amount = IOHandler.parseMoneyInput(newExpenseAmountInputField.getText());
        } catch (IOHandler.NotIntInputException e) {
            newExpenseAmountErrorLabel.setText(IOHandler.ERROR_AT_AMOUNT + e.getErrorText());
            return;
        }
        LocalDate date = newExpenseDatePicker.getValue();
        String description = newExpenseDescriptionInputField.getText();
        TimedExpense newExpense = new TimedExpense(date, amount, description);
        App.dataManager.addSavedExpense(newExpense);
        expensesTableRows.add(IOHandler.buildExpensesTableObject(newExpense));
    }

    @FXML
    private void onClickRemoveButton() {
        //get highlighted rows from table, create copy to avoid errors when removing von ObservableList
        List<ExpensesTableObject> expensesToRemove =
                List.copyOf(expensesOutputTable.getSelectionModel().getSelectedItems());
        //remove selected expenses from saved expenses and table
        expensesToRemove.forEach(expenseToRemove -> {
            App.dataManager.removeSavedExpense(expenseToRemove.id());
            expensesTableRows.remove(expenseToRemove);
        });
    }
}
