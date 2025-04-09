package org.example.luxusrechnerjava;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IOHandler {
    public static final String BUDGET_INFO = "Wie hoch ist das Budget pro Woche in ganzen Euro";
    public static final String CYCLE_LENGTH_INFO = "Wie viele Tage vergehen gewöhnlich zwischen Zahlungen";
    public static final String WEEK_FORMAT_INFO = "Wochen können von Montag bis Sonntag oder \n in 7 Tage Abschnitten ab Zahlungeingang berechnet werden";
    public static final String SAVE_EXPENSES_INFO = "Sollen die Ausgaben der aktuellen Woche gespeichert werden?";
    public static final String PART_BUDGET_INFO = "Wenn der Zeitraum nicht volle Wochen enthält, werden diese \n als ganze Wochen bertachtet oder das Budget entsprechend \n der Tage verringert";
    public static final String MAIN_TITLE = "Luxusrechner";
    public static final String FIX_COST_TITLE = "Luxusrechner - Fixkosten";
    public static final String EXPENSES_TITLE = "Luxusrechner - Ausgaben";
    public static final String CONFIG_TITLE = "Luxusrechner - Einstellungen";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String NEW_EXPENSE = "Neue Ausgabe";
    public static final String NEW_FIX_COST = "Neue Fixkosten";
    public static final String EXPENSES_PLACEHOLDER = "Noch keine Ausgaben angegeben";
    public static final String FIX_COST_PLACEHOLDER = "Noch keine Fixkosen angegeben";
    public static final String DATE_INPUT_ERROR = "Datum ist nicht korrekt";
    public static final String RED_BORDER_STYLE = "-fx-border-color: red";
    public static final String WRONG_TIME_SPAN_ORDER1 = "Begin muss vor";
    public static final String WRONG_TIME_SPAN_ORDER2 = "Ende sein";

    private static final String FRONT_CURRENCY_SYMBOL = ""; //for currencies like $100
    private static final String BACK_CURRENCY_SYMBOL = "€"; //for currencies like 100€
    private static final String SUB_CURRENCY_DIVIDER = ","; //language dependent
    private static final int CURRENCY_CONVERSION_FACTOR = 100; //for currencies with smaller currency like Euro and Cent, 1 otherwise
    private static final String INPUT_EMPTY = "Input Notwendig";
    private static final String INPUT_NOT_INT = "Keine korrekte Zahl eingegeben";
    private static final String CURRENTLY = "Aktuell: ";
    private static final String DAYS = " Tage";
    private static final String TIME_SPAN_SEPARATOR = " bis ";


    public record ExpensesTableObject(int id, SimpleStringProperty date, SimpleStringProperty amount,
                                      SimpleStringProperty description) {
    }

    public static class NotIntInputException extends Exception {
        private final String errorText;

        public NotIntInputException(String errorText) {
            this.errorText = errorText;
        }

        public String getErrorText() {
            return errorText;
        }
    }

    /**
     * parses given String to integer
     * throws exception with error text if input is empty or not valid
     *
     * @param input String from input field for parsing
     * @return input as integer if parsable
     */
    public static int parseIntegerInput(String input) throws NotIntInputException {
        //check if input is empty
        if (input.isBlank()) {
            throw new NotIntInputException(INPUT_EMPTY);
        }
        //parse input to integer
        int output;
        try {
            output = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            //input is not valid integer
            throw new NotIntInputException(INPUT_NOT_INT);
        }
        return output;
    }

    public static int parseMoneyInput(String input) throws NotIntInputException {
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
        int output = parseIntegerInput(input);
        //convert input to smaller currency unit for internal processing
        return output * localCurrencyConvergionFactor;

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

    public static String buildBudgetOutput(int budget) {
        return CURRENTLY + buildMoneyOutput(budget);
    }

    public static String buildCycleOutput(int days) {
        return CURRENTLY + days + DAYS;
    }

    public static String buildTimeSpanOutput(LocalDate begin, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IOHandler.DATE_FORMAT);
        return begin.format(formatter) + TIME_SPAN_SEPARATOR + end.format(formatter);
    }

    static ExpensesTableObject buildExpensesTableObject(DataManager.TimedExpense timedExpense) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IOHandler.DATE_FORMAT);
        SimpleStringProperty dateString = new SimpleStringProperty(timedExpense.date().format(formatter));
        SimpleStringProperty amountString = new SimpleStringProperty(buildMoneyOutput(timedExpense.amount()));
        SimpleStringProperty descriptionString = new SimpleStringProperty(timedExpense.description());
        return new ExpensesTableObject(timedExpense.id(), dateString, amountString, descriptionString);
    }
}
