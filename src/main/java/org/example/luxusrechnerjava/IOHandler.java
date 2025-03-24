package org.example.luxusrechnerjava;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Labeled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IOHandler {
    public static final String BUDGET_INFO = "Wie hoch ist das Budget pro Woche in ganzen Euro";
    public static final String CYCLE_LENGTH_INFO = "Wie viele Tage vergehen gewöhnlich zwischen Zahlungen";
    public static final String WEEK_FORMAT_INFO = "Wochen können von Montag bis Sonntag oder \n in 7 Tage Abschnitten ab Zahlungeingang berechnet werden";
    public static final String SAVE_EXPENSES_INFO = "Sollen die Ausgaben der aktuellen Woche gespeichert werden?";
    public static final String PART_BUDGET_INFO = "Wenn der Zeitraum nicht volle Wochen enthält, werden diese \n als ganze Wochen bertachtet oder das Budget entsprechend \n der Tage verringert";
    public static final String BUDGET_RESET = "Budget wurde zurückgesetz";
    public static final String MAIN_TITLE = "Luxusrechner";
    public static final String CALCULATOR_TITLE = "Luxusrechner - Rechner";
    public static final String EXPENSES_TITLE = "Luxusrechner - Ausgaben";
    public static final String CONFIG_TITLE = "Luxusrechner - Einstellungen";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String FRONT_CURRENCY_SYMBOL = ""; //for currencies like $100
    private static final String BACK_CURRENCY_SYMBOL = "€"; //for currencies like 100€
    private static final String SUB_CURRENCY_DIVIDER = ","; //language dependent
    private static final int CURRENCY_CONVERSION_FACTOR = 100; //for currencies with smaller currency like Euro and Cent, 1 otherwise
    private static final String INPUT_EMPTY = "Input Notwendig";
    private static final String INPUT_NOT_INT = "Keine korrekte Zahl eingegeben";
    private static final String REMAINING_DAYS_1 = " und ";
    private static final String REMAINING_DAYS_2_SINGULAR = " Tag";
    private static final String REMAINING_DAYS_2_PLURAL = " Tage";
    private static final String REMAINING_WEEKS_NOT_EXIST = "Noch diese Woche";
    private static final String REMAINING_WEEKS_EXIST = "Nach dieser Woche noch ";
    private static final String REMAINING_WEEKS_SINGULAR = " Woche";
    private static final String REMAINING_WEEKS_PLURAL = " Wochen";
    private static final String NEW_EXPENSES_1 = "Eine Ausgabe von ";
    private static final String NEW_EXPENSES_2 = " wurde gespeichert";
    private static final String CURRENTLY = "Aktuell: ";
    private static final String DAYS = " Tage";
    private static final String NEW_RESET_DATE = "Anfang des Zeitraums gesetzt auf den ";

    public record ExpensesTableObject(SimpleStringProperty date, SimpleStringProperty amount,
                                      SimpleStringProperty description) {
    }

    /**
     * parses given String to integer
     * writes error messages to given output if input is empty or not valid
     *
     * @param input       String from input field for parsing
     * @param errorOutput Output for error message
     * @return input as integer if parsable, null otherwise
     */
    public static Integer parseIntegerInput(String input, Labeled errorOutput) {
        //check if input is empty
        if (input.isBlank()) {
            errorOutput.setText(INPUT_EMPTY);
            return null;
        }
        //parse input to integer
        int output;
        try {
            output = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            //input is not valid integer
            errorOutput.setText(INPUT_NOT_INT);
            return null;
        }
        return output;
    }

    public static Integer parseMoneyInput(String input, Labeled errorOutput) {
        //input may need to be transformed to the smaller unit of currency or not depending on input
        //so local changeable conversion factor is needed
        int localCurrencyConvergionFactor = CURRENCY_CONVERSION_FACTOR;
        if (CURRENCY_CONVERSION_FACTOR != 1) {
            //currency has smaller fraction currency, e.g. Euro and Cent
            if (input.contains(",") || input.contains(".")) {
                //input contains currency fraction, e.g. Cent for Euro
                //removes dividing characters to be valid integer
                input = input.replace(",", "");
                input = input.replace(".", "");
                //after removing divider, input already in smaller currency unit
                localCurrencyConvergionFactor = 1;
            }
        }
        Integer output = parseIntegerInput(input, errorOutput);
        if (output == null) {
            //forwards null as error value for invalid input
            return null;
        } else {
            //convert input to smaller currency unit for internal processing
            return output * localCurrencyConvergionFactor;
        }
    }

    public static String buildRemainingTimeOutput(int totalDays) {
        int weeks = totalDays / 7;
        int days = totalDays % 7;
        boolean partWeek = App.dataManager.getPartBudgetConfig();
        if (!partWeek && days != 0) {
            //if partial weeks are treated as full weeks and a partial week exists
            //add partial week as full week
            weeks++;
            //disregard partial week display
            days = 0;
        }
        //if partial week exists add to output in days
        String remainingDaysString;
        switch (days) {
            case 0 -> remainingDaysString = "";
            case 1 -> remainingDaysString = REMAINING_DAYS_1 + days + REMAINING_DAYS_2_SINGULAR;
            default -> remainingDaysString = REMAINING_DAYS_1 + days + REMAINING_DAYS_2_PLURAL;
        }
        String remainingWeeksString;
        switch (weeks) {
            case 0 -> remainingWeeksString = REMAINING_WEEKS_NOT_EXIST;
            case 1 -> remainingWeeksString = REMAINING_WEEKS_EXIST + weeks + REMAINING_WEEKS_SINGULAR;
            default -> remainingWeeksString = REMAINING_WEEKS_EXIST + weeks + REMAINING_WEEKS_PLURAL;
        }
        return remainingWeeksString + remainingDaysString;
    }

    /**
     * Converts internal money value to String with
     * divider for sub currency, e.g. Euro,Cent
     *
     * @param amount Money value to be displayed
     * @return String containing divider for displaying
     */
    public static String buildMoneyOutput(int amount) {
        //Calculate fraction to be displayed after dividing symbol
        int fraction = Math.abs(amount % CURRENCY_CONVERSION_FACTOR);
        //Add leading 0s to fraction if necessary for fixed point display
        StringBuilder fractionString = new StringBuilder(String.valueOf(fraction));
        for (int i = fractionString.length(); i < String.valueOf(CURRENCY_CONVERSION_FACTOR).length() - 1; i++) {
            fractionString.insert(0, "0");
        }
        return FRONT_CURRENCY_SYMBOL + (amount / CURRENCY_CONVERSION_FACTOR) + SUB_CURRENCY_DIVIDER + fractionString + BACK_CURRENCY_SYMBOL;
    }

    public static String buildNexExpensesOutput(int newExpenses) {
        return NEW_EXPENSES_1 + buildMoneyOutput(newExpenses) + NEW_EXPENSES_2;
    }

    public static String buildBudgetOutput(int budget) {
        return CURRENTLY + buildMoneyOutput(budget);
    }

    public static String buildCycleOutput(int days) {
        return CURRENTLY + days + DAYS;
    }

    public static String buildNewDateOutput(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IOHandler.DATE_FORMAT);
        return NEW_RESET_DATE + date.format(formatter);
    }

    public static ExpensesTableObject buildExpensesTableOutput(DataManager.TimedExpense timedExpense) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IOHandler.DATE_FORMAT);
        SimpleStringProperty dateString = new SimpleStringProperty(timedExpense.date().format(formatter));
        SimpleStringProperty amountString = new SimpleStringProperty(buildMoneyOutput(timedExpense.amount()));
        SimpleStringProperty descriptionString = new SimpleStringProperty(timedExpense.description());
        return new ExpensesTableObject(dateString, amountString, descriptionString);
    }
}
