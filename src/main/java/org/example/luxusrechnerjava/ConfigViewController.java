package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class ConfigViewController extends SubViewController {
    public Button budgetInfoButton;
    public TextField budgetInputField;
    public Button budgetConfirmButton;
    public Button resetCycleInfoButton;
    public TextField resetCycleInputField;
    public Button resetCycleConfirmButton;
    public Button weekFormatInfoButton;
    public Button weekFormatMoSoButton;
    public Button weekFormat7DaysButton;
    public Button saveExpensesInfoButton;
    public Button saveExpensesYesButton;
    public Button saveExpensesNoButton;
    public Button partWeekInfoButton;
    public Button partWeekParBudgetButton;
    public Button partWeekFullBudgetButton;
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
        addTooltip(budgetInfoButton, IOHandler.BUDGETINFO);
        addTooltip(resetCycleInfoButton, IOHandler.CYCLELENGTHINFO);
        addTooltip(weekFormatInfoButton, IOHandler.WEEKFORMATINFO);
        addTooltip(saveExpensesInfoButton, IOHandler.SAVEEXPENESINFO);
        addTooltip(partWeekInfoButton, IOHandler.PARTBUDGETINFO);
        //output current values to input fields
        //TODO read current values for budget and cycleLength
        budget = (int)(Math.random() * 2000 + 9000); //placeholder
        cycleLength = (int)(Math.random() * 5 + 28); //placeholder

        budgetInputField.setPromptText(IOHandler.buildBudgetOutput(budget));
        resetCycleInputField.setPromptText(IOHandler.buildCycleOutput(cycleLength));

    }

    public void onClickBudgetConfirmButton(ActionEvent actionEvent) {

    }

    public void onClickResetCycleConfirmButton(ActionEvent actionEvent) {

    }

    public void onClickWeekFormatMoSoButton(ActionEvent actionEvent) {

    }

    public void onClickWeekFormat7DaysButton(ActionEvent actionEvent) {

    }

    public void onClickSaveExpensesYesButton(ActionEvent actionEvent) {

    }

    public void onClickSaveExpensesNoButton(ActionEvent actionEvent) {

    }

    public void onClickPartWeekPartBudgetButton(ActionEvent actionEvent) {

    }

    public void onClickPartWeekFullBudgetButton(ActionEvent actionEvent) {

    }
}
