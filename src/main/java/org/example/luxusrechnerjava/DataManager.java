package org.example.luxusrechnerjava;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

abstract class DataManager {

    enum WeekFormat {
        MO_TO_SO,
        SEVEN_DAYS
    }

    record TimedExpense(int id, LocalDate date, int amount, String description) {
        TimedExpense (LocalDate date, int amount, String description) {
            this(ID.getAndIncrement(), date, amount, description);
        }
    }

    static AtomicInteger ID = new AtomicInteger(0);

    abstract LocalDate getResetDate();

    abstract int getSavedExpenses();

    abstract int getBudgetConfig();

    abstract int getCycleLengthConfig();

    abstract WeekFormat getWeekFormatConfig();

    abstract boolean getSaveExpensesConfig();

    abstract boolean getPartBudgetConfig();

    abstract void setResetDate(LocalDate resetDate);

    abstract void setSavedExpenses(int expenses);

    abstract void setBudgetConfig(int budget);

    abstract void setCycleLengthConfig(int days);

    abstract void setWeekFormatConfig(WeekFormat format);

    abstract void setSaveExpensesConfig(boolean isSaveExpenses);

    abstract void setPartBudgetConfig(boolean isPartBudget);
}
