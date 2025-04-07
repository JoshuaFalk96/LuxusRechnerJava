package org.example.luxusrechnerjava;

import java.time.LocalDate;
import java.util.Map;

import org.example.luxusrechnerjava.DataManager.TimedExpense;

public class LuxuryCalculator {

    public record CalculationResult(int expenses, int fixCost, int currentBudget,
                                    int fullBudgets, int partBudget, int luxuryMoney) {

    }

    private static int calculateTotalExpenses(LocalDate currentDate) {
        Map<Integer, TimedExpense> savedExpenses = App.dataManager.getSavedExpenses();
        int totalExpenses = 0;
        for (TimedExpense expense : savedExpenses.values()) {
            //only consider Expenses of current Week
            if (expense.date().isAfter(DateManager.getBeginOfWeekDate(currentDate).minusDays(1))) {
                totalExpenses += expense.amount();
            }
        }
        return totalExpenses;
    }

    private static int calculateTotalFixCost(LocalDate currentDate) {
        Map<Integer, TimedExpense> savedFixCost = App.dataManager.getSavedFixCost();
        int totalFixCost = 0;
        for (TimedExpense fixCost : savedFixCost.values()) {
            //only consider fix cost in the future
            if (fixCost.date().isAfter(currentDate)) {
                totalFixCost += fixCost.amount();
            }
        }
        return totalFixCost;
    }

    /**
     * Calculates how many full weeks are in the days to consider
     * and returns that number times the budget set in config
     *
     * @param daysToConsider The time-span to consider in days
     * @return The budget reserved for the full weeks int the time-span
     */
    private static int calculateFullWeeksBudget(int daysToConsider) {
        //read week budget from config
        int budget = App.dataManager.getBudgetConfig();
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
        int budget = App.dataManager.getBudgetConfig();
        //read from config of part budgets are used
        boolean partBudget = App.dataManager.getPartBudgetConfig();
        int partialBudget;
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
     * @param balance              Total balance including week budgets
     * @param additionalDeductions
     * @return Fraction of the balance that can be used for luxuries
     */
    static CalculationResult calculateLuxuryMoney(int balance, int additionalDeductions) {
        LocalDate today = DateManager.getToday();
        //determine how many days are left til next income after the current week
        int daysToCycleEnd = DateManager.getDaysToCycleEnd(DateManager.getEndOfWeekDate(today));

        int totalExpenses = calculateTotalExpenses(today);

        int totalFixCost = calculateTotalFixCost(today);
        //get the sum of week budgets for the full weeks in the remaining days
        int fullWeekBudgets = calculateFullWeeksBudget(daysToCycleEnd);
        //get the budget for the partial week at end of remaining days if existing
        int partWeekBudget = calculatePartBudget(daysToCycleEnd);
        //get budget for a single week from config
        int budget = App.dataManager.getBudgetConfig();
        //calculate the remaining budget for the current week
        int currentBudget = totalExpenses >= budget ? 0 : budget - totalExpenses;

        //subtract all reserved Budgets from balance to determine how much cna be used for luxuries
        int luxuryMoney = balance
                - additionalDeductions
                - totalFixCost
                - currentBudget
                - fullWeekBudgets
                - partWeekBudget;
        return new CalculationResult(totalExpenses, totalFixCost, currentBudget, fullWeekBudgets,
                partWeekBudget, luxuryMoney);
    }

}
