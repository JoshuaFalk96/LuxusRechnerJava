package org.example.luxusrechnerjava;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Properties;

public class FileDataManager extends DataManager {
    private final Properties config;
    private final FileWriter configFileWriter;
    private final String RESET_DATE_NAME = "resetDate";
    private final String SAVED_EXPENSES_NAME = "savedExpenses";
    private final String BUDGET_NAME = "budget";
    private final String CYCLE_LENGTH_NAME = "cycleLength";
    private final String WEEK_FORMAT_NAME = "weekFormat";
    private final String SAVE_EXPENSES_CONFIG_NAME = "saveExpensesConfig";
    private final String PART_BUDGET_NAME = "partBudget";
    private final String STORE_COMMENT = "Einstellungen von Luxus Rechner";

    /**
     * initializes with default values
     */
    FileDataManager(Path configFilePath) throws IOException {
        config = new Properties();
        if (!Files.exists(configFilePath)) {
            //config file does not exist then create file with default values
            Files.createFile(configFilePath);
            config.setProperty(RESET_DATE_NAME, LocalDate.now().toString());
            config.setProperty(SAVED_EXPENSES_NAME, String.valueOf(1245));
            config.setProperty(BUDGET_NAME, String.valueOf(10000));
            config.setProperty(CYCLE_LENGTH_NAME, String.valueOf(30));
            config.setProperty(WEEK_FORMAT_NAME, WeekFormat.MO_TO_SO.toString());
            config.setProperty(SAVE_EXPENSES_CONFIG_NAME, String.valueOf(true));
            config.setProperty(PART_BUDGET_NAME, String.valueOf(false));
        } else {
            config.load(new FileInputStream(configFilePath.toString()));
        }
        configFileWriter = new FileWriter(configFilePath.toString());
        config.store(configFileWriter, STORE_COMMENT);
    }

    @Override
    LocalDate getResetDate() {
        return LocalDate.parse(config.getProperty(RESET_DATE_NAME));
    }

    @Override
    int getSavedExpenses() {
        return Integer.parseInt(config.getProperty(SAVED_EXPENSES_NAME));
    }

    @Override
    int getBudgetConfig() {
        return Integer.parseInt(config.getProperty(BUDGET_NAME));
    }

    @Override
    int getCycleLengthConfig() {
        return Integer.parseInt(config.getProperty(CYCLE_LENGTH_NAME));
    }

    @Override
    WeekFormat getWeekFormatConfig() {
        return WeekFormat.valueOf(config.getProperty(WEEK_FORMAT_NAME));
    }

    @Override
    boolean getSaveExpensesConfig() {
        return Boolean.parseBoolean(config.getProperty(SAVE_EXPENSES_CONFIG_NAME));
    }

    @Override
    boolean getPartBudgetConfig() {
        return Boolean.parseBoolean(config.getProperty(PART_BUDGET_NAME));
    }

    @Override
    void setResetDate(LocalDate resetDate) {
        try {
            config.setProperty(RESET_DATE_NAME, resetDate.toString());
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setSavedExpenses(int expenses) {
        try {
            config.setProperty(SAVED_EXPENSES_NAME, String.valueOf(expenses));
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setBudgetConfig(int budget) {
        try {
            config.setProperty(BUDGET_NAME, String.valueOf(budget));
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setCycleLengthConfig(int days) {
        try {
            config.setProperty(CYCLE_LENGTH_NAME, String.valueOf(days));
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setWeekFormatConfig(WeekFormat format) {
        try {
            config.setProperty(WEEK_FORMAT_NAME, format.toString());
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setSaveExpensesConfig(boolean isSaveExpenses) {
        try {
            config.setProperty(SAVE_EXPENSES_CONFIG_NAME, String.valueOf(isSaveExpenses));
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void setPartBudgetConfig(boolean isPartBudget) {
        try {
            config.setProperty(PART_BUDGET_NAME, String.valueOf(isPartBudget));
            config.store(configFileWriter, STORE_COMMENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
