package org.example.luxusrechnerjava;

import java.time.LocalDate;

/**
 * Implementation of DataManger only saving in working memory, resets every time
 */
public class RAMDataManager extends DataManager {
    private LocalDate resetDate;
    private int savedExpenses;
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
        this.savedExpenses = 1245;
        this.budget = 10000;
        this.cycleLength = 30;
        this.weekFormat = WeekFormat.MO_TO_SO;
        this.saveExpenses = true;
        this.partBudget = false;
    }

    /**
     * initializes with given values
     * @param resetDate Date when cycle starts
     * @param savedExpenses Expenses of current week
     * @param budget Budget for a week
     * @param cycleLength Length of a cycle in days
     * @param weekFormat How are weeks defined
     * @param saveExpenses Should expenses be saved
     * @param partBudget Should not full weeks get are smaller budget
     */
    RAMDataManager(LocalDate resetDate, int savedExpenses, int budget, int cycleLength, WeekFormat weekFormat, boolean saveExpenses, boolean partBudget) {
        this.resetDate = resetDate;
        this.savedExpenses = savedExpenses;
        this.budget = budget;
        this.cycleLength = cycleLength;
        this.weekFormat = weekFormat;
        this.saveExpenses = saveExpenses;
        this.partBudget = partBudget;
    }

    @Override
    LocalDate getResetDate() {
        return resetDate;
    }

    @Override
    int getSavedExpenses() {
        return savedExpenses;
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
    void setSavedExpenses(int expenses) {
        this.savedExpenses = expenses;
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
}
