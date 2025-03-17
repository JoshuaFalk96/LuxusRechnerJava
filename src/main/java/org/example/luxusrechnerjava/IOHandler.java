package org.example.luxusrechnerjava;

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
    private static final String INPUT_NOT_INT = "Keine korrekte ganze Zahl eingegeben";
    private static final String REMAINING_DAYS_1 = " und ";
    private static final String REMAINING_DAYS_2 = " Tage";
    private static final String REMAINING_WEEKS_1 = "Noch ";
    private static final String REMAINING_WEEKS_2 = " Wochen";
    private static final String NEW_EXPENSES_1 = "Eine Ausgabe von ";
    private static final String NEW_EXPENSES_2 = " wurde gespeichert";
    private static final String CURRENTLY = "Aktuell: ";
    private static final String DAYS = " Tage";
    private static final String NEW_RESET_DATE = "Anfang des Zeitraums gesetzt auf den ";

    /**
     * parses given String to integer
     * writes error messages to given output if input is empty or not valid
     *
     * @param input       String from input field for parsing
     * @param errorOutput Output for error message
     * @return input as integer if parsable, null otherwise
     */
    public static Integer parseInteger(String input, Labeled errorOutput) {
        //check if input is empty
        if (input.isBlank()) {
            errorOutput.setText(INPUT_EMPTY);
            return null;
        }
        int localCurrencyConvergionFactor = CURRENCY_CONVERSION_FACTOR;
        if (CURRENCY_CONVERSION_FACTOR != 1) {
            //currency has smaller fraction currency, e.g. Euro and Cent
            if (input.contains(",") || input.contains(".")) {
                //input contains currency fraction, e.g. Cent for Euro
                //removes dividing characters to be valid integer
                input = input.replace(",", "");
                input = input.replace(".", "");
                //after removing divider, input already in smaller currency
                localCurrencyConvergionFactor = 1;
            }
        }
        //read input Field for balance and convert to integer
        int output;
        try {
            output = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            //input is not valid integer
            errorOutput.setText(INPUT_NOT_INT);
            return null;
        }
        //converts input to smaller currency for internal processing
        return output * localCurrencyConvergionFactor;
    }

    public static String buildRemainingTimeOutput(int weeks, int days) {
        String remainingDaysString = (days == 0) ? "" : REMAINING_DAYS_1 + days + REMAINING_DAYS_2;
        return REMAINING_WEEKS_1 + weeks + REMAINING_WEEKS_2 + remainingDaysString;
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
}
