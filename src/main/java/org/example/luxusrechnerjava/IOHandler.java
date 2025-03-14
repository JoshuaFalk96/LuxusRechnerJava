package org.example.luxusrechnerjava;

import javafx.scene.control.Labeled;

public class IOHandler {
    public static final String BUDGETINFO = "Wie hoch ist das Budget pro Woche in ganzen Euro";
    public static final String CYCLELENGTHINFO = "Wie viele Tage vergehen gewöhnlich zwischen Zahlungen";
    public static final String WEEKFORMATINFO = "Wochen können von Montag bis Sonntag oder \n in 7 Tage Abschnitten ab Zahlungeingang berechnet werden";
    public static final String SAVEEXPENESINFO = "Sollen die Ausgaben der aktuellen Woche gespeichert werden?";
    public static final String PARTBUDGETINFO = "Wenn der Zeitraum nicht volle Wochen enthält, werden diese \n als ganze Wochen bertachtet oder das Budget entsprechend \n der Tage verringert";
    public static final String BUDGETRESET = "Budget wurde zurückgesetz";
    private static final String FRONTCURRENCYSYMBOL = ""; //for currencies like $100
    private static final String BACKCURRENCYSYMBOL = "€"; //for currencies like 100€
    private static final String SUBCURRENCYDEVIDER = ","; //language dependent
    private static final int CURRENCYCONVERGIONFACTOR = 100; //for currencies with smaller currency like Euro and Cent, 1 otherwise
    private static final String INPUTEMPTY = "Input Notwendig";
    private static final String INPUTNOTINT = "Keine korrekte ganze Zahl eingegeben";
    private static final String REMAINIGDAYS1 = " und ";
    private static final String REMAININGDAYS2 = " Tage";
    private static final String REMAINIGWEEKS1 = "Noch ";
    private static final String REMAININGWEEKS2 = " Wochen";
    private static final String NEWEXPENSES1 = "Eine Ausgabe von ";
    private static final String NEWEXPENSES2 = " wurde gespeichert";
    private static final String CURRNTLY = "Aktuell: ";
    private static final String DAYS = " Tage";

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
            errorOutput.setText(INPUTEMPTY);
            return null;
        }
        int localCurrencyConvergionFactor = CURRENCYCONVERGIONFACTOR;
        if (CURRENCYCONVERGIONFACTOR != 1) {
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
            errorOutput.setText(INPUTNOTINT);
            return null;
        }
        //converts input to smaller currency for internal processing
        return output * localCurrencyConvergionFactor;
    }

    public static String buildRemainingTimeOutput(int weeks, int days) {
        String remainingDaysString = (days == 0) ? "" : REMAINIGDAYS1 + days + REMAININGDAYS2;
        return REMAINIGWEEKS1 + weeks + REMAININGWEEKS2 + remainingDaysString;
    }

    public static String buildMoneyOutput(int amount) {
        int fraction = amount % CURRENCYCONVERGIONFACTOR;
        StringBuilder fractionString = new StringBuilder(String.valueOf(fraction));
        if (fraction < (CURRENCYCONVERGIONFACTOR / 10)) {
            for (int i = 1; i < String.valueOf(CURRENCYCONVERGIONFACTOR).length() -1; i++) {
                fractionString.insert(0, "0");
            }
        }
        return FRONTCURRENCYSYMBOL + (amount / CURRENCYCONVERGIONFACTOR) + SUBCURRENCYDEVIDER + fractionString + BACKCURRENCYSYMBOL;
    }

    public static String buildNexExpensesOutput(int newExpenses) {
        return NEWEXPENSES1 + newExpenses + NEWEXPENSES2;
    }

    public static String buildBudgetOutput(int budget) {
        int fraction = budget % CURRENCYCONVERGIONFACTOR;
        StringBuilder fractionString = new StringBuilder(String.valueOf(fraction));
        if (fraction < (CURRENCYCONVERGIONFACTOR / 10)) {
            for (int i = 1; i < String.valueOf(CURRENCYCONVERGIONFACTOR).length() -1; i++) {
                fractionString.insert(0, "0");
            }
        }
        return CURRNTLY + FRONTCURRENCYSYMBOL + (budget / CURRENCYCONVERGIONFACTOR) + SUBCURRENCYDEVIDER + fractionString + BACKCURRENCYSYMBOL;
    }

    public static String buildCycleOutput(int days) {
        return CURRNTLY + days + DAYS;
    }
}
