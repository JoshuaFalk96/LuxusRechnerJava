package org.example.luxusrechnerjava;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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

    record TimedExpense(int id, @JsonSerialize(using = LocalDateSerializer.class) LocalDate date, int amount,
                        String description) {
        TimedExpense(LocalDate date, int amount, String description) {
            this(ID.incrementAndGet(), date, amount, description);
        }
    }

    static AtomicInteger ID;

    final void initializeID() {
        Set<Integer> usedIDs = new HashSet<>(getSavedExpenses().keySet());
        usedIDs.addAll(getSavedFixCosts().keySet());
        Optional<Integer> maxID = usedIDs.stream().max(Integer::compareTo);
        ID = maxID.map(AtomicInteger::new).orElseGet(() -> new AtomicInteger(0));
    }

    abstract void saveChanges();

    abstract LocalDate getBeginDate();

    abstract LocalDate getEndDate();

    abstract Map<Integer, TimedExpense> getSavedExpenses();

    abstract Map<Integer, TimedExpense> getSavedFixCosts();

    abstract int getBudgetConfig();

    abstract WeekFormat getWeekFormatConfig();

    abstract boolean getPartBudgetConfig();

    abstract boolean getExpensesAutoRemovalConfig();

    abstract boolean getExpensesIgnoreDateConfig();

    abstract boolean getFixCostAutoTransferConfig();

    abstract boolean getFixCostIgnoreDateConfig();

    abstract void setBeginDate(LocalDate beginDate);

    abstract void setEndDate(LocalDate endDate);

    abstract void setSavedExpenses(Map<Integer, TimedExpense> expenses);

    abstract void setSavedFixCosts(Map<Integer, TimedExpense> fixCosts);

    abstract void setBudgetConfig(int budget);

    abstract void setWeekFormatConfig(WeekFormat format);

    abstract void setPartBudgetConfig(boolean isPartBudget);

    abstract void setExpensesAutoRemovalConfig(boolean isExpensesAutoRemoval);

    abstract void setExpensesIgnoreDateConfig(boolean isExpensesIgnoreDate);

    abstract void setFixCostAutoTransferConfig(boolean isFixCostAutoTransfer);

    abstract void setFixCostIgnoreDateConfig(boolean isFixCostIgnoreDate);

    abstract void addSavedExpense(TimedExpense expense);

    abstract void addSavedFixCost(TimedExpense fixCost);

    abstract void removeSavedExpense(int id);

    abstract void removeSavedFixCost(int id);

}
