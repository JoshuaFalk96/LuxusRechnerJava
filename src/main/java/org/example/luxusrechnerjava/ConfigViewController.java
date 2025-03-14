package org.example.luxusrechnerjava;

import javafx.event.ActionEvent;
import javafx.scene.Node;
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
    private static final String budgetInfo = "Wie hoch ist das Budget pro Woche in ganzen Euro";
    private static final String cycleLengthInfo = "Wie viele Tage vergehen gewöhnlich zwischen Zahlungen";
    private static final String weekFormatInfo = "Wochen können von Montag bis Sonntag oder in 7 Tage Abschnitten ab Zahlungeingang berechnet werden";
    private static final String saveExpensesInfo = "Sollen die Ausgaben der aktuellen Woche gespeichert werden?";
    private static final String partBudgetInfo = "Wenn der Zeitraum nicht volle Wochen enthält, werden diese als ganze Wochen bertachtet oder das Budget entsprechend der Tage verringert";
    private int budget;
    private int cycleLength;

    private void addTooltip(Control target, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(new Duration(0));
        target.setTooltip(tooltip);
    }

    private void setBudgetOutput(int budget) {
        budgetInputField.setPromptText("Aktuell: " + budget + " €");
    }

    private void setCycleOutput(int days) {
        resetCycleInputField.setPromptText("Aktuell: " + days + " Tage");
    }

    @Override
    public void initialize() {
        super.initialize();
        //add tooltips to info buttons
        addTooltip(budgetInfoButton, budgetInfo);
        addTooltip(resetCycleInfoButton, cycleLengthInfo);
        addTooltip(weekFormatInfoButton, weekFormatInfo);
        addTooltip(saveExpensesInfoButton, saveExpensesInfo);
        addTooltip(partWeekInfoButton, partBudgetInfo);
        //output current values to input fields
        //TODO read current values for budget and cycleLength
        budget = (int)(Math.random() * 20 + 90); //placeholder
        cycleLength = (int)(Math.random() * 5 + 28); //placeholder
        setBudgetOutput(budget);
        setCycleOutput(cycleLength);

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
