package org.example.luxusrechnerjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.luxusrechnerjava.DataManager.TimedExpense;

import java.time.LocalDate;

public class ExpensesViewController extends SubViewController {

    public TableColumn<TimedExpense, String> dateColumn;
    public TableColumn<TimedExpense, Number> amountColumn;
    public TableColumn<TimedExpense, String> descriptionColumn;
    @FXML
    TableView<TimedExpense> expensesOutputTable;
    public DatePicker newExpenseDatePicker;
    public Label NewExpenseDateErrorLabel;
    public TextField newExpenseAmountInputField;
    public Label newExpenseAmountErrorLabel;
    public TextField newExpenseDescriptionInputField;
    public Label newExpenseDescriptionErrorLabel;
    public Button newExpenseAddButton;
    private int totalBudget;
    private int savedExpenses;

    @Override
    public void initialize() {
        super.initialize();
        //read saved week budget from config
        totalBudget = App.dataManager.getBudgetConfig();
        //read currently saved expenses
        savedExpenses = App.dataManager.getSavedExpenses();
        final ObservableList<TimedExpense> expenses = FXCollections.observableArrayList(
                new TimedExpense(LocalDate.now(), 2000, "Pizza"),
                new TimedExpense(LocalDate.now(), 321, "Fisch")
        );
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amount());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description());
        expensesOutputTable.setItems(expenses);
    }

    /**
     * resets all info labels in the view
     */
    private void resetInfoLabels() {
    }

    public void onClickNewExpenseAddButton(ActionEvent actionEvent) {

    }
}
