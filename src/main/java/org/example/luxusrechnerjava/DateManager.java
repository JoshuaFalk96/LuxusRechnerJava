package org.example.luxusrechnerjava;

import java.time.LocalDate;

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
        //TODO write logic
        return null; //placeholder
    }

    /**
     * Based on reset date and cycle length config
     * determines date at which the cycle ends
     *
     * @return LocalDate object for teh date the cycle ends
     */
    static LocalDate getEndOfCycleDate() {
        //TODO write logic
        return null; //placeholder
    }

    /**
     * Calculates how many days to end of the cycle from the input date
     *
     * @param date Date to calculate from
     * @return Days to end of cycle
     */
    static int getDaysToCycleEnd(LocalDate date) {
        //TODO write logic
        return 0; //placeholder
    }
}
