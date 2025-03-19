package org.example.luxusrechnerjava;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class FileDataManager extends DataManager{
    private final Properties config;
    private final FileWriter configWriter;

    /**
     * initializes with default values
     */
    FileDataManager(String configFilePath) throws IOException {
        config = new Properties();
        configWriter = new FileWriter(configFilePath);
        config.load(new FileInputStream(configFilePath));
    }

    @Override
    LocalDate getResetDate() {
        return LocalDate.parse(config.getProperty("resetDate"));
    }

    @Override
    int getSavedExpenses() {
        return Integer.parseInt(config.getProperty("savedExpenses"));
    }

    @Override
    int getBudgetConfig() {
        return Integer.parseInt(config.getProperty("budget"));
    }

    @Override
    int getCycleLengthConfig() {
        return Integer.parseInt(config.getProperty("cycleLength"));
    }

    @Override
    WeekFormat getWeekFormatConfig() {
        return WeekFormat.valueOf(config.getProperty("weekFormat"));
    }

    @Override
    boolean getSaveExpensesConfig() {
        return Boolean.parseBoolean(config.getProperty("saveExpenses"));
    }

    @Override
    boolean getPartBudgetConfig() {
        return Boolean.parseBoolean(config.getProperty("partBudget"));
    }

    @Override
    void setResetDate(LocalDate resetDate) {
        try {
            config.setProperty("resetDate", resetDate.toString());
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setSavedExpenses(int expenses) {
        try {
            config.setProperty("savedExpenses", String.valueOf(expenses));
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setBudgetConfig(int budget) {
        try {
            config.setProperty("budget", String.valueOf(budget));
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setCycleLengthConfig(int days) {
        try {
            config.setProperty("cycleLength", String.valueOf(days));
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setWeekFormatConfig(WeekFormat format) {
        try {
            config.setProperty("weekFormat", format.toString());
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setSaveExpensesConfig(boolean isSaveExpenses) {
        try {
            config.setProperty("saveExpenses", String.valueOf(isSaveExpenses));
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setPartBudgetConfig(boolean isPartBudget) {
        try {
            config.setProperty("partBudget", String.valueOf(isPartBudget));
            config.store(configWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
