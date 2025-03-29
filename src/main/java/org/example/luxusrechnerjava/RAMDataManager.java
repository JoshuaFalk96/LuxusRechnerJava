package org.example.luxusrechnerjava;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DataManger only saving in working memory, resets every time
 */
public class RAMDataManager extends DataManager {
    private LocalDate resetDate;
    private Map<Integer, TimedExpense> savedExpenses;
    private int budget;
    private int cycleLength;
    private WeekFormat weekFormat;
    private boolean saveExpenses;
    private boolean partBudget;

    /**
     * initializes with default values
     */
    RAMDataManager() {
        this.resetDate = LocalDate.now();
        this.budget = 10000;
        this.cycleLength = 30;
        this.weekFormat = WeekFormat.MO_TO_SO;
        this.saveExpenses = true;
        this.partBudget = false;
        TimedExpense expense = new TimedExpense(5, LocalDate.now(), 2000, "Pizza");
        this.savedExpenses = new HashMap<>();
        this.savedExpenses.put(expense.id(), expense);
        initializeID();
    }

    /**
     * initializes with given values
     *
     * @param resetDate     Date when cycle starts
     * @param savedExpenses Expenses of current week
     * @param budget        Budget for a week
     * @param cycleLength   Length of a cycle in days
     * @param weekFormat    How are weeks defined
     * @param saveExpenses  Should expenses be saved
     * @param partBudget    Should not full weeks get are smaller budget
     */
    RAMDataManager(LocalDate resetDate, Map<Integer, TimedExpense> savedExpenses, int budget, int cycleLength, WeekFormat weekFormat, boolean saveExpenses, boolean partBudget) {
        this.resetDate = resetDate;
        this.savedExpenses = Map.copyOf(savedExpenses);
        this.budget = budget;
        this.cycleLength = cycleLength;
        this.weekFormat = weekFormat;
        this.saveExpenses = saveExpenses;
        this.partBudget = partBudget;
        initializeID();
    }

    @Override
    LocalDate getResetDate() {
        return resetDate;
    }

    @Override
    Map<Integer, TimedExpense> getSavedExpenses() {
        return Map.copyOf(savedExpenses);
    }

    @Override
    int getBudgetConfig() {
        return budget;
    }

    @Override
    int getCycleLengthConfig() {
        return cycleLength;
    }

    @Override
    WeekFormat getWeekFormatConfig() {
        return weekFormat;
    }

    @Override
    boolean getSaveExpensesConfig() {
        return saveExpenses;
    }

    @Override
    boolean getPartBudgetConfig() {
        return partBudget;
    }

    @Override
    void setResetDate(LocalDate resetDate) {
        this.resetDate = resetDate;
    }

    @Override
    void setSavedExpenses(Map<Integer, TimedExpense> expenses) {
        this.savedExpenses = Map.copyOf(expenses);
    }

    @Override
    void setBudgetConfig(int budget) {
        this.budget = budget;
    }

    @Override
    void setCycleLengthConfig(int days) {
        this.cycleLength = days;
    }

    @Override
    void setWeekFormatConfig(WeekFormat format) {
        this.weekFormat = format;
    }

    @Override
    void setSaveExpensesConfig(boolean isSaveExpenses) {
        this.saveExpenses = isSaveExpenses;
    }

    @Override
    void setPartBudgetConfig(boolean isPartBudget) {
        this.partBudget = isPartBudget;
    }

    @Override
    void addSavedExpense(TimedExpense expense) {
        this.savedExpenses.put(expense.id(), expense);
    }

    @Override
    void removeSavedExpense(int id) {
        this.savedExpenses.remove(id);
    }
}
