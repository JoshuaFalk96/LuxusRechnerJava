package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.example.luxusrechnerjava.DataManager.WeekFormat;

public class ConfigViewController extends SubViewController {
    public Button budgetInfoButton;
    public TextField budgetInputField;
    public Button budgetConfirmButton;
    public Button weekFormatInfoButton;
    public Button weekFormatMoSoButton;
    public Button weekFormat7DaysButton;
    public Button partWeekInfoButton;
    public Button partWeekPartBudgetButton;
    public Button partWeekFullBudgetButton;
    public Label budgetErrorLabel;


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
        addTooltip(weekFormatInfoButton, IOHandler.WEEK_FORMAT_INFO);
        addTooltip(partWeekInfoButton, IOHandler.PART_BUDGET_INFO);

        //read current values for budget
        int budget = App.dataManager.getBudgetConfig();
        //output current values to input fields
        budgetInputField.setPromptText(IOHandler.buildBudgetOutput(budget));

        //read weekFormat, saveExpenses and partBudget from config
        WeekFormat weekFormat = App.dataManager.getWeekFormatConfig();
        boolean partBudget = App.dataManager.getPartBudgetConfig();
        //highlight buttons for current config
        if (weekFormat == WeekFormat.MO_TO_SO) {
            weekFormatMoSoButton.setStyle("-fx-opacity:1.0");
            weekFormat7DaysButton.setStyle("-fx-opacity:0.5");
        } else {
            weekFormatMoSoButton.setStyle("-fx-opacity:0.5");
            weekFormat7DaysButton.setStyle("-fx-opacity:1.0");
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
    }

    public void onClickBudgetConfirmButton() {
        resetErrorLabels();
        //read new budget from input field
        int newBudget;
        try {
            newBudget = IOHandler.parseMoneyInput(budgetInputField.getText());
        } catch (IOHandler.NotIntInputException e) {
            //input field was not filled correctly
            budgetErrorLabel.setText(e.getErrorText());
            return;
        }
        //save new budget to config
        App.dataManager.setBudgetConfig(newBudget);
        //empty budget input field and display new budget
        budgetInputField.setText("");
        budgetInputField.setPromptText(IOHandler.buildBudgetOutput(newBudget));
    }

    public void onClickWeekFormatMoSoButton() {
        //set MoToSo as highlighted Button
        weekFormatMoSoButton.setStyle("-fx-opacity:1.0");
        weekFormat7DaysButton.setStyle("-fx-opacity:0.5");
        //save MO_TO_SO to config for weekFormat
        App.dataManager.setWeekFormatConfig(WeekFormat.MO_TO_SO);
    }

    public void onClickWeekFormat7DaysButton() {
        //set 7Days as highlighted button
        weekFormatMoSoButton.setStyle("-fx-opacity:0.5");
        weekFormat7DaysButton.setStyle("-fx-opacity:1.0");
        //save SEVEN_DAYS to config for weekFormat
        App.dataManager.setWeekFormatConfig(WeekFormat.SEVEN_DAYS);
    }

    public void onClickPartWeekPartBudgetButton() {
        //set partBudget as highlighted button
        partWeekPartBudgetButton.setStyle("-fx-opacity:1.0");
        partWeekFullBudgetButton.setStyle("-fx-opacity:0.5");
        //save true to config for partBudget
        App.dataManager.setPartBudgetConfig(true);
    }

    public void onClickPartWeekFullBudgetButton() {
        //set fullBudget as highlighted button
        partWeekPartBudgetButton.setStyle("-fx-opacity:0.5");
        partWeekFullBudgetButton.setStyle("-fx-opacity:1.0");
        //save false to config for partBudget
        App.dataManager.setPartBudgetConfig(false);
    }
}
