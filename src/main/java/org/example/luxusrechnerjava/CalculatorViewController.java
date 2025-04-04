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
    private Integer expenses;

    @Override
    public void initialize() {
        super.initialize();
        //calculate how many days are left til end of cycle
        int remainingCycleLength = DateManager.getDaysToCycleEnd(DateManager.getEndOfWeekDate(DateManager.getToday()));
        //displaying remaining time till next planed reset
        remainingTimeLabel.setText(IOHandler.buildRemainingTimeOutput(remainingCycleLength));

        //read config for saveExpenses
        saveExpenses = App.dataManager.getSaveExpensesConfig();

        //hiding expenses Input and centering balance input if expenses are saved
        if (saveExpenses) {
            expensesVBox.setDisable(true);
            expensesVBox.setVisible(false);
            HBox.setMargin(balanceVBox, new Insets(0, 0, 0, 135));

            //read saved expenses
            expenses = App.dataManager.getSavedExpenses();
        }
    }

    /**
     * resets all Error labels in the view
     */
    private void resetErrorLabels() {
        balanceErrorLabel.setText("");
        expensesErrorLabel.setText("");
    }

    /**
     * On clicking the calculate button checks if input field are set correctly
     * and sets error messages in error fields if necessary
     * then calls calculator to calculate luxury money from balance and expenses,
     * displays calculated luxury money and remaining week budget
     * @param actionEvent The Event triggering this function, unused
     */
    public void onClickCalculateButton(ActionEvent actionEvent) {
        resetErrorLabels();
        //get input as int
        Integer balance = IOHandler.parseMoneyInput(balanceInputField.getText(), balanceErrorLabel);
        //expenses input only exists if expenses not saved
        if (!saveExpenses) {
            //get input as int
            expenses = IOHandler.parseMoneyInput(expensesInputField.getText(), expensesErrorLabel);
        } //no else case as saved expenses already read in initialize methode
        if (balance == null || expenses == null) return; //inputs are not set correctly

        //call calculator to determine luxury budget
        int luxuryMoney = LuxuryCalculator.calculateLuxuryMoney(balance, expenses);

        //read week budget from config
         int budget = App.dataManager.getBudgetConfig();

        //display remaining budget for current week
        remainingBudgetOutputField.setText(IOHandler.buildMoneyOutput(budget - expenses));
        //display calculated luxury money
        luxuryMoneyOutputField.setText(IOHandler.buildMoneyOutput(luxuryMoney));
    }
}
