package org.example.luxusrechnerjava;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateManager {
    /**
     * Returns the date of today
     *
     * @return LocalDate object representing today
     */
    static LocalDate getToday() {
        return LocalDate.now();
    }

    /**
     * From the week containing the input date,
     * returns the date of the last day of the week.
     * Uses config to determine definition of a week,
     * either Monday to Sunday or 7-day intervals starting at reset day
     *
     * @param currentDate Date from which the next end of week is to find
     * @return LocalDate object for date at the end of given week
     */
    static LocalDate getEndOfWeekDate(LocalDate currentDate) {
        //read the week format from config
        DataManager.WeekFormat weekFormat = App.dataManager.getWeekFormatConfig();
        int daysToWeekEnd = 7;
        if (weekFormat == DataManager.WeekFormat.SEVEN_DAYS) {
            //week is defined as 7-day intervals starting reset day
            LocalDate resetDate = App.dataManager.getBeginDate();
            //get days from reset day to input date
            int daysSinceReset = (int) resetDate.until(currentDate, ChronoUnit.DAYS);
            //remove full 7-day intervals and subtract passed days in current week
            daysToWeekEnd = 6 - (daysSinceReset % 7);
        } else {
            //week is defined as Monday to Sunday
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            //depending on day of week how many days til sunday
            switch (dayOfWeek) {
                case MONDAY -> daysToWeekEnd = 6;
                case TUESDAY -> daysToWeekEnd = 5;
                case WEDNESDAY -> daysToWeekEnd = 4;
                case THURSDAY -> daysToWeekEnd = 3;
                case FRIDAY -> daysToWeekEnd = 2;
                case SATURDAY -> daysToWeekEnd = 1;
                case SUNDAY -> daysToWeekEnd = 0;
            }
        }
        //if daysToWeekEnd == 0 input is end of week
        if (daysToWeekEnd == 0) return currentDate;
        return currentDate.plusDays(daysToWeekEnd);
    }

    public static LocalDate getBeginOfWeekDate(LocalDate currentDate) {
        //read the week format from config
        DataManager.WeekFormat weekFormat = App.dataManager.getWeekFormatConfig();
        int daysFromWeekStart = 0;
        if (weekFormat == DataManager.WeekFormat.SEVEN_DAYS) {
            //week is defined as 7-day intervals starting reset day
            LocalDate resetDate = App.dataManager.getBeginDate();
            //get days from reset day to input date
            int daysSinceReset = (int) resetDate.until(currentDate, ChronoUnit.DAYS);
            //remove full 7-day intervals
            daysFromWeekStart = daysSinceReset % 7;
        } else {
            //week is defined as Monday to Sunday
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            //depending on day of week how many days since monday
            switch (dayOfWeek) {
                case MONDAY -> daysFromWeekStart = 0;
                case TUESDAY -> daysFromWeekStart = 1;
                case WEDNESDAY -> daysFromWeekStart = 2;
                case THURSDAY -> daysFromWeekStart = 3;
                case FRIDAY -> daysFromWeekStart = 4;
                case SATURDAY -> daysFromWeekStart = 5;
                case SUNDAY -> daysFromWeekStart = 6;
            }
        }
        //if daysFromWeekStart == 0 input is start of week
        if (daysFromWeekStart == 0) return currentDate;
        return currentDate.minusDays(daysFromWeekStart);
    }

    /**
     * Calculates how many days to end of the cycle from the input date
     *
     * @param date Date to calculate from
     * @return Days to end of cycle
     */
    static int getDaysToCycleEnd(LocalDate date) {
        return (int) date.until(App.dataManager.getEndDate(), ChronoUnit.DAYS);
    }
}
