package org.example.luxusrechnerjava;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

abstract class DataManager {

    enum WeekFormat {
        MO_TO_SO,
        SEVEN_DAYS
    }

    enum ExpensesViewType {
        EXPENSES,
        FIX_COST
    }

    record TimedExpense(int id, LocalDate date, int amount, String description) {
        TimedExpense(LocalDate date, int amount, String description) {
            this(ID.incrementAndGet(), date, amount, description);
        }
    }

    static AtomicInteger ID;

    final void initializeID() {
        Set<Integer> usedIDs = new HashSet<>(getSavedExpenses().keySet());
        usedIDs.addAll(getSavedFixCost().keySet());
        Optional<Integer> maxID = usedIDs.stream().max(Integer::compareTo);
        ID = maxID.map(AtomicInteger::new).orElseGet(() -> new AtomicInteger(0));
    }

    abstract LocalDate getBeginDate();

    abstract LocalDate getEndDate();

    abstract Map<Integer, TimedExpense> getSavedExpenses();

    abstract Map<Integer, TimedExpense> getSavedFixCost();

    abstract int getBudgetConfig();

    abstract int getCycleLengthConfig();

    abstract WeekFormat getWeekFormatConfig();

    abstract boolean getSaveExpensesConfig();

    abstract boolean getPartBudgetConfig();

    abstract void setBeginDate(LocalDate resetDate);

    abstract void setEndDate(LocalDate resetDate);

    abstract void setSavedExpenses(Map<Integer, TimedExpense> expenses);

    abstract void setSavedFixCost(Map<Integer, TimedExpense> expenses);

    abstract void setBudgetConfig(int budget);

    abstract void setCycleLengthConfig(int days);

    abstract void setWeekFormatConfig(WeekFormat format);

    abstract void setSaveExpensesConfig(boolean isSaveExpenses);

    abstract void setPartBudgetConfig(boolean isPartBudget);

    abstract void addSavedExpense(TimedExpense expense);

    abstract void addSavedFixCost(TimedExpense expense);

    abstract void removeSavedExpense(int id);

    abstract void removeSavedFixCost(int id);

}
