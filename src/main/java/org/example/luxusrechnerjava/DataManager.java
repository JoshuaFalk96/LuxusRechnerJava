package org.example.luxusrechnerjava;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

abstract class DataManager {

    enum WeekFormat {
        MO_TO_SO,
        SEVEN_DAYS
    }

    record TimedExpense(int id, LocalDate date, int amount, String description) {
        TimedExpense(LocalDate date, int amount, String description) {
            this(ID.incrementAndGet(), date, amount, description);
        }
    }

    static AtomicInteger ID;

    void initializeID() {
        Optional<Integer> maxID = getSavedExpenses().keySet().stream().max(Integer::compareTo);
        ID = maxID.map(AtomicInteger::new).orElseGet(() -> new AtomicInteger(0));
    }

    abstract LocalDate getResetDate();

    abstract Map<Integer, TimedExpense> getSavedExpenses();

    abstract int getBudgetConfig();

    abstract int getCycleLengthConfig();

    abstract WeekFormat getWeekFormatConfig();

    abstract boolean getSaveExpensesConfig();

    abstract boolean getPartBudgetConfig();

    abstract void setResetDate(LocalDate resetDate);

    abstract void setSavedExpenses(Map<Integer, TimedExpense> expenses);

    abstract void setBudgetConfig(int budget);

    abstract void setCycleLengthConfig(int days);

    abstract void setWeekFormatConfig(WeekFormat format);

    abstract void setSaveExpensesConfig(boolean isSaveExpenses);

    abstract void setPartBudgetConfig(boolean isPartBudget);

    abstract void addSavedExpense(TimedExpense expense);

    abstract void removeSavedExpense(int id);

}
