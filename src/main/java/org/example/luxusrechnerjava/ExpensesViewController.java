package org.example.luxusrechnerjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.luxusrechnerjava.DataManager.TimedExpense;
import org.example.luxusrechnerjava.IOHandler.ExpensesTableObject;

import java.time.LocalDate;

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
    private Label NewExpenseDateErrorLabel;
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
    private int totalBudget;
    private int savedExpenses;

    @Override
    public void initialize() {
        super.initialize();
        //read saved week budget from config
        totalBudget = App.dataManager.getBudgetConfig();
        //read currently saved expenses
        savedExpenses = App.dataManager.getSavedExpenses();
        final ObservableList<ExpensesTableObject> expenses = FXCollections.observableArrayList(
                IOHandler.buildExpensesTableOutput(new TimedExpense(LocalDate.now(), 2000, "Pizza")),
                IOHandler.buildExpensesTableOutput(new TimedExpense(LocalDate.now(), 321, "Fisch"))
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

    @FXML
    private void onClickNewExpenseAddButton(ActionEvent actionEvent) {

    }

    @FXML
    private void onClickRemoveButton(ActionEvent actionEvent) {
        expensesOutputTable.getSelectionModel().getSelectedItems();
    }
}
