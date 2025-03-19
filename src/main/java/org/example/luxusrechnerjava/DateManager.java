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
     * @param dateInWeek Date from which the next end of week is to find
     * @return LocalDate object for date at the end of given week
     */
    static LocalDate getEndOfWeekDate(LocalDate dateInWeek) {
        //read the week format from config
        DataManager.WeekFormat weekFormat = Main.dataManager.getWeekFormatConfig();
        int daysToWeekEnd = 7;
        if (weekFormat == DataManager.WeekFormat.SEVEN_DAYS) {
            //week is defined as 7-day intervals starting reset day
            LocalDate resetDate = Main.dataManager.getResetDate();
            //get days from reset day to input date
            int daysSinceReset = (int) resetDate.until(dateInWeek, ChronoUnit.DAYS);
            //remove full 7-day intervals and subtract passed days in current week
            daysToWeekEnd = 6 - (daysSinceReset % 7);
        } else {
            //week is defined as Monday to Sunday
            DayOfWeek dayOfWeek = dateInWeek.getDayOfWeek();
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
        if (daysToWeekEnd == 0) return dateInWeek;
        return dateInWeek.plusDays(daysToWeekEnd);
    }

    /**
     * Based on reset date and cycle length config
     * determines date at which the cycle ends
     *
     * @return LocalDate object for the date the cycle ends
     */
    static LocalDate getEndOfCycleDate() {
        //get reset date and cycle length from config
        LocalDate resetDate = Main.dataManager.getResetDate();
        int cycleLength = Main.dataManager.getCycleLengthConfig();
        //end of cycle date ist cycle length days after reset date
        return resetDate.plusDays(cycleLength);
    }

    /**
     * Calculates how many days to end of the cycle from the input date
     *
     * @param date Date to calculate from
     * @return Days to end of cycle
     */
    static int getDaysToCycleEnd(LocalDate date) {
        return (int)date.until(getEndOfCycleDate(), ChronoUnit.DAYS);
    }
}
