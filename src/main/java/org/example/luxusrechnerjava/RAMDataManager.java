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
    private boolean expensesAutoDelete;
    private boolean expensesIgnoreDate;
    private boolean fixCostAutoTransfer;
    private boolean fixCostIgnoreDate;

    /**
     * initializes with default values
     */
    RAMDataManager() {
        this.beginDate = LocalDate.now().minusDays(10);
        this.endDate = LocalDate.now().plusDays(20);
        this.budget = 10000;
        this.weekFormat = WeekFormat.MO_TO_SO;
        this.partBudget = false;
        this.expensesAutoDelete = false;
        this.expensesIgnoreDate = false;
        this.fixCostAutoTransfer = true;
        this.fixCostIgnoreDate = false;
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
     * @param expensesAutoDelete Should expenses from past weeks be deleted
     * @param expensesIgnoreDate Should all expenses be considered, ignoring date
     * @param fixCostAutoTransfer Should fix cost date be automatically changed to be in current time span
     * @param fixCostIgnoreDate Should all fix cost be considered, ignoring date
     */
    RAMDataManager(LocalDate beginDate, LocalDate endDate, Map<Integer, TimedExpense> savedExpenses,
                   Map<Integer, TimedExpense> savedFixCosts, int budget, WeekFormat weekFormat,
                   boolean partBudget, boolean expensesAutoDelete, boolean expensesIgnoreDate,
                   boolean fixCostAutoTransfer, boolean fixCostIgnoreDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.savedExpenses = savedExpenses;
        this.savedFixCosts = savedFixCosts;
        this.budget = budget;
        this.weekFormat = weekFormat;
        this.partBudget = partBudget;
        this.expensesAutoDelete = expensesAutoDelete;
        this.expensesIgnoreDate = expensesIgnoreDate;
        this.fixCostAutoTransfer = fixCostAutoTransfer;
        this.fixCostIgnoreDate = fixCostIgnoreDate;
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
    boolean getExpensesAutoRemovalConfig() {
        return expensesAutoDelete;
    }

    @Override
    boolean getExpensesIgnoreDateConfig() {
        return expensesIgnoreDate;
    }

    @Override
    boolean getFixCostAutoTransferConfig() {
        return fixCostAutoTransfer;
    }

    @Override
    boolean getFixCostIgnoreDateConfig() {
        return fixCostIgnoreDate;
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
    void setExpensesAutoRemovalConfig(boolean isExpensesAutoRemoval) {
        this.expensesAutoDelete = isExpensesAutoRemoval;
    }

    @Override
    void setExpensesIgnoreDateConfig(boolean isExpensesIgnoreDate) {
        this.expensesIgnoreDate = isExpensesIgnoreDate;
    }

    @Override
    void setFixCostAutoTransferConfig(boolean isFixCostAutoTransfer) {
        this.fixCostAutoTransfer = isFixCostAutoTransfer;
    }

    @Override
    void setFixCostIgnoreDateConfig(boolean isFixCostIgnoreDate) {
        this.fixCostIgnoreDate = isFixCostIgnoreDate;
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
