package org.example.luxusrechnerjava;

public class LuxuryCalculator {
    /**
     * Calculates how many full weeks are in the days to consider
     * and returns that number times the budget set in config
     *
     * @param daysToConsider The time-span to consider in days
     * @return The budget reserved for the full weeks int the time-span
     */
    private static int calculateFullWeeksBudget(int daysToConsider) {
        //TODO write logic
        return 0; //placeholder
    }

    /**
     * Checks if the time-span has a not full week, it true
     * dedicates a budget for this week based on partBudget config
     * partBudget true: full budget divided by 7 times the remaining days
     * partBudget false: the full budget
     *
     * @param daysToConsider time-span to consider in days
     * @return If time-span not dividable by 7 a budget for the partial week, 0 otherwise
     */
    private static int calculatePartBudget(int daysToConsider) {
        //TODO write logic
        return 0; //placeholder
    }

    /**
     * From the input balance subtract budgets for coming weeks
     * and budget for current week. Reduce budget for current week
     * by the amount of expenses
     *
     * @param balance Total balance including week budgets
     * @param expenses Expenses of the current week
     * @return Fraction of the balance that can be used for luxuries
     */
    static int calculateLuxuryMoney(int balance, int expenses) {
        //TODO write logic
        return 0; //placeholder
    }

}
