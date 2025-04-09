package org.example.luxusrechnerjava;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileDataManager extends DataManager {
    //file paths
    private final Path CONFIG_FILE_PATH = Paths.get("../UserData/config.properties");
    private final Path EXPENSES_FILE_PATH = Paths.get("../UserData/savedExpenses.json");
    private final Path FIX_COST_FILE_PATH = Paths.get("../UserData/fixCosts.json");
    //data objects
    private final Properties config;
    private Map<Integer, TimedExpense> savedExpenses;
    private Map<Integer, TimedExpense> savedFixCosts;
    //identifier names for config
    private final String START_DATE_NAME = "startDate";
    private final String END_DATE_NAME = "endDate";
    private final String BUDGET_NAME = "budget";
    private final String WEEK_FORMAT_NAME = "weekFormat";
    private final String PART_BUDGET_NAME = "partBudget";
    //default values for config
    //default start day it first of current month
    private final String START_DATE_DEFAULT = DateManager.getToday().withDayOfMonth(1).toString();
    //default end date is first of next month
    private final String END_DATE_DEFAULT = DateManager.getToday().withDayOfMonth(1).plusMonths(1).toString();
    //default budget is 100.00
    private final String BUDGET_DEFAULT = String.valueOf(10000);
    private final String WEEK_FORMAT_DEFAULT = WeekFormat.MO_TO_SO.toString();
    private final String PART_BUDGET_DEFAULT = String.valueOf(true);
    //Object mapper fpr json serialization
    private final ObjectMapper OBJECT_MAPPER;
    //flags for which file to safe
    private boolean configChanged = false;
    private boolean expensesChanged = false;
    private boolean fixCostsChanged = false;


    /**
     * initializes with default values
     */
    FileDataManager() throws IOException {
        //initialize object mapper
        OBJECT_MAPPER = JsonMapper.builder()
                .findAndAddModules()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        //initialize config
        config = new Properties();
        if (Files.notExists(CONFIG_FILE_PATH)) {
            //config file does not exist then create file with default values
            Files.createDirectories(CONFIG_FILE_PATH.getParent());
            Files.createFile(CONFIG_FILE_PATH);
            config.setProperty(START_DATE_NAME, START_DATE_DEFAULT);
            config.setProperty(END_DATE_NAME, END_DATE_DEFAULT);
            config.setProperty(BUDGET_NAME, BUDGET_DEFAULT);
            config.setProperty(WEEK_FORMAT_NAME, WEEK_FORMAT_DEFAULT);
            config.setProperty(PART_BUDGET_NAME, PART_BUDGET_DEFAULT);
            configChanged = true;
        } else {
            config.load(new FileInputStream(CONFIG_FILE_PATH.toString()));
        }
        //initialize expenses
        savedExpenses = new HashMap<>();
        if (Files.notExists(EXPENSES_FILE_PATH)) {
            //saved expenses file does not exist
            Files.createDirectories(EXPENSES_FILE_PATH.getParent());
            Files.createFile(EXPENSES_FILE_PATH);
            expensesChanged = true;
        } else {
            try (FileInputStream fileInput = new FileInputStream(EXPENSES_FILE_PATH.toString())) {
                TypeReference<HashMap<Integer, TimedExpense>> mapType = new TypeReference<>() {
                };
                savedExpenses = OBJECT_MAPPER.readValue(fileInput, mapType);
            }
        }
        //initialize fix cost
        savedFixCosts = new HashMap<>();
        if (Files.notExists(FIX_COST_FILE_PATH)) {
            //saved expenses file does not exist
            Files.createDirectories(FIX_COST_FILE_PATH.getParent());
            Files.createFile(FIX_COST_FILE_PATH);
            fixCostsChanged = true;
        } else {
            try (FileInputStream fileInput = new FileInputStream(FIX_COST_FILE_PATH.toString())) {
                TypeReference<HashMap<Integer, TimedExpense>> mapType = new TypeReference<>() {
                };
                savedFixCosts = OBJECT_MAPPER.readValue(fileInput, mapType);
            }
        }
        //save new files if necessary
        saveChanges();
        super.initializeID();
    }

    @Override
    void saveChanges() {
        if (configChanged) {
            //write config file
            try (FileWriter configWriter = new FileWriter(CONFIG_FILE_PATH.toString(), false)) {
                config.store(configWriter, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            configChanged = false;
        }
        if (expensesChanged) {
            //write expenses file
            try (FileWriter expensesWriter = new FileWriter(EXPENSES_FILE_PATH.toString())) {
                OBJECT_MAPPER.writeValue(expensesWriter, savedExpenses);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            expensesChanged = false;
        }
        if (fixCostsChanged) {
            //write fix cost file
            try (FileWriter fixCostWriter = new FileWriter(FIX_COST_FILE_PATH.toString())) {
                OBJECT_MAPPER.writeValue(fixCostWriter, savedFixCosts);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fixCostsChanged = false;
        }
    }

    @Override
    LocalDate getBeginDate() {
        return LocalDate.parse(config.getProperty(START_DATE_NAME, START_DATE_DEFAULT));
    }

    @Override
    LocalDate getEndDate() {
        return LocalDate.parse(config.getProperty(END_DATE_NAME, END_DATE_DEFAULT));
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
        return Integer.parseInt(config.getProperty(BUDGET_NAME, BUDGET_DEFAULT));
    }

    @Override
    WeekFormat getWeekFormatConfig() {
        return WeekFormat.valueOf(config.getProperty(WEEK_FORMAT_NAME, WEEK_FORMAT_DEFAULT));
    }

    @Override
    boolean getPartBudgetConfig() {
        return Boolean.parseBoolean(config.getProperty(PART_BUDGET_NAME, PART_BUDGET_DEFAULT));
    }

    @Override
    void setBeginDate(LocalDate beginDate) {
        config.setProperty(START_DATE_NAME, beginDate.toString());
        configChanged = true;
    }

    @Override
    void setEndDate(LocalDate endDate) {
        config.setProperty(END_DATE_NAME, endDate.toString());
        configChanged = true;
    }

    @Override
    void setSavedExpenses(Map<Integer, TimedExpense> expenses) {
        savedExpenses = new HashMap<>(expenses);
        expensesChanged = true;
    }

    @Override
    void setSavedFixCosts(Map<Integer, TimedExpense> fixCosts) {
        savedFixCosts = new HashMap<>(fixCosts);
        fixCostsChanged = true;
    }

    @Override
    void setBudgetConfig(int budget) {
        config.setProperty(BUDGET_NAME, String.valueOf(budget));
        configChanged = true;
    }

    @Override
    void setWeekFormatConfig(WeekFormat format) {
        config.setProperty(WEEK_FORMAT_NAME, format.toString());
        configChanged = true;
    }

    @Override
    void setPartBudgetConfig(boolean isPartBudget) {
        config.setProperty(PART_BUDGET_NAME, String.valueOf(isPartBudget));
        configChanged = true;
    }

    @Override
    void addSavedExpense(TimedExpense expense) {
        savedExpenses.put(expense.id(), expense);
        expensesChanged = true;
    }

    @Override
    void addSavedFixCost(TimedExpense fixCost) {
        savedFixCosts.put(fixCost.id(), fixCost);
        fixCostsChanged = true;
    }

    @Override
    void removeSavedExpense(int id) {
        savedExpenses.remove(id);
        expensesChanged = true;
    }

    @Override
    void removeSavedFixCost(int id) {
        savedFixCosts.remove(id);
        fixCostsChanged = true;
    }
}
