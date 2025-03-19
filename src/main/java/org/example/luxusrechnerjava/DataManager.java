package org.example.luxusrechnerjava;

import java.time.LocalDate;

abstract class DataManager {

    enum WeekFormat {
        MO_TO_SO,
        SEVEN_DAYS
    }

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
