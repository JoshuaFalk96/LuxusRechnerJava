package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.example.luxusrechnerjava.DataManager.WeekFormat;

public class ConfigViewController extends SubViewController {
    public Button budgetInfoButton;
    public TextField budgetInputField;
    public Button budgetConfirmButton;
    public Button cycleInfoButton;
    public TextField cycleInputField;
    public Button cycleConfirmButton;
    public Button weekFormatInfoButton;
    public Button weekFormatMoSoButton;
    public Button weekFormat7DaysButton;
    public Button saveExpensesInfoButton;
    public Button saveExpensesYesButton;
    public Button saveExpensesNoButton;
    public Button partWeekInfoButton;
    public Button partWeekPartBudgetButton;
    public Button partWeekFullBudgetButton;
    public Label budgetErrorLabel;
    public Label cycleErrorLabel;
    private int budget;
    private int cycleLength;


    private void addTooltip(Control target, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(new Duration(0));
        target.setTooltip(tooltip);
    }

    @Override
    public void initialize() {
        super.initialize();
        //add tooltips to info buttons
        addTooltip(budgetInfoButton, IOHandler.BUDGET_INFO);
        addTooltip(cycleInfoButton, IOHandler.CYCLE_LENGTH_INFO);
        addTooltip(weekFormatInfoButton, IOHandler.WEEK_FORMAT_INFO);
        addTooltip(saveExpensesInfoButton, IOHandler.SAVE_EXPENSES_INFO);
        addTooltip(partWeekInfoButton, IOHandler.PART_BUDGET_INFO);

        //read current values for budget and cycleLength
        budget = LuxuryCalculatorMain.dataManager.getBudgetConfig();
        cycleLength = LuxuryCalculatorMain.dataManager.getCycleLengthConfig();
        //output current values to input fields
        budgetInputField.setPromptText(IOHandler.buildBudgetOutput(budget));
        cycleInputField.setPromptText(IOHandler.buildCycleOutput(cycleLength));

        //read weekFormat, saveExpenses and partBudget from config
        WeekFormat weekFormat = LuxuryCalculatorMain.dataManager.getWeekFormatConfig();
        boolean saveExpenses = LuxuryCalculatorMain.dataManager.getSaveExpensesConfig();
        boolean partBudget = LuxuryCalculatorMain.dataManager.getPartBudgetConfig();
        //highlight buttons for current config
        if (weekFormat == WeekFormat.MO_TO_SO) {
            weekFormatMoSoButton.setStyle("-fx-opacity:1.0");
            weekFormat7DaysButton.setStyle("-fx-opacity:0.5");
        } else {
            weekFormatMoSoButton.setStyle("-fx-opacity:0.5");
            weekFormat7DaysButton.setStyle("-fx-opacity:1.0");
        }
        if (saveExpenses) {
            saveExpensesYesButton.setStyle("-fx-opacity:1.0");
            saveExpensesNoButton.setStyle("-fx-opacity:0.5");
        } else {
            saveExpensesYesButton.setStyle("-fx-opacity:0.5");
            saveExpensesNoButton.setStyle("-fx-opacity:1.0");
        }
        if (partBudget) {
            partWeekPartBudgetButton.setStyle("-fx-opacity:1.0");
            partWeekFullBudgetButton.setStyle("-fx-opacity:0.5");
        } else {
            partWeekPartBudgetButton.setStyle("-fx-opacity:0.5");
            partWeekFullBudgetButton.setStyle("-fx-opacity:1.0");
        }
    }

    private void resetErrorLabels() {
        budgetErrorLabel.setText("");
        cycleErrorLabel.setText("");
    }

    public void onClickBudgetConfirmButton(ActionEvent actionEvent) {
        resetErrorLabels();
        //read new budget from input field
        Integer newBudget = IOHandler.parseMoneyInput(budgetInputField.getText(), budgetErrorLabel);
        if (newBudget == null) return; //input was empty or not parsable
        //save the new budget
        budget = newBudget;
        //TODO save new budget to config

        //empty budget input field and display new budget
        budgetInputField.setText("");
        budgetInputField.setPromptText(IOHandler.buildBudgetOutput(budget));
    }

    public void onClickResetCycleConfirmButton(ActionEvent actionEvent) {
        resetErrorLabels();
        //read new length of cycle from input field
        Integer newCycle = IOHandler.parseIntegerInput(cycleInputField.getText(), cycleErrorLabel);
        if (newCycle == null) return; //input was empty or not parsable
        //save new cycle length
        cycleLength = newCycle;
        //TODO save new cycle length to config

        //empty cycle input and display new cycle length
        cycleInputField.setText("");
        cycleInputField.setPromptText(IOHandler.buildCycleOutput(cycleLength));
    }

    public void onClickWeekFormatMoSoButton(ActionEvent actionEvent) {
        weekFormatMoSoButton.setStyle("-fx-opacity:1.0");
        weekFormat7DaysButton.setStyle("-fx-opacity:0.5");
        //TODO save MO_TO_SO to config for weekFormat
    }

    public void onClickWeekFormat7DaysButton(ActionEvent actionEvent) {
        weekFormatMoSoButton.setStyle("-fx-opacity:0.5");
        weekFormat7DaysButton.setStyle("-fx-opacity:1.0");
        //TODO save SEVEN_DAYS to config for weekFormat
    }

    public void onClickSaveExpensesYesButton(ActionEvent actionEvent) {
        saveExpensesYesButton.setStyle("-fx-opacity:1.0");
        saveExpensesNoButton.setStyle("-fx-opacity:0.5");
        //TODO save true to config for saveExpenses
    }

    public void onClickSaveExpensesNoButton(ActionEvent actionEvent) {
        saveExpensesYesButton.setStyle("-fx-opacity:0.5");
        saveExpensesNoButton.setStyle("-fx-opacity:1.0");
        //TODO save false to config for saveExpenses
    }

    public void onClickPartWeekPartBudgetButton(ActionEvent actionEvent) {
        partWeekPartBudgetButton.setStyle("-fx-opacity:1.0");
        partWeekFullBudgetButton.setStyle("-fx-opacity:0.5");
        //TODO save true to config for partBudget
    }

    public void onClickPartWeekFullBudgetButton(ActionEvent actionEvent) {
        partWeekPartBudgetButton.setStyle("-fx-opacity:0.5");
        partWeekFullBudgetButton.setStyle("-fx-opacity:1.0");
        //TODO save false to config for partBudget
    }
}
