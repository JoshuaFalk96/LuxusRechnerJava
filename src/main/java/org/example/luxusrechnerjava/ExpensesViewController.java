package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ExpensesViewController extends SubViewController {
    public VBox budgetLabelVbox;
    public Label budgetResetLabel;
    public TextField currentBudgetOutput;
    public Button resetButton;
    public VBox newExpenseVBox;
    public Label newExpenseLabel;
    public TextField expensesInputField;
    public Button addButton;
    private int totalBudget;
    private int savedExpenses;

    @Override
    public void initialize() {
        super.initialize();
        //TODO read saved week budget from config
        totalBudget = 10000; //placeholder

        //TODO read currently saved expenses
        savedExpenses = (int) (Math.random() * 5000); //placeholder

        //displays currently remaining budget before new expense is added
        currentBudgetOutput.setText(IOHandler.buildMoneyOutput(totalBudget - savedExpenses));
    }

    /**
     * resets all info labels in the view
     */
    private void resetInfoLabels() {
        VBox.setMargin(budgetLabelVbox, new Insets(0, 0, -20, 0));
        VBox.setMargin(newExpenseVBox, new Insets(0, 0, -20, 0));
        budgetResetLabel.setText("");
        newExpenseLabel.setText("");
    }

    /**
     * On clicking reset button sets saved expenses to 0, resetting the current week budget.
     * Informs user of reset and updates current budget.
     *
     * @param actionEvent The Event triggering this function, unused
     */
    public void onClickResetButton(ActionEvent actionEvent) {
        resetInfoLabels();
        //set local saved expenses
        savedExpenses = 0;
        //TODO write expenses = 0 to memory

        //informs user that expenses and budget have been reset
        VBox.setMargin(budgetLabelVbox, new Insets(0, 0, -10, 0));
        budgetResetLabel.setText(IOHandler.BUDGET_RESET);
        //updates current budget field to total budget as expenses are 0
        currentBudgetOutput.setText(IOHandler.buildMoneyOutput(totalBudget));
    }

    /**
     * On clicking the add button checks if the expenses input is filled and tries to
     * parse input as integer, displays error messages if necessary.
     * Adds input expenses to saved expenses and updates saved expenses.
     * Displays added expenses to user and refreshes current budget field.
     *
     * @param actionEvent The Event triggering this function, unused
     */
    public void onClickAddButton(ActionEvent actionEvent) {
        resetInfoLabels();
        //get input as int
        Integer newExpenses = IOHandler.parseInteger(expensesInputField.getText(), newExpenseLabel);
        if (newExpenses == null) {
            //inputs are not set correctly
            //adjust spacing to show error label
            VBox.setMargin(newExpenseVBox, new Insets(0, 0, -20, 0));
            return;
        }
        //adjusts local saved expenses
        savedExpenses = savedExpenses + newExpenses;

        //TODO write new expenses to storage

        //informs user that expense of input amount has been added to saved value
        VBox.setMargin(newExpenseVBox, new Insets(0, 0, -10, 0));
        newExpenseLabel.setText(IOHandler.buildNexExpensesOutput(newExpenses));

        //sets the current budget output to new value after adding new expenses
        currentBudgetOutput.setText(IOHandler.buildMoneyOutput(totalBudget - savedExpenses));
    }

}
