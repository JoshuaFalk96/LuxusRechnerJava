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
    public TextField expensesInput;
    public Button addButton;

    public void initialize() {
        currentBudgetOutput.setText("100");
    }

    private void resetInfoLabels() {
        VBox.setMargin(budgetLabelVbox, new Insets(0,0,-20,0));
        VBox.setMargin(newExpenseVBox, new Insets(0,0,-20,0));
        budgetResetLabel.setText("");
        newExpenseLabel.setText("");
    }

    public void onClickResetButton(ActionEvent actionEvent) {
        resetInfoLabels();
        VBox.setMargin(budgetLabelVbox, new Insets(0,0,-10,0));
        budgetResetLabel.setText("Budget wurde zurückgesetz");
    }

    public void onClickAddButton(ActionEvent actionEvent) {
        resetInfoLabels();
        try {
            int newExpenses = Integer.parseInt(expensesInput.getText());
            VBox.setMargin(newExpenseVBox, new Insets(0,0,-10,0));
            newExpenseLabel.setText("Eine Ausgabe von " + newExpenses + " wurde gespeichert");
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }

}
