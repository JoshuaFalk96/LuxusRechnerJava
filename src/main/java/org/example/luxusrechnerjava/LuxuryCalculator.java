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
        //read week budget from config
        int budget = Main.dataManager.getBudgetConfig();
        //determine how many full weeks are in the time-span
        int fullWeeks = daysToConsider / 7;
        return fullWeeks * budget;
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
        //determine how many days are left when full weeks are removed from time-span
        int days = daysToConsider % 7;
        //if no days remain the time-span only contains full weeks and partial budget is not needed
        if (days == 0) return 0;
        //read week budget from config
        int budget = Main.dataManager.getBudgetConfig();
        //read from config of part budgets are used
        boolean partBudget = Main.dataManager.getPartBudgetConfig();
        int partialBudget = 0;
        if (partBudget) {
            //config is set to allocate partial budget based on remaining days
            partialBudget = (budget / 7) * days;
        } else {
            //config is set to allocate full budgets to partial weeks
            partialBudget = budget;
        }
        return partialBudget;
    }

    /**
     * From the input balance subtract budgets for coming weeks
     * and budget for current week. Reduce budget for current week
     * by the amount of expenses
     *
     * @param balance  Total balance including week budgets
     * @param expenses Expenses of the current week
     * @return Fraction of the balance that can be used for luxuries
     */
    static int calculateLuxuryMoney(int balance, int expenses) {
        //determine how many days are left til next income after the current week
        int daysToCycleEnd = DateManager.getDaysToCycleEnd(DateManager.getEndOfWeekDate(DateManager.getToday()));
        //get the sum of week budgets for the full weeks in the remaining days
        int fullWeekBudgets = calculateFullWeeksBudget(daysToCycleEnd);
        //get the budget for the partial week at end of remaining days if existing
        int partWeekBudget = calculatePartBudget(daysToCycleEnd);
        //get budget for a single week from config
        int budget = Main.dataManager.getBudgetConfig();
        //calculate the remaining budget for the current week
        int currentBudget = budget - expenses;
        //subtract all reserved Budgets from balance to determine how much cna be used for luxuries
        return balance - currentBudget - fullWeekBudgets - partWeekBudget;
    }

}
