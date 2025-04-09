package org.example.luxusrechnerjava;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DataManger only saving in working memory, resets every time
 */
public class RAMDataManager extends DataManager {
    private LocalDate beginDate;
    private LocalDate endDate;
    private Map<Integer, TimedExpense> savedExpenses;
    private Map<Integer, TimedExpense> savedFixCosts;
    private int budget;
    private WeekFormat weekFormat;
    private boolean partBudget;

    /**
     * initializes with default values
     */
    RAMDataManager() {
        this.beginDate = LocalDate.now().minusDays(10);
        this.endDate = LocalDate.now().plusDays(20);
        this.budget = 10000;
        this.weekFormat = WeekFormat.MO_TO_SO;
        this.partBudget = false;
        TimedExpense expense = new TimedExpense(2, LocalDate.now(), 2000, "Pizza");
        this.savedExpenses = new HashMap<>();
        this.savedExpenses.put(expense.id(), expense);
        TimedExpense fixCost = new TimedExpense(4, LocalDate.now(), 12275, "Versicherung");
        this.savedFixCosts = new HashMap<>();
        this.savedFixCosts.put(fixCost.id(), fixCost);
        initializeID();
    }

    /**
     * initializes with given values
     *
     * @param beginDate     Date when cycle starts
     * @param endDate       Date when cycle ends
     * @param savedExpenses Expenses of current week
     * @param savedFixCosts Fix cost for this time span
     * @param budget        Budget for a week
     * @param weekFormat    How are weeks defined
     * @param partBudget    Should not full weeks get are smaller budget
     */
    RAMDataManager(LocalDate beginDate, LocalDate endDate, Map<Integer, TimedExpense> savedExpenses,
                   Map<Integer, TimedExpense> savedFixCosts, int budget, WeekFormat weekFormat, boolean partBudget) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.savedExpenses = savedExpenses;
        this.savedFixCosts = savedFixCosts;
        this.budget = budget;
        this.weekFormat = weekFormat;
        this.partBudget = partBudget;
        initializeID();
    }

    @Override
    void saveChanges() {
        //not relevant for this manger
    }

    @Override
    LocalDate getBeginDate() {
        return beginDate;
    }

    @Override
    LocalDate getEndDate() {
        return endDate;
    }

    @Override
    Map<Integer, TimedExpense> getSavedExpenses() {
        return Map.copyOf(savedExpenses);
    }

    @Override
    Map<Integer, TimedExpense> getSavedFixCosts() {
        return Map.copyOf(savedFixCosts);
    }

    @Override
    int getBudgetConfig() {
        return budget;
    }

    @Override
    WeekFormat getWeekFormatConfig() {
        return weekFormat;
    }

    @Override
    boolean getPartBudgetConfig() {
        return partBudget;
    }

    @Override
    void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    @Override
    void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    void setSavedExpenses(Map<Integer, TimedExpense> expenses) {
        this.savedExpenses = new HashMap<>(expenses);
    }

    @Override
    void setSavedFixCosts(Map<Integer, TimedExpense> fixCosts) {
        this.savedFixCosts = new HashMap<>(fixCosts);
    }

    @Override
    void setBudgetConfig(int budget) {
        this.budget = budget;
    }

    @Override
    void setWeekFormatConfig(WeekFormat format) {
        this.weekFormat = format;
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
    void addSavedFixCost(TimedExpense fixCost) {
        this.savedFixCosts.put(fixCost.id(), fixCost);
    }

    @Override
    void removeSavedExpense(int id) {
        this.savedExpenses.remove(id);
    }

    @Override
    void removeSavedFixCost(int id) {
        this.savedFixCosts.remove(id);
    }
}
