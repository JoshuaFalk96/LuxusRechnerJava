package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalculatorViewController extends SubViewController {
    public TextField balanceInputField;
    public Label balanceErrorLabel;
    public TextField expensesInputField;
    public Label expensesErrorLabel;
    public TextField luxuryMoneyOutputField;
    public TextField remainingBudgetOutputField;
    public Label remainingTimeLabel;
    public Button calculateButton;
    public VBox balanceVBox;
    public VBox expensesVBox;
    boolean saveExpenses;
    private int expenses;

    public void initialize() {
        //TODO replace with real calculation for dates
        int remainingWeeks = (int) (Math.random() * 4);
        int remainingDays = (int) (Math.random() * 8);

        //displaying remaining time till next planed reset
        String remainingDaysString = (remainingDays == 0) ? "" : " und " + remainingDays + " Tage";
        remainingTimeLabel.setText("Noch " + remainingWeeks + " Wochen" + remainingDaysString);

        //TODO read config for saveExpenses
        saveExpenses = Math.random() > 0.5;
        //hiding expenses Input and centering balance input if expenses are saved
        if (saveExpenses) {
            expensesVBox.setDisable(true);
            expensesVBox.setVisible(false);
            HBox.setMargin(balanceVBox, new Insets(0, 0, 0, 135));

            //TODO read expenses
            expenses = (int) (Math.random() * 101);
        }
    }

    private void resetErrorLabels() {
        balanceErrorLabel.setText("");
        expensesErrorLabel.setText("");
    }

    public void onClickCalculateButton(ActionEvent actionEvent) {
        resetErrorLabels();
        //check  if balance input field is empty
        String balanceInput = balanceInputField.getText();
        if (balanceInput.isBlank()) {
            balanceErrorLabel.setText("Input notwendig");
            return;
        }
        //read input Field for balance and convert to int
        int balance;
        try {
            balance = Integer.parseInt(balanceInput);
        } catch (NumberFormatException e) {
            //input is not valid int
            balanceErrorLabel.setText("Keine korrekte Zahl eingegeben");
            return;
        }
        //expenses input only exists if expenses not saved
        if (!saveExpenses) {
            //check if expenses input field is empty
            String expensesInput = expensesInputField.getText();
            if (expensesInput.isBlank()) {
                expensesErrorLabel.setText("Input notwendig");
                return;
            }
            //read expenses input and convert to int
            try {
                expenses = Integer.parseInt(expensesInput);
            } catch (NumberFormatException e) {
                //input is not valid int
                expensesErrorLabel.setText("Keine korrekte Zahl eingegeben");
                return;
            }
        } //no else case as saved expenses already read in initialize methode

        //TODO call calculator

        //display calculated luxury money
        remainingBudgetOutputField.setText(String.valueOf(expenses));
        //display remaining budget for current week
        luxuryMoneyOutputField.setText(String.valueOf(balance));

    }
}
